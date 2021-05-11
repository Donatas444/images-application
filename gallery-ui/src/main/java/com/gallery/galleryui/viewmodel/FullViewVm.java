package com.gallery.galleryui.viewmodel;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.gallery.galleryui.viewmodel.views.ImageView;

import com.gallery.repository.imageview.ImageViewShow;
import com.gallery.repository.imageview.TagView;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Tag;
import com.gallery.galleryui.service.ImageService;
import com.gallery.galleryui.service.TagService;

import lombok.Getter;
import lombok.Setter;

public class FullViewVm implements Serializable {
    private static final long serialVersionUID = -3440818130908355085L;

    @WireVariable
    ImageService imageService;
    @WireVariable
    TagService tagService;

    @Getter
    @Setter
    private ImageView fullView;
    @Getter
    @Setter
    private TagView tagView;
    @Getter
    @Setter
    private List<TagView> tags;

    @Init
    public void init(@QueryParam("id") Long id) {

        fullView = imageService.getImageById(id);
        tags = imageService.getTImageTags(id);
    }

    // @Command
    // public void doSaveChanges() {
    //     Image image = this.fullView;
    //     image.setName(name);
    //     image.setDescription(description);
    //     tagService.addTags(image, tagName);
    //     Executions.sendRedirect("gallery.zul");
    // }
    //
    // @NotifyChange({"tagNames"})
    // @Command
    // public void doRemoveTag(@BindingParam("tag") Tag tag) {
    //
    //     Image image = this.fullView;
    //     image.getTags().remove(tag);
    //     imageService.updateImage(image);
    // }

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
}

