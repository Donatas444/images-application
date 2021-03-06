package com.gallery.galleryui.viewmodel;

import com.gallery.galleryui.service.ImageService;
import com.gallery.repository.imageview.ImageViewShow;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SearchVm {
    @WireVariable
    ImageService imageService;
    @Getter
    @Setter
    private String keyword;
    @Getter
    private List<ImageViewShow> images;

    @NotifyChange({"images"})
    @Command
    public void doSearchByKeyword() {
        List<Long> imagesId = imageService.searchByKeyword(keyword);
        images = imageService.convertIdListToImageViewList(imagesId);
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
