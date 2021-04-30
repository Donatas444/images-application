package com.gallery.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Image_;

class ImageRepoImpl extends SimpleJpaRepository<Image, Long> implements ImageRepoCustom {

    public ImageRepoImpl(JpaEntityInformation<Image, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public ImageRepoImpl(Class<Image> domainClass, EntityManager em) {
        super(domainClass, em);
    }
    EntityManager entityManager;


    @Override
    public List<Image> findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Image> cq = cb.createQuery(Image.class);

        Root<Image> image = cq.from(Image.class);
        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(cb.equal(image.get(Image_.name), name));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
    }
    // @Override
    // public List<Image> findImageByNameAndDescription(String name, String description) {
    //
    //
    //     // constructor
    //
    //
    //     CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    //     CriteriaQuery<Image> criteriaQuery = criteriaBuilder.createQuery(Image.class);
    //
    //     Root<Image> image = criteriaQuery.from(Image.class);
    //     List<Predicate> predicates = new ArrayList<>();
    //
    //     if (name != null) {
    //         predicates.add(criteriaBuilder.equal(image.get(Image_.name), name));
    //     }
    //     if (description != null) {
    //         predicates.add(criteriaBuilder.like(image.get(Image_.description), "%" + description + "%"));
    //     }
    //     criteriaQuery.where(predicates.toArray(new Predicate[0]));
    //
    //     return entityManager.createQuery(criteriaQuery).getResultList();


        // Predicate imageNamePredicate = criteriaBuilder.equal(image.get(Image_.name), name);
        // Predicate descriptionPredicate = criteriaBuilder.like(image.get(Image_.description), "%" + description + "%");
        // criteriaQuery.where(imageNamePredicate, descriptionPredicate);
        //
        // TypedQuery<Image> query = entityManager.createQuery(criteriaQuery);
        // return query.getResultList();
  //  }

    // @Override
    // public List<Image> findImageByName(String name) {
    //     CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    //     CriteriaQuery<Image> criteriaQuery = criteriaBuilder.createQuery(Image.class);
    //
    //     Root<Image> image = criteriaQuery.from(Image.class);
    //   // List<Predicate> predicates = new ArrayList<>();
    //     Predicate imageNamePredicate = criteriaBuilder.equal(image.get(Image_.name), name);
    //     criteriaQuery.where(imageNamePredicate);
    //     TypedQuery<Image> query = entityManager.createQuery(criteriaQuery);
    //     return query.getResultList();
    // }




