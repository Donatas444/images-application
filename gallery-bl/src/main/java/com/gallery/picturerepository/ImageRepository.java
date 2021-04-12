package com.gallery.picturerepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gallery.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

}
