package com.example.rest_api.controller;

import com.example.rest_api.database.users.model.UserEntity;
import com.example.rest_api.service.AlbumService;
import com.example.rest_api.database.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Controller
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/my_albums")
    public String myAlbums(Model model) {
        model.addAttribute("albums", albumService.findAll());
        return "user/my_albums";
    }

    @GetMapping("/user/all_albums")
    public String allAlbums(Model model) {
        model.addAttribute("albums", albumService.findAll());
        return "user/all_albums";
    }

    @PostMapping("/api/albums/create")
    @ResponseBody
    public String createAlbum(@RequestBody Map<String, String> request, Principal principal) {
        String albumName = request.get("name");

        if (albumName == null || albumName.isEmpty()) {
            return "Album name cannot be empty!";
        }

        // Obține utilizatorul autentificat
        UserEntity user = userRepository.findByEmail(principal.getName()).orElse(null);

        if (user == null) {
            return "User not found!";
        }

        albumService.createAlbum(albumName, user);

        return "Album created successfully!";
    }
}