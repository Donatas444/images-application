package com.gallery.galleryui.viewmodel;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.gallery.gallerymodel.Image;
import com.gallery.service.ImageService;

import lombok.Getter;
import lombok.Setter;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ImageViewModel {

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
    private Media picture;

    private Media uploadedPicture;

    @Command
    public void doAddImage() {
        Image image = new Image();
        image.setName(name);
        image.setDescription(description);

        imageService.addImage(image);

    }

    @NotifyChange("picture")
    @Command
    public void doUpload(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {
        picture = event.getMedia();
        if (picture.isBinary()) {
            InputStream inputStream = picture.getStreamData();
        } else {
            String inoutString = picture.getStringData();
        }

    }

}
