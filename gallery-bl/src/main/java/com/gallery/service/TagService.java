package com.gallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gallery.gallerymodel.Tag;
import com.gallery.repository.TagRepository;

@Service
public class TagService {
    ;
    @Autowired
    TagRepository tagRepository;

    public void addTag(Tag tag) {
        tagRepository.save(tag);
    }

    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }

}
