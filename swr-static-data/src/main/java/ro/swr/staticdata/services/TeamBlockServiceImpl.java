package ro.swr.staticdata.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.ImageBlock;
import ro.swr.staticdata.model.TeamBlock;
import ro.swr.staticdata.model.TrendingBlock;
import ro.swr.staticdata.repository.TeamBlockRepository;
import ro.swr.staticdata.repository.entities.ImageBlockEntity;
import ro.swr.staticdata.repository.entities.TeamBlockEntity;
import ro.swr.staticdata.repository.entities.TrendingBlockEntity;
import ro.swr.staticdata.services.mappers.ImageMapper;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TeamBlockServiceImpl implements TeamBlockService{

    private final ImageMapper<TeamBlock, TeamBlockEntity> teamMapper;
    private final TeamBlockRepository teamRepository;

    @Override
    @Transactional
    public List<TeamBlock> findAll() {
        return teamMapper.fromEntity((List<TeamBlockEntity>) teamRepository.findAll());
    }

    @Override
    public TeamBlock save(TeamBlock teamBlock, MultipartFile file) {
        ImageBlockEntity imageBlock = teamMapper.fromFile(file);
        TeamBlockEntity entity = teamMapper.toEntity(teamBlock);
        entity.setImage(imageBlock);
        teamRepository.save(entity);
        return teamMapper.fromEntity(entity);
    }
}
