package com.example.rest_api.database.resources.repository;

import com.example.rest_api.database.resources.model.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer> {
    Iterable<PhotoEntity> findByAlbumId(int albumId);
}
