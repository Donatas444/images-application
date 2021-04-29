package com.gallery.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.gallery.gallerymodel.Image;

class ImageRepoImpl extends SimpleJpaRepository<Image, Long> implements ImageRepoCustom {
    public ImageRepoImpl(JpaEntityInformation<Image, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public ImageRepoImpl(Class<Image> domainClass, EntityManager em) {
        super(domainClass, em);
    }
}
