package com.gallery.repository;


import com.gallery.gallerymodel.Image;

public interface InternalImageRepo extends InternalImageRepoCustom {
   Image getById(Long id);
   void deleteById(Long id);
   Image save(Image image);
}
