package com.example.rest_api.controller;

import com.example.rest_api.database.resources.model.PhotoEntity;
import com.example.rest_api.service.PhotoService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    // GET: Obține pozele din album
//    @GetMapping("/album/{albumId}")
//    public String getPhotosByAlbum(@PathVariable("albumId") int albumId, Model model) {
//        model.addAttribute("photos", photoService.findPhotosByAlbumId(albumId));
//        return "album/photos"; // Vezi că se folosește template-ul album/photos
//    }
    @GetMapping("/album_photos_admin/{albumId}")
    public String getPhotosByAlbum(@PathVariable("albumId") int albumId, Model model) {
        System.out.println("I am here with albumId: " + albumId);  // Verifică dacă ajunge în funcție

        model.addAttribute("photos", photoService.findPhotosByAlbumId(albumId));

        return "/user/album_photos_admin";  // Numele corect al template-ului
    }

    // POST: Adaugă o poză în album
    @PostMapping("/api/photos/add")
    public String addPhoto(@RequestParam("file") MultipartFile file,
                           @RequestParam("albumId") int albumId) {
        try {
            // Salvează poza
            byte[] imageData = file.getBytes(); // Transformă fișierul într-un array de bytes
            PhotoEntity photoEntity = photoService.addPhoto(imageData, albumId);
            return "redirect:/album_photos_admin/" + albumId;
            // Redirect către albumul cu pozele
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Sau o altă pagină de eroare
        }
    }

    @GetMapping("/api/photos/{photoId}")
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
    @DeleteMapping("/api/photos/delete/{id}")
    @ResponseBody
    public String deletePhoto(@PathVariable("id") int id) {
        try {
            photoService.deletePhoto(id);
            return "success"; // Poți returna un mesaj de succes sau status code
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
