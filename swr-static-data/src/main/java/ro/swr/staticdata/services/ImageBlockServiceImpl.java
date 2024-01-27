package ro.swr.staticdata.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.ImageBlock;
import ro.swr.staticdata.repository.ImageBlockRepository;
import ro.swr.staticdata.repository.entities.ImageBlockEntity;
import ro.swr.staticdata.services.mappers.ImageMapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageBlockServiceImpl implements ImageBlockService {

    private final ImageBlockRepository imgRepository;
    private final ImageMapper<ImageBlock, ImageBlockEntity> imgMapper;

    @Override
    public ImageBlock saveImage(ImageBlock imageBlock, MultipartFile file) throws IOException {
        ImageBlockEntity entity;
        if (Objects.isNull(imageBlock)) {
            entity = imgMapper.fromFile(file);
        } else {
            entity = imgMapper.fromFile(imageBlock, file);
        }
        imgRepository.save(entity);
        log.info("Image successfully stored in db");

        return imgMapper.fromEntity(entity);
    }


    @Override
    @Transactional
    public List<ImageBlock> findAllByOrigin(String origin) {
        List<ImageBlockEntity> images;
        if (StringUtils.isNotBlank(origin)) {
            images = imgRepository.findAllByOrigin(origin);
        } else {
            images = (List<ImageBlockEntity>) imgRepository.findAll();
        }
        return imgMapper.fromEntity(images);
    }

    @Override
    public void deleteById(Long id) {
        imgRepository.deleteById(id);
    }

}
