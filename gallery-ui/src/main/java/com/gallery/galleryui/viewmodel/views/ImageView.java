package com.gallery.galleryui.viewmodel.views;

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
    private Long id;
    private String name;
    private String description;
    private byte[] data;
    private byte[] thumbnail;
      private String tags;
    //  private Set<TagView> tags;
      private Set<ImageView> pictures;

    public ImageView(Image image) {
        this.id = image.getId();
        this.name = image.getName();
        this.description = image.getDescription();
        this.data = image.getData();
    }
    // public void addTag(TagView tag) {
    //     tags.add(tag);
    //     tag.getPictures().add(this);
    // }
    //
    // public void removeTag(TagView tag) {
    //     tags.remove(tag);
    //     pictures.remove(this);
    // }


}
