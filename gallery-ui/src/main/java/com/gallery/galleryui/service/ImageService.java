package com.gallery.galleryui.service;

import com.gallery.gallerymodel.Image;
import com.gallery.galleryui.viewmodel.views.ImageView;
import com.gallery.repository.InternalImageRepo;
import com.gallery.repository.imageview.ImageViewShow;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        String tagName = imageView.getTags();





    }

        public void addTags(Image image, String tagName){
            tagService.addTags(image, tagName);
        }

    public List<ImageViewShow> getAllImages() {
        return imageRepository.getAllImages();
    }

    public Image getImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            return image.get();
        } else {
            throw new RuntimeException("Image not found: " + id);
        }
    }

    public void updateImage(Image image) {
        image.setDescription(image.getDescription());
        image.setName(image.getName());
        imageRepository.save(image);
    }

    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }

    public List<Image> searchByKeyword(String keyword) {
        return imageRepository.searchByKeyword(keyword);
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