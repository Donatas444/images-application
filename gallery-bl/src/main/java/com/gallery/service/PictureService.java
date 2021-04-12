package com.gallery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gallery.Image;
import com.gallery.Tag;
import com.gallery.picturerepository.ImageRepository;
import com.gallery.tagrepository.TagRepository;

@Service
public class PictureService {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    Tag tag;

    public Image addImage(Image image, Long id) {
        Tag tag = tagRepository.findById(id).orElse(new Tag());
        image.setName(null);
        image.setDescription(null);
        imageRepository.save(image);
        return image;
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
