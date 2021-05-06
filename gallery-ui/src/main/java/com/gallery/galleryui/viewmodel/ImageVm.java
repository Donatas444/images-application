package com.gallery.galleryui.viewmodel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;

import com.gallery.gallerymodel.Image;
import com.gallery.gallerymodel.Tag;
import com.gallery.service.ImageService;
import com.gallery.service.TagService;

import lombok.Getter;
import lombok.Setter;

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
    private Long id;
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
