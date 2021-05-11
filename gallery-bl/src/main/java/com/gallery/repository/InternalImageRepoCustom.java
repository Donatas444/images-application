package com.gallery.repository;

import com.gallery.gallerymodel.Image;
import com.gallery.repository.imageview.ImageViewShow;
import com.gallery.repository.imageview.TagView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface InternalImageRepoCustom extends JpaRepository<Image, Long> {
    List<Long> searchByKeyword(String keyword);
    List<ImageViewShow> getAllImages();
    List<TagView> getImageTags(Long id);
}
