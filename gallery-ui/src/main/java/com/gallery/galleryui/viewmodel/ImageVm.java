package com.gallery.galleryui.viewmodel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
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
    @WireVariable
    private byte[] thumbnail;
    @Getter
    @Setter
    private List<Image> images;

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
    }

    @Command
    public void onFileUpload(@ContextParam(ContextType.BIND_CONTEXT) BindContext picture) throws SQLException, IOException {
        UploadEvent uploadEvent = null;
        Object objUploadEvent = picture.getTriggerEvent();

        if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
            uploadEvent = (UploadEvent) objUploadEvent;

            Media media = uploadEvent.getMedia();

            name = media.getName();
            data = media.getByteData();
            BufferedImage thnl = createThumbnail(data);
            thumbnail = bufferedImageToByteArray(thnl);
        }
    }

    @Command
    public void doAddImage() {

        Image image = new Image();
        image.setName(name);
        image.setDescription(description);
        image.setData(data);
        image.setThumbnail(thumbnail);
        imageService.addImage(image);
    }

    @Init
    public void init() {

        images = imageService.getAllImages();
    }

    public BufferedImage createThumbnail(byte[] input) {

        BufferedImage scaledImage = Scalr.resize(createImageFromBytes(input), 150);
        return scaledImage;
    }

    public BufferedImage createImageFromBytes(byte[] input) {
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] bufferedImageToByteArray(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}
