package com.gallery.repository;

import com.gallery.gallerymodel.Image;

public interface ImageRepo extends ImageRepoCustom {
    Image getById(Long id);

    void deleteById(Long id);
}
