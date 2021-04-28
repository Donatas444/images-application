package com.gallery.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gallery.gallerymodel.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    @Query(value = "SELECT * FROM tag WHERE tag.name = :name", nativeQuery = true)
    Tag findByNameTag(String name);
}
