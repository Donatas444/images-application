package com.gallery.galleryui.viewmodel;

import java.io.Serializable;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.gallery.gallerymodel.Image;
import com.gallery.service.ImageService;

import lombok.Getter;
import lombok.Setter;

public class FullViewVm implements Serializable {
    private static final long serialVersionUID = -3440818130908355085L;

    @WireVariable
    ImageService imageService;
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private byte[] data;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private Image fullView;

    @Init
    public void init(@QueryParam("id") Long id) {

        fullView = imageService.getImageById(id);
        this.name = fullView.getName();
        this.description = fullView.getDescription();
        this.data = fullView.getData();
    }

    @Command
    public void doSaveChanges() {
        Image image = this.fullView;
        image.setName(name);
        image.setDescription(description);
        imageService.updateImage(image);
        Executions.sendRedirect("gallery.zul");
    }
}

