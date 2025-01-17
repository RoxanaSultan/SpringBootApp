package com.example.rest_api.controller;

import com.example.rest_api.database.resources.model.PhotoEntity;
import com.example.rest_api.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    // Endpoint pentru a adăuga o fotografie
    @PostMapping("/add")
    public ResponseEntity<String> addPhoto(@RequestParam("file") MultipartFile file,
                                           @RequestParam("albumId") int albumId) {
        try {
            // Convertim fișierul într-un array de byte-uri și îl salvăm în baza de date
            byte[] imageData = file.getBytes();
            photoService.savePhoto(imageData, albumId);
            return ResponseEntity.ok("Photo added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding photo");
        }
    }

    // Endpoint pentru a șterge o fotografie
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePhoto(@PathVariable Integer id) {
        try {
            photoService.deletePhoto(id);
            return ResponseEntity.ok("Photo deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Photo not found");
        }
    }
}
