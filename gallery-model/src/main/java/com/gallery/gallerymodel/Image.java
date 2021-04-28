package com.gallery.gallerymodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Length(max = 20)
    private String description;
    @Lob
    private byte[] thumbnail;
    @Lob
    private byte[] data;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "picture_tag", joinColumns = @JoinColumn(name = "picture_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPictures().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPictures().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        return id != null && id.equals(((Image) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
