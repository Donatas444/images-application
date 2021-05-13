package com.gallery.galleryui.viewmodel.views;

import com.gallery.gallerymodel.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TagView {
    private Long tagId;
    private String name;
    private Set<ImageView> pictures;

    public TagView(Tag tag) {
        this.tagId = tag.getTagId();
        this.name = tag.getName();
    }
}
