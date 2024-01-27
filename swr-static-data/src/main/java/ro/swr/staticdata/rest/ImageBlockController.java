package ro.swr.staticdata.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.ImageBlock;

import java.io.IOException;
import java.util.List;

@RequestMapping(path = "/api/image-blocks", produces = "application/json")
@CrossOrigin
public interface ImageBlockController {

    @GetMapping
    List<ImageBlock> findAllByOrigin(@RequestParam String origin);

    @PostMapping
    ImageBlock uploadImage(@RequestPart(required = false) ImageBlock imageBlock, @RequestPart MultipartFile file) throws IOException;

    @DeleteMapping("/id")
    ResponseEntity<Void> deleteById(@PathVariable Long id);


}
