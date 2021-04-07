package com.gallery.galleryui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Testas {

    @GetMapping("/")
        public String getIndex(Model model) {

            return "index.html";
    }

}
