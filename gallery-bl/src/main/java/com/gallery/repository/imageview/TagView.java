package com.gallery.repository.imageview;

import lombok.Getter;

@Getter
public class TagView {

    private Long id;
    private String name;



    public TagView(String name) {
        this.name = name;
    }
}
