package com.gallery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Tag;
import com.gallery.repository.ImageRepository;
import com.gallery.repository.TagRepository;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    TagRepository tagRepository;

    public void addImage(Image image) {

        imageRepository.save(image);

    }

    public Image updateImage(Image image, Long imageId, Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElse(new Tag());
        image.setPictureTags(image.getPictureTags());
        image.setDescription(image.getDescription());
        imageRepository.save(image);
        return image;
    }

    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }

    public List<Image> getAllImages() {
        return (List<Image>) imageRepository.findAll();
    }

    public Image getImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            return image.get();
        } else {
            throw new RuntimeException("Image not found: " + id);
        }
    }
}
