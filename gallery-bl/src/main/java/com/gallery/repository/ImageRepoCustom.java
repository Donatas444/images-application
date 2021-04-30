package com.gallery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.gallery.gallerymodel.Image;

@NoRepositoryBean
public interface ImageRepoCustom extends JpaRepository<Image, Long> {
   // List<Image> findImageByNameAndDescription(String name, String description);
    List<Image> findByName(String name);

   // List<Image> findImageByName(String name);


}
