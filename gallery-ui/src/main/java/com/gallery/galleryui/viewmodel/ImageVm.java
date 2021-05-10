package com.gallery.galleryui.viewmodel;

import com.gallery.repository.imageview.ImageViewShow;
import com.gallery.galleryui.service.ImageService;
import com.gallery.galleryui.service.TagService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.*;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.io.Serializable;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ImageVm implements Serializable {

    private static final long serialVersionUID = 1L;

    @WireVariable
    ImageService imageService;
    @WireVariable
    TagService tagService;
    @Getter
    @Setter
    private String tagName;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private byte[] data;
    @Getter
    @Setter
    private byte[] thumbnail;
    @Getter
    @Setter
    private List<ImageViewShow> images;
    @Getter
    @Setter
    private ImageViewShow image;


    @Init
    public void init() {
        showGalleryList();

    }




    @Command
    public void doSelectImage(@BindingParam("id") Long id) {
        Executions.sendRedirect("editimage.zul?id=" + id);
    }

    @NotifyChange({"images"})
    @Command
    public void doDeleteImage(@BindingParam("image") Long id) {
        Messagebox.show("Sure want to delete?", "Warning!", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, event -> {
            if (event.getName().equals("onYes")) {
                imageService.deleteImageById(id);
                Executions.sendRedirect("gallery.zul");
            }
        });
    }

    @NotifyChange({"images"})
    public void showGalleryList() {
        images = imageService.getAllImages();
    }


}
