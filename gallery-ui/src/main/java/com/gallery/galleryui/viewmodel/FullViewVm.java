package com.gallery.galleryui.viewmodel;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.gallery.galleryui.viewmodel.views.ImageView;

import com.gallery.galleryui.viewmodel.views.TagView;
import com.gallery.repository.imageview.ImageViewShow;

import com.gallery.repository.imageview.TagViewShow;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Tag;
import com.gallery.galleryui.service.ImageService;
import com.gallery.galleryui.service.TagService;

import lombok.Getter;
import lombok.Setter;
import org.zkoss.zk.ui.util.Clients;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FullViewVm implements Serializable {
    private static final long serialVersionUID = -3440818130908355085L;

    @WireVariable
    ImageService imageService;
    @WireVariable
    TagService tagService;
    @Getter
    // @Setter
    ImageView fullView;
    @Getter
    @Setter
    String tagName;
    @Getter
    // @Setter
    Set<TagViewShow> tags;
    // @Getter
    // @Setter
    TagView tagView;
    // @Getter
    // @Setter
    Set<TagView> tagsSet;


    @Init
    public void init(@QueryParam("id") Long id) {
        getImagePageContent(id);
    }

    @NotifyChange({"tags"})
    @Command
    public void doSaveChanges(@BindingParam("imageId") Long id) {
        imageService.updateImage(id, fullView, tagName);
        getImagePageContent(id);
        Clients.showNotification("Saved!");
    }

    @NotifyChange({"tags"})
    @Command
    public void doRemoveTag(@BindingParam("imageId") Long id, @BindingParam("tagName") String tagName) {
        tagService.removeTag(id, tagName);
        tags = imageService.getImageTags(id);


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

    public void getImagePageContent(Long id) {
        fullView = imageService.getImageById(id);
        tags = imageService.getImageTags(id);
    }
}

