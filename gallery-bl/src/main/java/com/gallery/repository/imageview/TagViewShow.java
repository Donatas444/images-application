package com.gallery.repository.imageview;

import lombok.Getter;

@Getter
public class TagViewShow {

    private Long tagId;
    private String name;

    public TagViewShow(String name) {
        this.name = name;
    }
}
