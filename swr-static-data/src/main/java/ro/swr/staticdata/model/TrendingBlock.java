package ro.swr.staticdata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrendingBlock {

    private Long id;

    private String name;

    private String shortDescription;

    private String date;

    private String author;

    private String description;

    private boolean inTrending;

    private List<ImageBlock> images;

    private String title;

}
