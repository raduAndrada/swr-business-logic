package ro.swr.dishes.repository.entities;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "SUBCATEGORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoryEntity {

    @Id
    private String name;

    @Expose
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_name")
    private CategoryEntity category;

    private String icon;

//    SANDWICHES,
//    TOGO,
//    DRINKS,
//    BRUNCH
}
