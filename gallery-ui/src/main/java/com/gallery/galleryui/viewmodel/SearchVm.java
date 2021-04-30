package com.gallery.galleryui.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.gallery.gallerymodel.Image;
import com.gallery.service.ImageService;

import lombok.Getter;
import lombok.Setter;

public class SearchVm {

    @WireVariable
    ImageService imageService;
    @Getter
    @Setter
    String name;
    @Getter
    @Setter
    String description;
    @Getter
    @Setter
    private byte[] thumbnail;
    @Getter
    @Setter
    private List<Image> images;

    @NotifyChange({"images"})
    @Command
    public void doFindImage(@BindingParam("name") String name) {
        name = this.name;
        images = imageService.findByName(name);
    }
}
