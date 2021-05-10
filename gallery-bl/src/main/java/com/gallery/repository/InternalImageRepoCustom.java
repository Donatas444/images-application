package com.gallery.repository;

import com.gallery.gallerymodel.Image;
import com.gallery.repository.imageview.ImageView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface InternalImageRepoCustom extends JpaRepository<Image, Long> {
    List<Image> searchByKeyword(String keyword);
    List<ImageView> getAllImages();
}
