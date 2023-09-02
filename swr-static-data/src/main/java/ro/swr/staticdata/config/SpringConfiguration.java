package ro.swr.staticdata.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.annotation.Immutable;
import org.springframework.web.multipart.MultipartFile;
import ro.swr.staticdata.model.ImageBlock;
import ro.swr.staticdata.model.TeamBlock;
import ro.swr.staticdata.model.TrendingBlock;
import ro.swr.staticdata.repository.TeamBlockRepository;
import ro.swr.staticdata.repository.TrendingBlockRepository;
import ro.swr.staticdata.repository.entities.ImageBlockEntity;
import ro.swr.staticdata.repository.entities.TeamBlockEntity;
import ro.swr.staticdata.repository.entities.TrendingBlockEntity;
import ro.swr.staticdata.services.mappers.ImageMapper;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static ro.swr.staticdata.services.mappers.ImageUtils.decompressImage;

@Configuration
public class SpringConfiguration {

    @Bean
    public ImageMapper<TrendingBlock, TrendingBlockEntity> trendingMapper(ModelMapper imageModelMapper) {
        return new ImageMapper(imageModelMapper, TrendingBlock.class, TrendingBlockEntity.class);
    }

    @Bean
    public ImageMapper<ImageBlock, ImageBlockEntity> imgMapper(ModelMapper imageModelMapper) {
        return new ImageMapper(imageModelMapper, ImageBlock.class, ImageBlockEntity.class);
    }

    @Bean
    public ImageMapper<TeamBlock, TeamBlockEntity> teamMapper(ModelMapper imageModelMapper) {
        return new ImageMapper(imageModelMapper, TeamBlock.class, TeamBlockEntity.class);
    }

    @Bean
    public ModelMapper imageModelMapper() {
        ModelMapper mapper = new ModelMapper();
        Gson gson = new Gson();

        TypeMap<ImageBlockEntity, ImageBlock> imageMapper = mapper.createTypeMap(ImageBlockEntity.class, ImageBlock.class);
        Converter<byte[], byte[]> compressedToDecompressed = c -> decompressImage(c.getSource());
        imageMapper.addMappings(
                modelMapper ->
                    modelMapper.using(compressedToDecompressed).map(ImageBlockEntity::getImage, ImageBlock::setImage)
                );

        TypeMap<TeamBlockEntity, TeamBlock> teamMapper = mapper.createTypeMap(TeamBlockEntity.class, TeamBlock.class);
        Converter<String, Map<String,String>> socialMediaConverter = c -> gson.fromJson(c.getSource(), Map.class);
        Converter<String, List<String>> descriptionConverter = c -> gson.fromJson(c.getSource(), List.class);
        teamMapper.addMappings(
                modelMapper -> {
                    modelMapper.using(socialMediaConverter).map(TeamBlockEntity::getSocialMedia, TeamBlock::setSocialMedia);
                    modelMapper.using(descriptionConverter).map(TeamBlockEntity::getDescription, TeamBlock::setDescription);
                }
        );

        TypeMap<TeamBlock, TeamBlockEntity> teamEntityMapper = mapper.createTypeMap(TeamBlock.class, TeamBlockEntity.class);
        Converter<Map<String,String>, String> socialMediaConverterFromJson = c -> gson.toJson(c.getSource(), Map.class);
        Converter<Map<String,String>, String> descriptionConverterFromJson = c -> gson.toJson(c.getSource(), Map.class);
        teamEntityMapper.addMappings(
                modelMapper -> {
                    modelMapper.using(socialMediaConverterFromJson).map(TeamBlock::getSocialMedia, TeamBlockEntity::setSocialMedia);
                    modelMapper.using(descriptionConverterFromJson).map(TeamBlock::getDescription, TeamBlockEntity::setDescription);
                }
        );

//        TypeMap<ImageBlock, ImageBlockEntity> toCompressionMapper = mapper.createTypeMap(ImageBlock.class, ImageBlockEntity.class);
//        Converter<byte[], byte[]> decompressedToCompressed = c -> compressImage(c.getSource());
//        toCompressionMapper.addMappings(
//                modelMapper ->
//                        modelMapper.using(decompressedToCompressed).map(ImageBlock::getImage, ImageBlockEntity::setImage)
//        );
        return mapper;
    }


    @Bean
    public CommandLineRunner dataLoader(TeamBlockRepository teamRepository,
                                        ImageMapper<ImageBlock, ImageBlockEntity> imgMapper,
                                        TrendingBlockRepository trendingRepository
                                        ) throws IOException {
        Gson gson = new Gson();
        byte[] data = this.getClass().getClassLoader()
                .getResourceAsStream("images/andrada.jpg").readAllBytes();
        MultipartFile file = new CustomMultipartFile(data);
        ImageBlockEntity entity = imgMapper.fromFile(file);
        TeamBlockEntity team1 = TeamBlockEntity.builder()
                .name("Radu Andrada")
                .title("Owner")
                .description(gson.toJson(ImmutableList.of("This a paragraph", "this is another one", "and another one")))
                .image(entity)
                .socialMedia(
                        gson.toJson(ImmutableMap.of("facebook", "https://www.facebook.com/andrada.radu.7/",
                                "instagram", "https://www.instagram.com/aandradaa08/"),
                                Map.class))
                .build();

        byte[] data1 = this.getClass().getClassLoader()
                .getResourceAsStream("images/outside1.jpg").readAllBytes();
        byte[] data2 = this.getClass().getClassLoader()
                .getResourceAsStream("images/inside1.jpg").readAllBytes();
        byte[] data3 = this.getClass().getClassLoader()
                .getResourceAsStream("images/dinner1.jpg").readAllBytes();
        ImageBlockEntity entity1 = imgMapper.fromFile(new CustomMultipartFile(data1));
        ImageBlockEntity entity2 = imgMapper.fromFile(new CustomMultipartFile(data2));
        ImageBlockEntity entity3 = imgMapper.fromFile(new CustomMultipartFile(data3));
        TrendingBlockEntity trending1 = TrendingBlockEntity.builder()
                .date("01/09/2023")
                .author("Andrada Radu")
                .inTrending(true)
                .shortDescription("Grand Opening happening soon. Stay tuned in for more details")
                .title("Opening in October")
                .description("More to come as soon as possible")
                .images(List.of(entity1, entity2, entity3))
                .build();
        return args -> {
           // teamRepository.save(team1);
//            trendingRepository.save(trending1);
        };
    }

    @AllArgsConstructor
    private class CustomMultipartFile implements MultipartFile{

        private byte[] input;

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return null;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return input == null || input.length == 0;
        }

        @Override
        public long getSize() {
            return input.length;
        }

        @Override
        public byte[] getBytes()  {
            return input;
        }

        @Override
        public InputStream getInputStream()  {
            return new ByteArrayInputStream(input);
        }

        @Override
        public void transferTo(File destination) throws IOException, IllegalStateException {
            try(FileOutputStream fos = new FileOutputStream(destination)) {
                fos.write(input);
            }
        }

        @Override
        public Resource getResource() {
            return MultipartFile.super.getResource();
        }

        @Override
        public void transferTo(Path dest) throws IOException, IllegalStateException {
            MultipartFile.super.transferTo(dest);
        }
    }

}
