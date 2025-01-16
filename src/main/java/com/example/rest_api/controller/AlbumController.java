package com.example.rest_api.controller;

import com.example.rest_api.database.model.AlbumEntity;
import com.example.rest_api.database.model.UserEntity;
import com.example.rest_api.service.AlbumService;
import com.example.rest_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final UserService userService;  // Service to get the logged-in user

    @Autowired
    public AlbumController(AlbumService albumService, UserService userService) {
        this.albumService = albumService;
        this.userService = userService;
    }

    @PostMapping("/create/albums")
    public ResponseEntity<String> createAlbum(@RequestBody Map<String, String> request, Principal principal) {
        String albumName = request.get("name");
        UserEntity user = userService.findByEmail(principal.getName());
        albumService.createAlbum(albumName, user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Album and roles created successfully.");
    }
}
