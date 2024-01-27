package ro.swr.staticdata.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.TrendingBlock;

import java.util.List;

@RequestMapping(path = "/api/trending-blocks", produces = "application/json")
@CrossOrigin
public interface TrendingBlockController {
    @GetMapping
    List<TrendingBlock> findAllInTrending(@RequestParam Boolean inTrending, @RequestParam String lang);

    @PostMapping
    TrendingBlock save(@RequestPart TrendingBlock trendingBlock, @RequestPart("files") MultipartFile[] files);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable("id") Long id);

    @DeleteMapping
    ResponseEntity<Void> deleteAllInactive();
}
