package com.example.rest_api.database.users.repository;

import com.example.rest_api.database.users.model.RoleEntity;
import com.example.rest_api.database.users.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //@Query(value = "SELECT user FROM UserEntity user WHERE user.username=:username")
    //@Query(value = "SELECT * FROM app_user WHERE username=:username", nativeQuery = true)
    List<UserEntity> findAllByUsername(String username);

    @Modifying
    @Query(value = "update app_user set username=:username, email=:email, password=:password WHERE id=:id", nativeQuery = true)
    void updateUserEntity(Long id, String username, String email, String password);

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT role_id FROM app_users_roles WHERE app_user_id=:id", nativeQuery = true)
    Iterable<Long> findAdminRoles(Long id);

    @Query(value = "SELECT role_id FROM app_users_roles WHERE app_user_id=:id", nativeQuery = true)
    List<String> findRolesByUserId(Long id);
}
