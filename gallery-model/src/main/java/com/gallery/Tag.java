package com.gallery;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;



@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tag;

    @ManyToMany (mappedBy = "pictureTags")
    Set<Image> taggedPictures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<Image> getTaggedPictures() {
        return taggedPictures;
    }

    public void setTaggedPictures(Set<Image> taggedPictures) {
        this.taggedPictures = taggedPictures;
    }
}
