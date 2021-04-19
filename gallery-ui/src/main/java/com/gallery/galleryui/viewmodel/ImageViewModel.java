package com.gallery.galleryui.viewmodel;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.util.media.Media;

import org.zkoss.zhtml.Fileupload;
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
    private byte[] data;

    @Command
    public void doAddImage() {
        FileUpload fileUpload = new FileUpload();

        Image image = new Image();
        image.setName(name);
        image.setDescription(description);
        //image.setData(fileUpload.bFile);
        imageService.addImage(image);

    }

  /*  @NotifyChange("picture")
    @Command
    public void doUpload(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {
        media = event.getMedia();
        if (media.isBinary()) {
            InputStream inputStream = media.getStreamData();
        } else {
            String inputString = media.getStringData();
        }

    }*/

    @Command
    public void doUpload2(@BindingParam("data") Media data) throws IOException {

        // Media data = Fileupload.get(true);
        // InputStream inputStream= data.getStreamData();
        // return null;
        Image image = new Image();

        image.setData(data.getByteData());
        imageService.addImage(image);

    }
}
