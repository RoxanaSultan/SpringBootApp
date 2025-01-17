package com.example.rest_api.service;

import com.example.rest_api.database.resources.model.PhotoEntity;
import com.example.rest_api.database.resources.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    // Metodă pentru a salva o fotografie
    public void savePhoto(byte[] imageData, int albumId) {
        // Creăm un obiect PhotoEntity și îl salvăm în baza de date
        PhotoEntity photo = new PhotoEntity();
        photo.setAlbumId(albumId);
        photo.setImageData(imageData);
        photoRepository.save(photo);
    }

    // Metodă pentru a șterge o fotografie
    public void deletePhoto(Integer id) throws Exception {
        PhotoEntity photo = photoRepository.findById(id)
                .orElseThrow(() -> new Exception("Photo not found"));
        photoRepository.delete(photo);
    }
}
