package com.gallery.repository.imageview;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageView {
    private long id;
    private String name;
    private String description;
    private byte[] thumbnail;

    public ImageView(Long id, String name, String description, byte[] thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }


}
