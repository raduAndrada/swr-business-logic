package ro.swr.staticdata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamBlock {

    private Long id;

    private String title;

    private String name;

    private Map<String, String> socialMedia;

    private ImageBlock image;

    private List<String> description;

}
