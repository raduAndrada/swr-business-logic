package ro.swr.staticdata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageBlock {

    private Long id;

    private String title;

    private String description;

    private String origin;

    private String additionalData; //JSON store anything else needed

    private byte[] image;

    private String filename;

    private String contentType;

}
