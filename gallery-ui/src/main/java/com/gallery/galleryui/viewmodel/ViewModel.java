package com.gallery.galleryui.viewmodel;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Textbox;

import com.gallery.Image;
import com.gallery.Tag;

public class ViewModel {
    @Wire
    private Groupbox imageBox;
    @Wire
    private Textbox nameBox;
    @Wire
    private Textbox descriptionBox;
    @Wire
    private Textbox tagBox;
    @Listen("onClick = ")
    public void doAddImage() {

    }
}
