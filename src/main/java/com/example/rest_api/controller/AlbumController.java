package com.example.rest_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlbumController {

    @GetMapping("/my_albums")
    public String myAlbums(Model model) {
        // Aici poți adăuga albumele în model pentru a fi afișate în pagină
        // model.addAttribute("albums", albumService.getUserAlbums());

        return "user/my_albums";  // numele paginii HTML (my_albums.html în templates)
    }
}
