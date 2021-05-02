package com.gallery.galleryui.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.gallery.gallerymodel.Image;
import com.gallery.service.ImageService;

import lombok.Getter;
import lombok.Setter;

public class SearchVm {

    @WireVariable
    ImageService imageService;
    @Getter
    @Setter
    String name;
    @Getter
    @Setter
    String description;
    @Getter
    @Setter
    private byte[] thumbnail;
    @Getter
    @Setter
    private List<Image> images;

    @NotifyChange({"images"})
    @Command
    public void doFindImage() {
        String name = this.name;
        images = imageService.findByName(name);
    }

    @NotifyChange({"images"})
    @Command
    public void doFindByNameAndDescription() {
        String name = this.name;
        String description = this.description;
        images = imageService.findByNameAndDescription(name, description);
    }

    @Command
    public void doSelectImage(@BindingParam("id") Long id) {
        Executions.sendRedirect("exactimage.zul?id=" + id);
    }

    @NotifyChange({"images"})
    @Command
    public void doDeleteImage(@BindingParam("image") Image image) {

        Messagebox.show("Sure want to delete?", "Warning!", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, event -> {
            if (event.getName().equals("onYes")) {
                imageService.deleteImage(image);
                Executions.sendRedirect("search.zul");
            }
        });
    }
}

