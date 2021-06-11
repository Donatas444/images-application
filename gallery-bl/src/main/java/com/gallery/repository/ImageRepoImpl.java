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
import java.util.*;

@Component
public class ImageRepoImpl extends SimpleJpaRepository<Image, Long> implements ImageRepoCustom {

    EntityManager entityManager;

    ImageRepoImpl(EntityManager em) {
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
        tuples.stream().forEach(tuple -> idList.add(tuple.get(0, Long.class)));
        return idList;
    }

    @Override
    public List<ImageViewShow> getAllImages() {
        List<ImageViewShow> imageList = new ArrayList<>();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = builder.createTupleQuery();
        Root<Image> root = criteriaQuery.from(Image.class);
        Path idPath = root.get(Image_.id);
        Path namePath = root.get(Image_.name);
        Path descriptionPath = root.get(Image_.description);
        Path thumbnailPath = root.get(Image_.thumbnail);
        criteriaQuery.multiselect(idPath, namePath, descriptionPath, thumbnailPath);
        criteriaQuery.orderBy(builder.desc(root.get(Image_.id)));
        List<Tuple> tuples = entityManager.createQuery(criteriaQuery).getResultList();
        tuples.stream().forEach(tuple -> imageList.add(new ImageViewShow(
                tuple.get(0, Long.class),
                tuple.get(1, String.class),
                tuple.get(2, String.class),
                tuple.get(3, byte[].class))));
        return imageList;
    }

    @Override
    public Set<TagViewShow> getImageTags(Long id) {
        Image image = entityManager.find(Image.class, id);
        Set<Tag> tags = image.getTags();
        Set<TagViewShow> tagSet = new HashSet<>();
        tags.stream().forEach(tag -> tagSet.add(new TagViewShow(tag.getName())));
        return tagSet;
    }
}

