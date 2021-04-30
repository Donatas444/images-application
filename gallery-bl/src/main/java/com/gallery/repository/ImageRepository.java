package com.gallery.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gallery.gallerymodel.Image;

@Repository
public interface ImageRepository extends ImageRepoCustom {
    // @Override
    // List<Image> findImageByName(String name);

    List<Image> findByName(String name);
}
