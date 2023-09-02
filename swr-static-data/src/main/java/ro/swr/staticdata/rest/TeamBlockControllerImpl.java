package ro.swr.staticdata.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.TeamBlock;
import ro.swr.staticdata.services.TeamBlockService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamBlockControllerImpl implements TeamBlockController {

    private final TeamBlockService teamService;

    @Override
    @GetMapping
    public List<TeamBlock> findAll() {
        return teamService.findAll();
    }

    @Override
    @PostMapping
    public TeamBlock save(TeamBlock team, MultipartFile file) {
        return teamService.save(team, file);
    }


}
