package com.example.rest_api.controller;

import com.example.rest_api.database.users.model.UserEntity;
import com.example.rest_api.service.AlbumService;
import com.example.rest_api.database.users.repository.UserRepository;
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

    @DeleteMapping("/api/albums/delete")
    @ResponseBody
    public String deleteAlbum(@RequestBody Map<String, String> request, Principal principal) {
        // Extragem albumId din request
        String albumIdString = request.get("albumId");
        if (albumIdString == null) {
            return "Album ID is missing in request.";
        }

        // Convertim albumId la integer
        int albumId = Integer.parseInt(albumIdString);

        // Verificăm dacă utilizatorul autenticat poate șterge albumul (optional)
//            String username = principal.getName();
//            if (username == null || !albumService.canDeleteAlbum(albumId, username)) {
//                return "You don't have permission to delete this album.";
//            }

        // Ștergem albumul
        albumService.deleteAlbum(albumId);
        return "Album deleted successfully.";
    }

    @GetMapping("/album_photos_admin/{id}")
    public String showAlbumPhotos(@PathVariable("id") Integer albumId, Model model, Principal principal) {
        // Verificăm dacă utilizatorul este logat
        if (principal == null) {
            return "redirect:/login"; // Redirecționează către pagina de login dacă nu e logat
        }

        // Găsim albumul după ID
        var album = albumService.findAlbumById(albumId);
        if (album == null) {
            return "error/404"; // Redirecționează către o pagină de eroare dacă albumul nu există
        }

        // Adăugăm albumul în model pentru a-l afișa în view
        model.addAttribute("album", album);
//        model.addAttribute("photos", album.getPhotos()); // Presupunem că AlbumEntity are o listă de poze

        return "user/album_photos_admin"; // Returnează pagina de vizualizare
    }
}