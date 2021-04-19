package com.gallery.galleryui.viewmodel;

import java.io.Serializable;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
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
public class FileUpload implements Serializable {
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

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }

    @Command
    public void onFileUpload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
        UploadEvent uploadEvent = null;
        Object objUploadEvent = ctx.getTriggerEvent();

        if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
            uploadEvent = (UploadEvent) objUploadEvent;

            Media media = uploadEvent.getMedia();

            name = media.getName();
            String format = media.getFormat();

            data = media.getByteData();

            System.out.println(String.format("File Name: %s, Format: %s", name, format));
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
}
