package com.example.rest_api.database.users.repository;

import com.example.rest_api.database.users.model.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    @Query(value = "SELECT * FROM permissions p WHERE p.role_id = :roleId", nativeQuery = true)
    Iterable<PermissionEntity> findPermissionsByRole(Long roleId);
}
