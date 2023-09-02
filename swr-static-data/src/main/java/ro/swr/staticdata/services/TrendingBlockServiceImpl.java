package ro.swr.staticdata.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.TrendingBlock;
import ro.swr.staticdata.repository.TrendingBlockRepository;
import ro.swr.staticdata.repository.entities.TrendingBlockEntity;
import ro.swr.staticdata.services.mappers.ImageMapper;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrendingBlockServiceImpl implements TrendingBlockService {

    private final TrendingBlockRepository trendingRepository;

    private final ImageMapper<TrendingBlock, TrendingBlockEntity> trendingMapper;

    @Override
    @Transactional
    public List<TrendingBlock> findAllInTrending(boolean inTrending) {
        return trendingMapper.fromEntity(trendingRepository.findAllByInTrending(inTrending));
    }

    @Override
    public TrendingBlock saveTrendingBlock(TrendingBlock trendingBlock, List<MultipartFile> images) {
        TrendingBlockEntity entity = trendingMapper.toEntity(trendingBlock);
        entity.setImages(trendingMapper.fromFile(images));
        trendingRepository.save(entity);
        return trendingMapper.fromEntity(entity);
    }

    @Override
    public void deleteTrendingBlockById(Long id) {
        trendingRepository.deleteById(id);
    }

    @Override
    public void deleteTrendingBlockByInactive() {
        trendingRepository.deleteAllByInTrending(false);
    }

    @Override
    public TrendingBlock updateTrendingBlock(TrendingBlock trendingBlock) {
        trendingRepository.save(trendingMapper.toEntity(trendingBlock));
        return trendingBlock;
    }
}
