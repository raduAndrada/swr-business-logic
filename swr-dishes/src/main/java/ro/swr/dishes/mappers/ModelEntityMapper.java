package ro.swr.dishes.mappers;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import model.DatabaseJsonArray;
import model.DatabaseJsonObject;
import model.DatabaseLabel;
import model.Label;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.CollectionUtils;


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public record ModelEntityMapper<M, E>(Class<M> modelClass, Class<E> entityClass) {

    private static ModelMapper mapper = new ModelMapper();
    private static Gson gson = new Gson();

    public M fromEntity(E entity) {
        M model = mapper.map(entity, modelClass);
        convertFromJsonObjectField(entity, model);
        return model;
    }

    public List<M> fromEntity(List<E> entities) {
        return entities.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<M> fromEntity(Page<E> entities) {
        return new PageImpl(fromEntity(entities.getContent()));
    }

    public E toEntity(M model) {
        E entity = mapper.map(model, entityClass);
        convertToJsonObjectField(model, entity);

        return entity;
    }

    public List<E> toEntity(List<M> models) {
        return models.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Page<E> toEntity(Page<M> models) {
        return new PageImpl(toEntity(models.getContent()));
    }

    private void convertFromJsonObjectField(E src, M dst) {
        for (Field srcField : entityClass.getDeclaredFields()) {
            if (srcField.isAnnotationPresent(DatabaseJsonObject.class)) {
                srcField.setAccessible(true);
                setModelField(src, dst, srcField);
            }
            if (srcField.isAnnotationPresent(DatabaseJsonArray.class)) {
                srcField.setAccessible(true);
                setModelArrayField(src, dst, srcField);
            }
            if (srcField.isAnnotationPresent(DatabaseLabel.class)) {
                srcField.setAccessible(true);
                setModelLabelsField(src, dst, srcField);
            }
        }
    }

    private void setModelLabelsField(E src, M dst, Field srcField) {
        try {
            Field dstField = modelClass.getDeclaredField(srcField.getName());
            dstField.setAccessible(true);
            dstField.set(dst, convertStringToLabelList((String) srcField.get(src)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Couldn't convert to model label, entity: {}", src, e);
        }
    }


    private void setModelArrayField(E src, M dst, Field srcField) {
        String value = null;
        try {
            value = (String) srcField.get(src);
            if (StringUtils.isNotBlank(value)) {
                Type arrayType = new TypeToken<List<Object>>() {
                }.getType();

                List<Object> array = gson.fromJson(value, arrayType);

                Field dstField = modelClass.getDeclaredField(srcField.getName());
                dstField.setAccessible(true);
                dstField.set(dst, array);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.error("Couldn't convert to model array, entity: {}", src, e);
        }
    }

    private void setModelField(E src, M dst, Field srcField) {
        String value;
        try {
            value = (String) srcField.get(src);
            if (StringUtils.isNotBlank(value)) {
                Field dstField = modelClass.getDeclaredField(srcField.getName());
                Class dstClass = dstField.getType();
                Object json = gson.fromJson(value, dstClass);

                dstField.setAccessible(true);
                dstField.set(dst, dstClass.cast(json));
            }
        } catch (IllegalAccessException | NoSuchFieldException  e) {
            log.error("Couldn't convert to model, entity: {}", src, e);
        }
    }

    private void convertToJsonObjectField(M src, E dst) {
        for (Field dstField : entityClass.getDeclaredFields()) {
            if (dstField.isAnnotationPresent(DatabaseJsonObject.class)
                    || dstField.isAnnotationPresent(DatabaseJsonArray.class)) {
                dstField.setAccessible(true);
                setEntityField(src, dst, dstField);
            }
            if (dstField.isAnnotationPresent(DatabaseLabel.class)) {
                setEntityLabelField(src, dst, dstField);
            }
        }
    }

    private void setEntityLabelField(M src, E dst, Field dstField) {
        try {
            Field srcField = modelClass.getDeclaredField(dstField.getName());
            srcField.setAccessible(true);
            dstField.setAccessible(true);
            dstField.set(dst, convertLabelListToString((List<Label>) srcField.get(src)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Couldn't convert to entity, model: {}", src, e);
        }
    }

    private void setEntityField(M src, E dst, Field dstField) {
        try {
            Field srcField = modelClass.getDeclaredField(dstField.getName());
            srcField.setAccessible(true);
            Object value = srcField.get(src);
            if (Objects.isNull(value)) {
                dstField.set(dst, null);
            } else {
                dstField.set(dst, gson.toJson(value));
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.error("Couldn't convert to entity, model: {}", src, e);
        }
    }

    private List<Label> convertStringToLabelList(String value) {
        if (StringUtils.isNotBlank(value)) {
            String[] values = value.split(",");
            return Arrays.stream(values)
                    .map(Label::valueOf)
                    .collect(Collectors.toList());
        } else {
            return Lists.newArrayList();
        }
    }

    private String convertLabelListToString(List<Label> labels) {
        if (CollectionUtils.isEmpty(labels)) {
            return null;
        }
        return labels.stream().map(Label::name).collect(Collectors.joining(","));
    }

}
