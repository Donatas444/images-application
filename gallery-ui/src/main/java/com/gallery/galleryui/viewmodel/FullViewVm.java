package com.gallery.galleryui.viewmodel;

import com.gallery.galleryui.service.ImageService;
import com.gallery.galleryui.service.TagService;
import com.gallery.galleryui.viewmodel.views.ImageView;
import com.gallery.repository.imageview.TagViewShow;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.*;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Set;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FullViewVm implements Serializable {
    private static final long serialVersionUID = -3440818130908355085L;
    @WireVariable
    ImageService imageService;
    @WireVariable
    @Transient
    TagService tagService;
    @Getter
    ImageView fullView;
    @Getter
    @Setter
    String tagName;
    @Getter
    Set<TagViewShow> tags;

    @Init
    public void init(@QueryParam("id") Long id) {
        getImagePageContent(id);
    }

    @NotifyChange({"tags"})
    @Command
    public void doSaveChanges(@BindingParam("imageId") Long id) {
        imageService.updateImage(id, fullView, tagName);
        getImagePageContent(id);
        Executions.sendRedirect("gallery.zul");
    }

    @NotifyChange({"tags"})
    @Command
    public void doAddTags(@BindingParam("imageId") Long id) {
        imageService.updateImage(id, tagName);
        tags = imageService.getImageTags(id);
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
        Messagebox.show("Sure want to delete image?", "Warning!", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, event -> {
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

