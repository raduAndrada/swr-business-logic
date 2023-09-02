package ro.swr.dishes.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.DatabaseJsonArray;
import model.DatabaseJsonObject;
import model.IngredientsJsonArray;
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

    @DatabaseJsonObject
    @Column(columnDefinition="text")
    private String nutritionalValue; // JSONObject

    @IngredientsJsonArray
    @Column(columnDefinition="text")
    private String ingredients; //JSONObject

    @CreationTimestamp
    private Instant creationDate;

    @UpdateTimestamp
    private Instant lastUpdate;
}
