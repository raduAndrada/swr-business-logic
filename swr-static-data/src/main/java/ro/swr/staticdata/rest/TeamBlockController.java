package ro.swr.staticdata.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.TeamBlock;

import java.util.List;

@RequestMapping(path = "/api/team-blocks", produces = "application/json")
@CrossOrigin
public interface TeamBlockController {

    @GetMapping
    List<TeamBlock> findAll();

    @PostMapping
    TeamBlock save(@RequestPart TeamBlock team, @RequestPart("file") MultipartFile file);
}
