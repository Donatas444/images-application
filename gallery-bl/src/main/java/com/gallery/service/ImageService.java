package com.gallery.service;

import java.util.List;
import java.util.Optional;

import com.gallery.repository.InternalImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gallery.gallerymodel.Image;

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
    public void deleteImageById(Long id){
        imageRepository.deleteById(id);
    }
}