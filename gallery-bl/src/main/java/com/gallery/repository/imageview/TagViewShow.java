package com.gallery.repository.imageview;

import lombok.Getter;

@Getter
public class TagViewShow {

    private Long id;
    private String name;



    public TagViewShow(String name) {
        this.name = name;
    }
}
