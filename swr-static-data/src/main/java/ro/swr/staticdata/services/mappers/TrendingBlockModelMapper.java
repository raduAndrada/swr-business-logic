package ro.swr.staticdata.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ro.swr.staticdata.model.TrendingBlock;
import ro.swr.staticdata.repository.entities.TrendingBlockEntity;

import java.util.List;

@Component
public class TrendingBlockModelMapper extends ImageMapper<TrendingBlock, TrendingBlockEntity> {

    public TrendingBlockModelMapper(ModelMapper imageModelMapper) {
        super(imageModelMapper, TrendingBlock.class, TrendingBlockEntity.class);
    }

    public List<TrendingBlock> fromEntity(List<TrendingBlockEntity> entity, String lang) {
        return entity.stream().map(e -> fromEntity(e, lang)).toList();
    }

    public TrendingBlock fromEntity(TrendingBlockEntity entity, String lang) {
        var trendingBlock = super.fromEntity(entity);
        if (lang.equals("ro")) {
            trendingBlock.setShortDescription(entity.getShortDescriptionRO());
            trendingBlock.setDescription(entity.getDescriptionRO());
            trendingBlock.setTitle(entity.getTitleRO());
            trendingBlock.setName(entity.getNameRO());
        }
        return trendingBlock;
    }
}
