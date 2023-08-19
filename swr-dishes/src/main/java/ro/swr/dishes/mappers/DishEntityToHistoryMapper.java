package ro.swr.dishes.mappers;

import org.modelmapper.ModelMapper;
import ro.swr.dishes.repository.entities.DishEntity;
import ro.swr.dishes.repository.entities.DishHistoryEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DishEntityToHistoryMapper {

    private static ModelMapper mapper = new ModelMapper();

    public DishHistoryEntity toHistory(DishEntity dish){
        return mapper.map(dish , DishHistoryEntity.class);
    }

    public List<DishHistoryEntity> toHistory(List<DishEntity> dishes){
       return dishes.stream().map(this:: toHistory).collect(Collectors.toList());
    }

    public DishEntity fromHistory(DishHistoryEntity history){
        DishEntity dish =  mapper.map(history , DishEntity.class);
        dish.setId(null);
        return dish;
    }

    public List<DishEntity> fromHistory(List<DishHistoryEntity> dishes){
        return dishes.stream().map(this:: fromHistory).collect(Collectors.toList());
    }
}
