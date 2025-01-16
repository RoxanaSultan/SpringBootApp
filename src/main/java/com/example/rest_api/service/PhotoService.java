package com.example.rest_api.service;

import com.example.rest_api.database.repository.PhotoRepository;
import com.example.rest_api.database.model.PhotoEntity;
import com.example.rest_api.database.model.AlbumEntity;
import com.example.rest_api.database.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    // Get all photos for a specific album
//    public List<PhotoEntity> getPhotosByAlbum(AlbumEntity album) {
////        return photoRepository.findByAlbum(album);  // Assuming you have a relationship between Album and Photo
//    }
//
//    // Get all photos for a specific user (if needed)
//    public List<PhotoEntity> getPhotosByUser(UserEntity user) {
////        return photoRepository.findByUser(user);  // Assuming photos are related to users
//    }
//
//    // Upload a new photo to an album
//    public PhotoEntity uploadPhoto(String photoUrl, AlbumEntity album, UserEntity user) {
//        PhotoEntity photo = new PhotoEntity();
////        photo.setUrl(photoUrl);  // URL of the uploaded photo
////        photo.setAlbum(album);  // Associate with the album
////        photo.setUser(user);  // Associate with the user
//        return photoRepository.save(photo);  // Save the photo to the database
//    }
}
