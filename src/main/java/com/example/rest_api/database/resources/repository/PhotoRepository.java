package com.example.rest_api.database.resources.repository;

import com.example.rest_api.database.resources.model.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer> {
    // Poți adăuga metode suplimentare de interogare, de exemplu pentru a căuta poze după albumId
}
