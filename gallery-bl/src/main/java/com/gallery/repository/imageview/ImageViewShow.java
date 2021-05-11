package com.gallery.repository.imageview;

import com.gallery.gallerymodel.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class ImageViewShow {
    private long id;
    private String name;
    private String description;
    private byte[] thumbnail;
    private byte[] data;
    private String tags;

    public ImageViewShow(Long id, String name, String description, byte[] thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }


}
