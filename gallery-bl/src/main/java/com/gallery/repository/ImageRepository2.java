package com.gallery.repository;

import java.awt.print.Book;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Image_;

@Repository
public interface ImageRepository2 extends CrudRepository<Image, Long>{


}
