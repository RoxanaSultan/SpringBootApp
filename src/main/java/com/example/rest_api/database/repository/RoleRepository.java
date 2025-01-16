package com.example.rest_api.database.repository;

import com.example.rest_api.database.model.RoleEntity;
import com.example.rest_api.database.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAllByName(String name);

    Boolean existsByName(String name);

    Optional<RoleEntity> findByName(String name);

    // Insert admin role
    @Modifying
    @Query(value = "INSERT INTO role (name) VALUES (:adminRoleName)", nativeQuery = true)
    void createAdminRole(String adminRoleName);

    // Insert user role
    @Modifying
    @Query(value = "INSERT INTO role (name) VALUES (:userRoleName)", nativeQuery = true)
    void createUserRole(String userRoleName);

    // Associate user with admin role
    @Modifying
    @Query(value = "INSERT INTO app_users_roles (user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
    void associateRoleToUser(Long userId, Long roleId);
}