package com.example.rest_api.database.resources.repository;

import com.example.rest_api.database.resources.model.AlbumEntity;
import com.example.rest_api.database.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Integer> {
    Optional<AlbumEntity> findByName(String name);

    @Query(value = "SELECT name FROM albums WHERE id = :albumId", nativeQuery = true)
    String findNameById(int albumId);
}