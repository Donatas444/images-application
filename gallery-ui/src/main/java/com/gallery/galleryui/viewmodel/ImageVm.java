package com.gallery.galleryui.viewmodel;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Tag;
import com.gallery.service.ImageService;
import com.gallery.service.TagService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;

import java.awt.image.BufferedImage;
import java.io.IOException;
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
    private List<Image> images;


    @Init
    public void init() {
        showGalleryList();

    }


    @NotifyChange({"thumbnail"})
    @Command
    public void onFileUpload(@ContextParam(ContextType.BIND_CONTEXT) BindContext picture) throws IOException {
        UploadEvent uploadEvent = null;
        Object objUploadEvent = picture.getTriggerEvent();

        if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
            uploadEvent = (UploadEvent) objUploadEvent;

            Media media = uploadEvent.getMedia();

            name = media.getName();
            data = media.getByteData();
            BufferedImage newThumbnail = imageService.createThumbnail(data);
            thumbnail = imageService.bufferedImageToByteArray(newThumbnail);
        }
    }

    @Command
    public void doAddImage() {

        Image image = new Image();
        Tag tag = new Tag();
        image.setName(name);
        image.setDescription(description);
        image.setData(data);
        image.setThumbnail(thumbnail);
        tag.setName(tagName);

        if (data != null) {
            tagService.ifTagExists(image, tagName);
            Executions.sendRedirect("gallery.zul");
        } else {
            Clients.showNotification("Upload image before saving!");
        }
    }

    @Command
    public void doSelectImage(@BindingParam("id") Long id) {
        Executions.sendRedirect("editimage.zul?id=" + id);
    }

    @NotifyChange({"images"})
    @Command
    public void doDeleteImage(@BindingParam("image") Long id) {
        imageService.deleteMessageBox(id);
    }

    @NotifyChange({"images"})
    public void showGalleryList() {
        images = imageService.getAllImages();
    }


}
