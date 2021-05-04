package com.gallery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.gallery.gallerymodel.Image;

@NoRepositoryBean
public interface InternalImageRepoCustom extends JpaRepository<Image, Long>{
    List<Image> search(String keyword);
 //   List<Image> findByName(String name);

}
