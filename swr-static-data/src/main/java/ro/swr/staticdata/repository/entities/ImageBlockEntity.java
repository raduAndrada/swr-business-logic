package ro.swr.staticdata.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.DatabaseJsonArray;

@Entity(name = "IMAGE_BLOCKS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageBlockEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private String origin;

    private String filename;

    private String contentType;

    @DatabaseJsonArray
    private String additionalData; //JSON store anything else needed

    @Lob
    private byte[] image;


}
