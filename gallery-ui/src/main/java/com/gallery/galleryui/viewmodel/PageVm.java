package com.gallery.galleryui.viewmodel;

import java.io.Serializable;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.gallery.gallerymodel.Image;

import lombok.Getter;

public class PageVm implements Serializable {
    private static final long serialVersionUID = -3440818130908355085L;

    @WireVariable(rewireOnActivate = true)
    private transient Image image;

    @Getter
    private List<Object> someList;

    @Init
    public void init() {
        // code to run on page init
    }

}