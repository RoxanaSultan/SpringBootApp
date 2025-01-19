package com.example.rest_api.controller;

import com.example.rest_api.database.resources.model.AlbumEntity;
import com.example.rest_api.database.resources.model.PhotoEntity;
import com.example.rest_api.database.users.model.UserEntity;
import com.example.rest_api.database.users.repository.UserRepository;
import com.example.rest_api.service.AlbumService;
import com.example.rest_api.service.PhotoService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PhotoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private PhotoService photoService;

    @GetMapping("/photos/{albumId}")
    public String getPhotosByAlbum(@PathVariable("albumId") int albumId, Model model, Principal principal) {
        UserEntity user = userRepository.findByEmail(principal.getName()).orElse(null);
        Iterable<PhotoEntity> photos = photoService.findPhotosByAlbumId(albumId);
        model.addAttribute("photos", photos);

        if (albumService.canAdd(albumId, user)) {
            model.addAttribute("canAdd", true);
        } else {
            model.addAttribute("canAdd", false);
        }

        if (albumService.canDelete(albumId, user)) {
            model.addAttribute("canDelete", true);
        } else {
            model.addAttribute("canDelete", false);
        }

        return "/user/photos";
    }

    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam("file") MultipartFile file,
                           @RequestParam("albumId") int albumId) throws IOException {
        byte[] imageData = file.getBytes();
        photoService.addPhoto(imageData, albumId);
        return "redirect:/photos/" + albumId;
    }

    @GetMapping("/photos/{photoId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable("photoId") int photoId) {
        PhotoEntity photo = photoService.getPhotoById(photoId);
        if (photo != null && photo.getImageData() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  // sau PNG în funcție de format
                    .body(photo.getImageData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // DELETE: Șterge o poză din album
    @DeleteMapping("/photos/delete/{id}")
    @ResponseBody
    public String deletePhoto(@PathVariable("id") int id) {
            photoService.deletePhoto(id);
            return "success";
    }
}
