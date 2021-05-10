package com.gallery.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.gallery.repository.imagerepository.InternalImageRepo;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gallery.gallerymodel.Image;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;

import javax.imageio.ImageIO;

@Service
public class ImageService {

    @Autowired
    InternalImageRepo imageRepository;

    public void addImage(Image image) {
        imageRepository.save(image);

    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
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