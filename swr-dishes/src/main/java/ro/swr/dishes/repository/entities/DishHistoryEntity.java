package ro.swr.dishes.repository.entities;

import com.google.gson.annotations.Expose;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Category;
import model.DatabaseJsonObject;
import model.DatabaseLabel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity(name = "DISHES_HISTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishHistoryEntity {

    @Id
    private Long id;

    @Expose
    private String name;

    @Expose
    @OneToMany
    private List<IngredientEntity> ingredients;

    @Expose
    private BigDecimal price;

    @Expose
    @DatabaseJsonObject(type = "ro.swr.dishes.model.NutritionalValue")
    private String nutritionalValue; // save as JSONObject in the db

    @Expose
    @DatabaseLabel
    private String labels; // list of labels

    @Expose
    private Category category;

    @CreationTimestamp
    private Instant creationDate;

    @UpdateTimestamp
    private Instant lastUpdate;


}
