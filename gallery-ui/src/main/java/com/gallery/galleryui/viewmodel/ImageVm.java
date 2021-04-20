package com.gallery.galleryui.viewmodel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.image.Images;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.gallery.gallerymodel.Image;
import com.gallery.service.ImageService;

import lombok.Getter;
import lombok.Setter;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ImageVm implements Serializable {

    private static final long serialVersionUID = 1L;
    @WireVariable
    ImageService imageService;
    @Getter
    @Setter
    @WireVariable
    private String name;
    @Getter
    @Setter
    @WireVariable
    private String description;
    @Getter
    @Setter
    @WireVariable
    private byte[] data;
    @Getter
    @Setter
    private List<Image> images;

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }

    @Command
    public void onFileUpload(@ContextParam(ContextType.BIND_CONTEXT) BindContext picture) {
        UploadEvent uploadEvent = null;
        Object objUploadEvent = picture.getTriggerEvent();

        if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
            uploadEvent = (UploadEvent) objUploadEvent;

            Media media = uploadEvent.getMedia();

            name = media.getName();

            data = media.getByteData();
        }
    }

    @Command
    public void doAddImage() {

        Image image = new Image();
        image.setName(name);
        image.setDescription(description);
        image.setData(data);
        imageService.addImage(image);

    }

  /*  @NotifyChange("images")
    public List<Image> imageList() {

        List<Image> images = imageService.getAllImages();

        return images;
    } */

    @Init
    public void init() {
        images = imageService.getAllImages();
    }


 /*    @Command
    public org.zkoss.image.Image create(String name, byte[] data) throws IOException {
        AImage alImage = new AImage(name, data);
        org.zkoss.zul.Image zkImage = new org.zkoss.zul.Image();
        zkImage.setContent(alImage);

        org.zkoss.image.Image newImageFromDB = zkImage.getContent();
        return newImageFromDB;
    }

   @Command
    public org.zkoss.image.Image show() {
        AImage img = null;
        try {
            img = new AImage(nomeImmagineArticolo, artImg[0].getImmagine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return immagineArt.setContent(img);
    }*/

}
