package ro.swr.staticdata.repository.entities;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "TRENDING_BLOCKS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrendingBlockEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String nameRO;

    private String shortDescription;

    private String shortDescriptionRO;

    private String date;

    private String author;

    private String description;

    private String descriptionRO;

    private boolean inTrending;

    private String title;

    private String titleRO;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ImageBlockEntity> images;




}
