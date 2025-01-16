package com.example.rest_api.service;

import com.example.rest_api.database.model.RoleEntity;
import com.example.rest_api.database.repository.AlbumRepository;
import com.example.rest_api.database.model.AlbumEntity;
import com.example.rest_api.database.model.UserEntity;
import com.example.rest_api.database.repository.RoleRepository;
import com.example.rest_api.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<AlbumEntity> findAll() {
        return this.albumRepository.findAll();
    }

    // Create a new album
    public void createAlbum(String albumName, UserEntity user) {
        // Create and save the album
        AlbumEntity album = new AlbumEntity();
        album.setName(albumName);
        albumRepository.save(album);

        // Create roles
        String adminRoleName = albumName.toUpperCase() + "_ALBUM_ADMIN";
        String userRoleName = albumName.toUpperCase() + "_ALBUM";

        RoleEntity adminRole = new RoleEntity();
        adminRole.setName(adminRoleName);
        roleRepository.save(adminRole);

        RoleEntity userRole = new RoleEntity();
        userRole.setName(userRoleName);
        roleRepository.save(userRole);

        user.getRoles().add(adminRole);
        userRepository.save(user);

        albumRepository.createAlbum(albumName);
        roleRepository.createAdminRole(adminRoleName);
        roleRepository.createUserRole(userRoleName);
        roleRepository.associateRoleToUser(user.getId(), adminRole.getId());
    }
}
