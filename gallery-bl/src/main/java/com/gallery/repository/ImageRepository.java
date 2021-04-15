package com.gallery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gallery.gallerymodel.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {


}
