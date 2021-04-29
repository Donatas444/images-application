package com.gallery.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Image_;

@Repository
public interface ImageRepository extends ImageRepoCustom {

}
