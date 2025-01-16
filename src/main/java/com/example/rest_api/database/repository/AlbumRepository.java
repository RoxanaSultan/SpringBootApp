package com.example.rest_api.database.repository;

import com.example.rest_api.database.model.AlbumEntity;
import com.example.rest_api.database.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Integer> {
    Optional<AlbumEntity> findByName(String name);

    @Modifying
    @Query(value = "INSERT INTO albums (name) VALUES (:albumName)", nativeQuery = true)
    void createAlbum(String albumName);
}
