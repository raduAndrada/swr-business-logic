package ro.swr.dishes.repository.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.DatabaseJsonArray;
import model.DatabaseJsonObject;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity(name = "RECIPES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @DatabaseJsonObject(type = "ro.swr.dishes.model.NutritionalValue")
    private String nutritionalValue; // JSONObject

    @DatabaseJsonArray(type = "ro.swr.dishes.model.Ingredient")
    private String ingredients; //JSONObject

    @CreationTimestamp
    private Instant creationDate;

    @UpdateTimestamp
    private Instant lastUpdate;
}
