package com.gallery.gallerymodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tagId;
    @NaturalId
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private Set<Image> pictures = new HashSet<>();
}
