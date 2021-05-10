package com.gallery.galleryui.viewmodel;

import com.gallery.gallerymodel.Image;
import com.gallery.service.ImageService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.List;

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
    String keyword;
    @Getter
    @Setter
    private byte[] thumbnail;
    @Getter
    @Setter
    private List<Image> images;


    @NotifyChange({"images"})
    @Command
    public List<Image> doSearchByKeyword() {
        String keyword = this.keyword;
        images = imageService.searchByKeyword(keyword);
        for (Image image : images) {
            imageService.getImageById(image.getId());
            this.description = image.getDescription();
            this.name = image.getName();
            this.thumbnail = image.getThumbnail();
        }
        return images;
    }

    @Command
    public void doSelectImage(@BindingParam("id") Long id) {
        Executions.sendRedirect("editimage.zul?id=" + id);
    }

    @NotifyChange({"images"})
    @Command
    public void doDeleteImage(@BindingParam("image") Long id) {
        Messagebox.show("Sure want to delete?", "Warning!", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, event -> {
            if (event.getName().equals("onYes")) {
                imageService.deleteImageById(id);
                Executions.sendRedirect("gallery.zul");
            }
        });
    }
}
