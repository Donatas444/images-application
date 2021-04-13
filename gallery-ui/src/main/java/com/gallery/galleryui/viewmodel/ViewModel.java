package com.gallery.galleryui.viewmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Textbox;

import com.gallery.Image;
import com.gallery.Tag;
import com.gallery.service.PictureService;

public class ViewModel {
    @Autowired
    Image image;
    @Autowired
    PictureService pictureService;

    @Wire
    private Groupbox imageBox;
    @Wire
    private Textbox nameBox;
    @Wire
    private Textbox descriptionBox;
    @Wire
    private Textbox tagBox;
    @Wire
    private Button saveButton;

    @Listen("onClick = #saveButton")
    public void doAddImage() {
        Image image = new Image();
        String name = nameBox.getValue();
        String description = descriptionBox.getValue();
image.setName(name);
image.setDescription(description);
pictureService.addImage(image);

    }
}
