package com.gallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Tag;
import com.gallery.repository.InternalImageRepo;
import com.gallery.repository.TagRepository;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    InternalImageRepo imageRepository;

    public void addTag(Tag tag) {
        tagRepository.save(tag);
    }

    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }

    public void getTagById(Long id) {
        tagRepository.findById(id);
    }

    public void getTagByName(String name) {
        tagRepository.findByNameTag(name);
    }

    public String getExistingName(String name) {

        tagRepository.findByNameTag(name);
        Tag existingTag = tagRepository.findByNameTag(name);
        return existingTag.getName();
    }

    public void ifTagExists(Image image, String name) {
        if (tagRepository.findByNameTag(name) != null && name.equalsIgnoreCase(getExistingName(name))) {
            Tag existingTag = tagRepository.findByNameTag(name);
            image.addTag(existingTag);
            imageRepository.save(image);
        } else if (name == null) {
            imageRepository.save(image);
        } else {
            Tag tag = new Tag();
            tag.setName(name);
            tagRepository.save(tag);
            image.addTag(tag);
            imageRepository.save(image);
        }
    }
}


