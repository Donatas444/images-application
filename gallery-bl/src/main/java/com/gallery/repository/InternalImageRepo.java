package com.gallery.repository;


import com.gallery.gallerymodel.Image;
import com.gallery.repository.imageview.ImageViewShow;

import java.util.Optional;

public interface InternalImageRepo extends InternalImageRepoCustom {
   Image getById(Long id);
   void deleteById(Long id);
   Image save(Image image);
}
