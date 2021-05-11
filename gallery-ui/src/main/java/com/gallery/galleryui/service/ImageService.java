package com.gallery.galleryui.service;

import com.gallery.gallerymodel.Image;
import com.gallery.galleryui.viewmodel.views.ImageView;
import com.gallery.repository.InternalImageRepo;
import com.gallery.repository.imageview.ImageViewShow;
import com.gallery.repository.imageview.TagView;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    InternalImageRepo imageRepository;
    @Autowired
    TagService tagService;


    public void addImage(ImageView imageView) {
        Image image = new Image();
        image.setName(imageView.getName());
        image.setDescription(imageView.getDescription());
        image.setData(imageView.getData());
        image.setThumbnail(imageView.getThumbnail());
        imageRepository.save(image);
        addTags(image, imageView.getTags());


    }

    public List<TagView> getTImageTags(Long id) {
        return imageRepository.getImageTags(id);
    }


    public void addTags(Image image, String tagName) {
        tagService.addTags(image, tagName);
    }

    public List<ImageViewShow> getAllImages() {
        return imageRepository.getAllImages();
    }

    public ImageView getImageById(Long id) {
        return new ImageView(imageRepository.getById(id));

    }

    public void updateImage(Image image) {
        image.setDescription(image.getDescription());
        image.setName(image.getName());
        imageRepository.save(image);
    }

    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }

    public List<Long> searchByKeyword(String keyword) {
        return imageRepository.searchByKeyword(keyword);
    }

    public List<ImageViewShow> convertIdListToImageViewList(List<Long> imageIdList) {

        List<ImageViewShow> imagesList = new ArrayList<>();
        for (Long id : imageIdList) {
            Image image = imageRepository.getById(id);
            imagesList.add(new ImageViewShow(image.getId(), image.getName(), image.getDescription(), image.getThumbnail()));
        }
        return imagesList;
    }

    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
    }

    public BufferedImage createThumbnail(byte[] input) {

        BufferedImage scaledImage = Scalr.resize(createImageFromBytes(input), 150);
        return scaledImage;
    }

    public BufferedImage createImageFromBytes(byte[] input) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
        try {
            return ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] bufferedImageToByteArray(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }
}