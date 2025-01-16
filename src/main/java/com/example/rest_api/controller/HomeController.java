package com.example.rest_api.controller;

import com.example.rest_api.database.repository.UserRepository;
import com.example.rest_api.security.AuthenticatedUser;
import com.example.rest_api.service.AlbumService;
import com.example.rest_api.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final AlbumService albumService;
    private final PhotoService photoService;
    UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    public HomeController(UserRepository userRepository, AlbumService albumService, PhotoService photoService) {
        this.userRepository = userRepository;
        this.albumService = albumService;
        this.photoService = photoService;
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
        return "user/home";
    }

//    @GetMapping("/my_albums")
//    public String myAlbums(Model model) {
//        // Replace with actual service call to get the user's albums
////        List<AlbumEntity> myAlbumsList = albumService.getMyAlbums();
//        model.addAttribute("my_albums", albumService.findAll());  // Add the list of albums to the model
//        return "my_albums";
//    }
//
//    @GetMapping("/all_albums")
//    public String allAlbums(Model model) {
//        // Replace with actual service call to get all albums
////        List<AlbumEntity> allAlbumsList = albumService.getAllAlbums();
//        model.addAttribute("all_albums");  // Add the list of albums to the model
//        return "all_albums";
//    }

}

