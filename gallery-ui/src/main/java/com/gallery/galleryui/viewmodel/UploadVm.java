package com.gallery.galleryui.viewmodel;

import com.gallery.galleryui.service.ImageService;
import com.gallery.galleryui.service.TagService;
import com.gallery.galleryui.viewmodel.views.ImageView;
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

@Getter
@Setter
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UploadVm {
    @WireVariable
    ImageService imageService;
    @WireVariable
    TagService tagService;
    private ImageView image;
    private String tagName;

    @Init
    public void init() {
        image = new ImageView();
    }

    @Command
    public void doAddImage() {
        if (image.getData() != null) {
            imageService.addImage(image, tagName);
            Executions.sendRedirect("gallery.zul");
        } else {
            Clients.showNotification("Upload image before saving!");
        }
    }

    @NotifyChange({"image"})
    @Command
    public void onFileUpload(@ContextParam(ContextType.BIND_CONTEXT) BindContext picture) throws IOException {
        UploadEvent uploadEvent = null;
        Object objUploadEvent = picture.getTriggerEvent();
        if (objUploadEvent instanceof UploadEvent) {
            uploadEvent = (UploadEvent) objUploadEvent;
            Media media = uploadEvent.getMedia();
            String mediaName = media.getName();
            byte[] data = media.getByteData();
            BufferedImage newThumbnail = imageService.createThumbnail(data);
            byte[] thumbnail = imageService.bufferedImageToByteArray(newThumbnail);
            image.setName(mediaName);
            image.setData(data);
            image.setThumbnail(thumbnail);
        }
    }
}
