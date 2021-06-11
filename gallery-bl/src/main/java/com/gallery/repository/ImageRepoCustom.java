package com.gallery.repository;

import com.gallery.gallerymodel.Image;
import com.gallery.repository.imageview.ImageViewShow;
import com.gallery.repository.imageview.TagViewShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Set;

@NoRepositoryBean
public interface ImageRepoCustom extends JpaRepository<Image, Long> {
    List<Long> searchByKeyword(String keyword);
    List<ImageViewShow> getAllImages();
    Set<TagViewShow> getImageTags(Long id);
}
