package ro.swr.staticdata.services.mappers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.ImageBlock;
import ro.swr.staticdata.repository.entities.ImageBlockEntity;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static ro.swr.staticdata.services.mappers.ImageUtils.compressImage;

@Component
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ImageMapper<M, E> {

    private ModelMapper imageModelMapper;

    private Class<M> modelClass;
    private Class<E> entityClass;

    public ImageMapper(ModelMapper imageModelMapper) {
        this.imageModelMapper = imageModelMapper;
    }

    public M fromEntity(E entity) {
        return imageModelMapper.map(entity, modelClass);
    }

    public List<M> fromEntity(List<E> entity) {
        return entity.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    public E toEntity(M model) {
        return imageModelMapper.map(model, entityClass);
    }

    public List<E> toEntity(List<M> model) {
        return model.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public ImageBlockEntity fromFile(ImageBlock imageBlock, MultipartFile file) throws IOException {
        ImageBlockEntity entity = imageModelMapper.map(imageBlock, ImageBlockEntity.class);
        entity.setFilename(file.getOriginalFilename());
        entity.setContentType(file.getContentType());
        entity.setImage(compressImage(file.getBytes()));
        return entity;
    }

    public List<ImageBlockEntity> fromFile(List<MultipartFile> files) {
        return files.stream().map(this::fromFile).collect(Collectors.toList());
    }

    public ImageBlockEntity fromFile(MultipartFile file) {
        try {
            return ImageBlockEntity.builder()
                    .filename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .image(compressImage(file.getBytes()))
                    .build();
        } catch (IOException e) {
            log.error("File: {} could not be saved.", file.getOriginalFilename(), e);
        }
        return null;
    }


}
