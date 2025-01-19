package com.example.rest_api.controller;

import com.example.rest_api.database.users.model.UserEntity;
import com.example.rest_api.security.AuthenticatedUser;
import com.example.rest_api.service.AlbumService;
import com.example.rest_api.database.users.repository.UserRepository;
import com.example.rest_api.service.RoleService;
import com.example.rest_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
public class AlbumController {

    @Autowired
    private AlbumService albumService;
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/albums")
    public String albums(Integer albumId, Model model, Principal principal) {
//        UserEntity user = userRepository.findByEmail(principal.getName()).orElse(null);
//
//        if (!albumService.canAddPicture(albumId, user)) {
//            model.addAttribute("canAdd", false);
//        } else {
//            model.addAttribute("canAdd", true);
//        }
//
//        if (!albumService.canDelete(albumId, user)) {
//            model.addAttribute("canDelete", false);
//        } else {
//            model.addAttribute("canDelete", true);
//        }

        return "/albums";
    }

    @PostMapping("/home/create")
    @ResponseBody
    public String createAlbum(@RequestBody Map<String, String> request, Principal principal) {
        String albumName = request.get("name");
        albumName = albumName.toUpperCase().trim();
        if (albumService.findByName(albumName).isPresent()) {
            return "Album name already exists!";
        }

        if (albumName == null || albumName.isEmpty()) {
            return "Album name cannot be empty!";
        }

        UserEntity user = userRepository.findByEmail(principal.getName()).orElse(null);

        if (user == null) {
            return "User not found!";
        }

        albumService.createAlbum(albumName, user);

        return "Album created successfully!";
    }

    @DeleteMapping("/home/delete")
    @ResponseBody
    public String deleteAlbum(@RequestBody Map<String, String> request, Principal principal) {
        String albumIdString = request.get("albumId");

        if (albumIdString == null) {
            return "Album ID is missing in request.";
        }

        int albumId = Integer.parseInt(albumIdString);

        albumService.deleteAlbum(albumId);
        return "Album deleted successfully.";
    }
}