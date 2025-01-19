package com.example.rest_api.service;

import com.example.rest_api.database.resources.model.AlbumEntity;
import com.example.rest_api.database.resources.model.PhotoEntity;
import com.example.rest_api.database.resources.repository.AlbumRepository;
import com.example.rest_api.database.resources.repository.PhotoRepository;
import com.example.rest_api.database.users.model.PermissionEntity;
import com.example.rest_api.database.users.model.RoleEntity;
import com.example.rest_api.database.users.model.UserEntity;
import com.example.rest_api.database.users.repository.PermissionRepository;
import com.example.rest_api.database.users.repository.RoleRepository;
import com.example.rest_api.database.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private AlbumRepository albumRepository;

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
        return photoRepository.findByAlbumId(albumId);
    }

    // Șterge o poză după ID
    public void deletePhoto(int photoId) {
        photoRepository.deleteById(photoId);  // Apelează repository-ul pentru a șterge poza
    }

    public PhotoEntity getPhotoById(int photoId) {
        return photoRepository.findById(photoId).orElse(null);
    }
}
