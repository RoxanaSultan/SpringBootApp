package com.example.rest_api.controller;

import com.example.rest_api.service.RoleService;
import com.example.rest_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("welcomeMessage", "Hello Admin");
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String userManagement(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/roles")
    public String roleManagement(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "admin/roles";
    }

//    @GetMapping("/my_albums")
//    public String myAlbums(Model model) {
//        // Replace with actual service call to get the user's albums
////        List<AlbumEntity> myAlbumsList = albumService.getMyAlbums();
////        model.addAttribute("my_albums", myAlbumsList);  // Add the list of albums to the model
//        return "my_albums";
//    }
//
//    @GetMapping("/all_albums")
//    public String allAlbums(Model model) {
//        // Replace with actual service call to get all albums
////        List<AlbumEntity> allAlbumsList = albumService.getAllAlbums();
////        model.addAttribute("all_albums", allAlbumsList);  // Add the list of albums to the model
//        return "all_albums";
//    }
}