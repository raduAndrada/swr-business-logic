package ro.swr.staticdata.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.ImageBlock;
import ro.swr.staticdata.services.ImageBlockService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageBlockControllerImpl implements ImageBlockController {

    private final ImageBlockService imageService;

    @GetMapping
    public List<ImageBlock> findAllByOrigin(String origin) {
        return imageService.findAllByOrigin(origin);
    }

    @PostMapping
    public ImageBlock uploadImage(ImageBlock imageBlock, MultipartFile file) throws IOException {
        return imageService.saveImage(imageBlock, file);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteById(Long id) {
        imageService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
