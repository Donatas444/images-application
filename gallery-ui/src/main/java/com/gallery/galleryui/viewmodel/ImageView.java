package com.gallery.galleryui.viewmodel;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ImageView {
    private long id;
    private String name;
    private String description;
    private byte[] data;
    private byte[] thumbnail;
    private Set<Tag> tag;

    public ImageView(Image image) {
        this.id = image.getId();
        this.name = image.getName();
        this.description = image.getDescription();
        this.thumbnail = image.getThumbnail();
        this.data = image.getData();
        this.tag = image.getTags();
    }

    public ImageView(long id, String name, String description, byte[] data, byte[] thumbnail, Set<Tag> tag) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.data = data;
        this.thumbnail = thumbnail;
        this.tag = tag;
    }
}
