package ro.swr.dishes.repository.entities;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity(name = "DISHES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Expose
    private String name;

    @Expose
    @IngredientsJsonArray
    @Column(columnDefinition="text")
    private String ingredients;

    @Expose
    private BigDecimal price;

    @Expose
    @DatabaseJsonObject
    @Column(columnDefinition="text")
    private String nutritionalValue; // save as JSONObject in the db

    @Expose
    @DatabaseLabel
    private String labels; // list of labels

    @Expose
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_name")
    private SubcategoryEntity subcategory;

    @CreationTimestamp
    private Instant creationDate;

    @UpdateTimestamp
    private Instant lastUpdate;


}
