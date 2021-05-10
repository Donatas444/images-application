package com.gallery.repository.imagerepository;


import com.gallery.gallerymodel.Image;

import java.util.Optional;

public interface InternalImageRepo extends InternalImageRepoCustom {
   Optional<Image> findById(Long id);
   void deleteById(Long id);
}
