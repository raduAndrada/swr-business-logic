package ro.swr.staticdata.services;

import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.ImageBlock;

import java.io.IOException;
import java.util.List;

public interface ImageBlockService {

    ImageBlock saveImage(ImageBlock imageBlock, MultipartFile file) throws IOException;

    List<ImageBlock> findAllByOrigin(String origin);

    void deleteById(Long id);

}
