package com.example.rest_api.service;

import com.example.rest_api.database.resources.model.PhotoEntity;
import com.example.rest_api.database.resources.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    // Adaugă o poză nouă
    public PhotoEntity addPhoto(byte[] imageData, int albumId) {
        // Creează obiectul PhotoEntity
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setAlbumId(albumId);  // Setează albumId-ul
        photoEntity.setImageData(imageData);  // Setează datele imaginii (în bytes)

        // Salvează entitatea Photo în baza de date
        return photoRepository.save(photoEntity);  // Returnează poza salvată
    }

    // Găsește pozele dintr-un album după albumId
    public Iterable<PhotoEntity> findPhotosByAlbumId(int albumId) {
        return photoRepository.findByAlbumId(albumId);  // Apelează metoda repository-ului pentru a găsi pozele
    }

    // Șterge o poză după ID
    public void deletePhoto(int photoId) {
        photoRepository.deleteById(photoId);  // Apelează repository-ul pentru a șterge poza
    }
}
