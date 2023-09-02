package ro.swr.staticdata.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.TrendingBlock;
import ro.swr.staticdata.services.TrendingBlockService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrendingBlockControllerImpl implements TrendingBlockController {

    private final TrendingBlockService trendingService;

    @Override
    @GetMapping
    public List<TrendingBlock> findAllInTrending(Boolean inTrending) {
        return trendingService.findAllInTrending(inTrending);
    }

    @Override
    @PostMapping
    public TrendingBlock save(TrendingBlock trendingBlock, MultipartFile[] files) {
        return trendingService.saveTrendingBlock(trendingBlock, Arrays.asList(files));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(Long id) {
        trendingService.deleteTrendingBlockById(id);
        return ResponseEntity.ok()
                .build();
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleteAllInactive() {
        trendingService.deleteTrendingBlockByInactive();
        return ResponseEntity.ok()
                .build();
    }


}
