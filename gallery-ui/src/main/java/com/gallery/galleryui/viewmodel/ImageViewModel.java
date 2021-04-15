package com.gallery.galleryui.viewmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;

import com.gallery.gallerymodel.Image;
import com.gallery.service.ImageService;

public class ImageViewModel {

    @Autowired
    ImageService imageService;

    private String name;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Command
    public void doAddImage(@BindingParam("name") String name, @BindingParam("description") String description) {
        Image image = new Image();
        image.setName(name);
        image.setDescription(description);
        imageService.addImage(image);
    }

}
