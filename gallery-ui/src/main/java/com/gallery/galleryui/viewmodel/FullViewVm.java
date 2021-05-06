package com.gallery.galleryui.viewmodel;

import java.io.Serializable;
import java.util.Set;

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
import com.gallery.service.ImageService;
import com.gallery.service.TagService;

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
    private Set<Tag> tagNames;
    @Getter
    @Setter
    private String tagName;
    @Getter
    @Setter
    private Image fullView;

    @Init
    public void init(@QueryParam("id") Long id) {

        fullView = imageService.getImageById(id);
        this.name = fullView.getName();
        this.description = fullView.getDescription();
        this.data = fullView.getData();
        this.tagNames = fullView.getTags();
        this.id = fullView.getId();
    }

    @Command
    public void doSaveChanges() {
        Image image = this.fullView;
        image.setName(name);
        image.setDescription(description);
        tagService.ifTagExists(image, tagName);
        Executions.sendRedirect("gallery.zul");
    }

    @NotifyChange({"tagNames"})
    @Command
    public void doRemoveTag(@BindingParam("tag") Tag tag) {

        Image image = this.fullView;
        image.getTags().remove(tag);
        imageService.updateImage(image);
    }

    @NotifyChange({"images"})
    @Command
    public void doDeleteImage(@BindingParam("image") Long id) {

        imageService.deleteMessageBox(id);
    }
}

