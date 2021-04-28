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

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    EntityManager entityManager = null;

    // constructor

    default List<Image> findImageByNameAndDescription(String name, String description) {
        assert false;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Image> cq = criteriaBuilder.createQuery(Image.class);

        Root<Image> image = cq.from(Image.class);
        Predicate imageNamePredicate = criteriaBuilder.equal(image.get("image"), name);
        Predicate descriptionPredicate = criteriaBuilder.like(image.get("description"), "%" + description + "%");
        cq.where(imageNamePredicate, descriptionPredicate);

        TypedQuery<Image> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

}
