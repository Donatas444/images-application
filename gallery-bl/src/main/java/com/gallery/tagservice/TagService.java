package com.gallery.tagservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gallery.Tag;
import com.gallery.tagrepository.TagRepository;

@Service
public class TagService {

    @Autowired
    Tag tag;
    @Autowired
    TagRepository tagRepository;

    public Tag getTagById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            return tag.get();
        } else {
            throw new RuntimeException("Tag not found: " + id);
        }
    }
}
