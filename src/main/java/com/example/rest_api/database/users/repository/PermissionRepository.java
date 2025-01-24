package com.example.rest_api.database.users.repository;

import com.example.rest_api.database.users.model.PermissionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    @Query(value = "SELECT * FROM permissions p WHERE p.role_id = :roleId", nativeQuery = true)
    Iterable<PermissionEntity> findPermissionsByRole(Long roleId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM permissions WHERE role_id = :roleId", nativeQuery = true)
    void deletePermissionsForRole(Long roleId);

    @Query(value = "SELECT * FROM permissions p WHERE p.role_id = :roleId", nativeQuery = true)
    List<PermissionEntity> getRolePermissions(Long roleId);
}
