package com.gallery.service;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Tag;
import com.gallery.repository.InternalImageRepo;
import com.gallery.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (name != null) {
            String[] splittedTags = name.split("\\s+");
            for (String tagNotNull : splittedTags) {
                if (tagRepository.findByNameTag(tagNotNull) != null && tagNotNull.equalsIgnoreCase(getExistingName(tagNotNull))) {
                    Tag existingTag = tagRepository.findByNameTag(tagNotNull);
                    image.addTag(existingTag);
                } else {
                    Tag newTag = new Tag();
                    newTag.setName(tagNotNull);
                    tagRepository.save(newTag);
                    image.addTag(newTag);
                }
            }
        } else {
            imageRepository.save(image);
        }
        imageRepository.save(image);
    }
}



