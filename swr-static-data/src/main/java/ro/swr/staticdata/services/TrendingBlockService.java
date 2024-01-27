package ro.swr.staticdata.services;

import org.springframework.web.multipart.MultipartFile;
import ro.swr.services.SwrServiceBase;
import ro.swr.staticdata.model.TrendingBlock;
import ro.swr.staticdata.repository.entities.TrendingBlockEntity;

import java.util.List;

public interface TrendingBlockService {

    List<TrendingBlock> findAllInTrending(boolean inTrending, String lang);

    TrendingBlock saveTrendingBlock(TrendingBlock trendingBlock, List<MultipartFile> images);

    void deleteTrendingBlockById(Long trendingBlockId);

    void deleteTrendingBlockByInactive();

    TrendingBlock updateTrendingBlock(TrendingBlock trendingBlock);
}
