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
    EntityManager entityManager = null;

    // constructor

    default List<Image> findImageByNameAndDescription(String name, String description) {
        assert false;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Image> cq = criteriaBuilder.createQuery(Image.class);

        Root<Image> image = cq.from(Image.class);
        Predicate imageNamePredicate = criteriaBuilder.equal(image.get(Image_.name), name);
        Predicate descriptionPredicate = criteriaBuilder.like(image.get("description"), "%" + description + "%");
        cq.where(imageNamePredicate, descriptionPredicate);

        TypedQuery<Image> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
