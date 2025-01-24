package com.example.rest_api.database.users.repository;

import com.example.rest_api.database.users.model.RoleEntity;
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
    public List<RoleEntity> findAllByName(String name);

    Boolean existsByName(String name);

    Optional<RoleEntity> findByName(String name);

    @Modifying
    @Query(value = "INSERT INTO app_users_roles (app_user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
    void associateRoleToUser(Long userId, Long roleId);

    @Modifying
    @Query(value = "DELETE FROM role r WHERE r.name = :role_name", nativeQuery = true)
    void deleteRole(String role_name);

    @Query(value = "SELECT id FROM role r WHERE r.name = :role_name", nativeQuery = true)
    Integer findRoleByName(String role_name);

    @Modifying
    @Query(value = "DELETE FROM app_users_roles ar WHERE ar.role_id = :roleId", nativeQuery = true)
    void deleteAssociatedRole(Integer roleId);

    @Query(value = "SELECT name FROM role r WHERE r.id = :roleId", nativeQuery = true)
    String findRoleName(Long roleId);

    @Query(value = "SELECT * FROM role r WHERE r.id IN (SELECT role_id FROM app_users_roles WHERE app_user_id = :userId)", nativeQuery = true)
    List<RoleEntity> findRolesByUserId(int userId);

    @Query(value = "SELECT r.* FROM role r INNER JOIN app_users_roles ar ON r.id = ar.role_id WHERE ar.app_user_id = :id", nativeQuery = true)
    List<RoleEntity> getUserRoles(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_users_roles WHERE ap p_user_id = :userId", nativeQuery = true)
    void deleteRolesByUserId(Integer userId);
}
