package ro.swr.staticdata.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.DatabaseJsonArray;

@Entity(name = "TEAM_BLOCKS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamBlockEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String name;

    private String description;

    @DatabaseJsonArray
    private String socialMedia; // stored as json array

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private ImageBlockEntity image;


}
