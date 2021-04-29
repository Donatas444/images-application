package com.gallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.gallery.gallerymodel.Image;

@NoRepositoryBean
public interface ImageRepoCustom extends JpaRepository<Image, Long> {

}
