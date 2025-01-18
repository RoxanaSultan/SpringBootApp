package com.example.rest_api.controller;

import com.example.rest_api.database.resources.model.PhotoEntity;
import com.example.rest_api.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    // GET: Obține pozele din album
    @GetMapping("/album/{albumId}")
    public String getPhotosByAlbum(@PathVariable("albumId") int albumId, Model model) {
        model.addAttribute("photos", photoService.findPhotosByAlbumId(albumId));
        return "album/photos"; // Vezi că se folosește template-ul album/photos
    }

    // POST: Adaugă o poză în album
    @PostMapping("/api/photos/add")
    public String addPhoto(@RequestParam("file") MultipartFile file,
                           @RequestParam("albumId") int albumId) {
        try {
            // Salvează poza
            byte[] imageData = file.getBytes(); // Transformă fișierul într-un array de bytes
            PhotoEntity photoEntity = photoService.addPhoto(imageData, albumId);
            return "redirect:/album/" + albumId + "/photos"; // Redirect către albumul cu pozele
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Sau o altă pagină de eroare
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
