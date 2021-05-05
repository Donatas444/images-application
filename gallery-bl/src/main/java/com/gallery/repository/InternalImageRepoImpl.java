package com.gallery.repository;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Image_;
import com.gallery.gallerymodel.Tag;
import com.gallery.gallerymodel.Tag_;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Component
public class InternalImageRepoImpl extends SimpleJpaRepository<Image, Long> implements InternalImageRepoCustom {

    EntityManager entityManager;

    InternalImageRepoImpl(EntityManager em) {
        super(Image.class, em);
        this.entityManager = em;
    }

    @Override
    public List<Image> searchByKeyword(String keyword) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Image> criteriaQuery = builder.createQuery(Image.class);
        Root<Image> root = criteriaQuery.from(Image.class);

        Join<Image, Tag> tagJoin = root.join(Image_.tags);

        ArrayList<Predicate> conditions = new ArrayList<>();

        conditions.add(builder.equal(root.get(Image_.name), keyword.toLowerCase(Locale.ROOT)));
        conditions.add(builder.like(builder.lower(root.get(Image_.description)), "%" + keyword.toLowerCase(Locale.ROOT) + "%"));
        conditions.add(builder.like(builder.lower(tagJoin.get(Tag_.name)), "%" + keyword.toLowerCase(Locale.ROOT) + "%"));

        criteriaQuery.where(builder.or(conditions.toArray(new Predicate[conditions.size()])));

        Query query = entityManager.createQuery(criteriaQuery);
        List<Image> eventList = query.getResultList();
        return eventList;
    }
}
