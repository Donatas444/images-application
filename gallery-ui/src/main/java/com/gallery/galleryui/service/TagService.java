package com.gallery.galleryui.service;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Tag;
import com.gallery.repository.ImageRepo;
import com.gallery.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Set;

@Service
public class TagService  implements Serializable {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    ImageRepo imageRepository;

    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    public String getExistingName(String name) {
        tagRepository.findByName(name);
        Tag existingTag = tagRepository.findByName(name);
        return existingTag.getName();
    }

    public void addTags(Image image, String name) {
        if (name != null) {
            saveTagsAndAddToImage(image, name);
        } else {
            imageRepository.save(image);
        }
    }

    public void saveTagsAndAddToImage(Image image, String name) {
        String[] splitTags = name.split("\\s+");
        for (String tagNotNull : splitTags) {
            if (tagRepository.findByName(tagNotNull) != null && tagNotNull.equalsIgnoreCase(getExistingName(tagNotNull))) {
                Tag existingTag = tagRepository.findByName(tagNotNull);
                image.addTag(existingTag);
            } else {
                Tag newTag = new Tag();
                newTag.setName(tagNotNull);
                tagRepository.save(newTag);
                image.addTag(newTag);
            }
        }
        imageRepository.save(image);
    }
    
    public void removeTag(Long id, String name) {
        Image image = imageRepository.getById(id);
        Tag tag = getTagByName(name);
        Set<Tag> tags = image.getTags();
        try {
            for (Tag tagInSet : tags) {
                if (tagInSet.getName().equals(tag.getName())) {
                    tags.remove(tagInSet);
                    tagInSet.getPictures().remove(image);
                    imageRepository.save(image);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




