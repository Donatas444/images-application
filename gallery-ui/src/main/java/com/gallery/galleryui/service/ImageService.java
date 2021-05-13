package com.gallery.galleryui.service;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Tag;
import com.gallery.galleryui.viewmodel.views.ImageView;
import com.gallery.repository.InternalImageRepo;
import com.gallery.repository.TagRepository;
import com.gallery.repository.imageview.ImageViewShow;
import com.gallery.repository.imageview.TagViewShow;
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
import java.util.Set;

@Service
public class ImageService {

    @Autowired
    InternalImageRepo imageRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TagService tagService;


    public void addImage(ImageView imageView, String tagName) {
        Image image = new Image();
        image.setName(imageView.getName());
        image.setDescription(imageView.getDescription());
        image.setData(imageView.getData());
        image.setThumbnail(imageView.getThumbnail());
        imageRepository.save(image);
        tagService.addTags(image, tagName);
    }

    public Set<TagViewShow> getImageTags(Long id) {
        return imageRepository.getImageTags(id);
    }

    public List<ImageViewShow> getAllImages() {
        return imageRepository.getAllImages();
    }

    public ImageView getImageById(Long id) {
        return new ImageView(imageRepository.getById(id));

    }



    public void updateImage(Long id, ImageView imageView, String tagName) {
       Image image = imageRepository.getById(id);

        image.setDescription(imageView.getDescription());
        image.setName(imageView.getName());
        tagService.addTags(image, tagName);
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