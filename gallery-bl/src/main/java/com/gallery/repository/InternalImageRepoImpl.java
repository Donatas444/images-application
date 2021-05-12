package com.gallery.repository;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Image_;
import com.gallery.gallerymodel.Tag;
import com.gallery.gallerymodel.Tag_;
import com.gallery.repository.imageview.ImageViewShow;
import com.gallery.repository.imageview.TagViewShow;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;


@Component
public class InternalImageRepoImpl extends SimpleJpaRepository<Image, Long> implements InternalImageRepoCustom {

    EntityManager entityManager;

    InternalImageRepoImpl(EntityManager em) {
        super(Image.class, em);
        this.entityManager = em;
    }

    @Override
    public List<Long> searchByKeyword(String keyword) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = builder.createTupleQuery();
        Root<Image> root = criteriaQuery.from(Image.class);


        Join<Image, Tag> tagJoin = root.join(Image_.tags, JoinType.LEFT);
        Path<Long> idPath = root.get(Image_.id);
        criteriaQuery.multiselect(idPath);



        ArrayList<Predicate> conditions = new ArrayList<>();

        conditions.add(builder.like(builder.lower(root.get(Image_.name)), "%" + keyword.toLowerCase(Locale.ROOT) + "%"));
        conditions.add(builder.like(builder.lower(root.get(Image_.description)), "%" + keyword.toLowerCase(Locale.ROOT) + "%"));
        conditions.add(builder.like(builder.lower(tagJoin.get(Tag_.name)), "%" + keyword.toLowerCase(Locale.ROOT) + "%"));

        criteriaQuery.where(builder.or(conditions.toArray(new Predicate[conditions.size()])));
        criteriaQuery.distinct(true);
        List<Long> idList = new ArrayList<>();

        List<Tuple> tuples = entityManager.createQuery(criteriaQuery).getResultList();

        for (Tuple tuple : tuples) {
            idList.add(tuple.get(0, Long.class));
        }

        return idList;
    }

    @Override
    public List<ImageViewShow> getAllImages() {
        List<ImageViewShow> imageList = new ArrayList<>();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteria = builder.createTupleQuery();
        Root root = criteria.from(Image.class);
        Path<Long> idPath = root.get(Image_.id);
        Path<Long> namePath = root.get(Image_.name);
        Path<Long> descriptionPath = root.get(Image_.description);
        Path<Long> thumbnailPath = root.get(Image_.thumbnail);
        criteria.multiselect(idPath, namePath, descriptionPath, thumbnailPath);
        criteria.orderBy(builder.desc(root.get(Image_.id)));
        List<Tuple> tuples = entityManager.createQuery(criteria).getResultList();

        for (Tuple tuple : tuples) {
            imageList.add(new ImageViewShow(tuple.get(0, Long.class), tuple.get(1, String.class), tuple.get(2, String.class), tuple.get(3, byte[].class)));
        }

        return imageList;
    }

    @Override
    public List<TagViewShow> getImageTags(Long id) {
        List<TagViewShow> tagViews = new ArrayList<>();
        Image image = entityManager.find(Image.class, id);
        Set<Tag> tags = image.getTags();

        for (Tag tag : tags) {
            tagViews.add(new TagViewShow(tag.getName()));
        }
        return tagViews;
    }


}

