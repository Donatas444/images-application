package com.gallery.galleryui.viewmodel.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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



}
