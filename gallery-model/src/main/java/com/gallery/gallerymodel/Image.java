package com.gallery.gallerymodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;
    @Lob
    private byte[] thumbnail;
    @Lob
    private byte[] data;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "picture_tag", joinColumns = @JoinColumn(name = "picture_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public Image(Long aLong) {
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPictures().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPictures().remove(this);
    }
}
