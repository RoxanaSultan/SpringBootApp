package com.example.rest_api.controller;

import com.example.rest_api.database.resources.model.AlbumEntity;
import com.example.rest_api.database.users.model.UserEntity;
import com.example.rest_api.database.users.repository.UserRepository;
import com.example.rest_api.security.AuthenticatedUser;
import com.example.rest_api.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/home")
public class HomeController {
    UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private AlbumService albumService;

    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String home(Model model, Principal principal) {
        if (principal instanceof AuthenticatedUser authenticatedUser) {
            // Since AuthenticatedUser contains email and other attributes
            model.addAttribute("username", authenticatedUser.getEmail());
        } else {
            // Fallback if principal is not AuthenticatedUser for some reason
            model.addAttribute("username", principal.getName());
        }

        UserEntity user = userRepository.findByEmail(principal.getName()).orElse(null);

//        List<AlbumEntity> albums = albumService.findAll();

        List<AlbumEntity> albums = albumService.findAlbums(user);

        model.addAttribute("albums", albums);

        // Create a list to hold canDelete values for each album
        List<Boolean> canDeleteList = new ArrayList<>();

        for (AlbumEntity album : albums) {
            boolean canDelete = albumService.canDelete(album.getId(), user);
            canDeleteList.add(canDelete);
        }

        // Add the canDelete list to the model
        model.addAttribute("canDeleteList", canDeleteList);

        return "user/home";
    }

}

