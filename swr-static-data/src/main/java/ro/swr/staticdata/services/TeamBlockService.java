package ro.swr.staticdata.services;

import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.TeamBlock;

import java.util.List;

public interface TeamBlockService {

    List<TeamBlock> findAll();

    TeamBlock save(TeamBlock teamBlock , MultipartFile file);
}
