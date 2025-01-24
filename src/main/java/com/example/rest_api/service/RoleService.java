package com.example.rest_api.service;

import com.example.rest_api.database.users.model.RoleEntity;
import com.example.rest_api.database.users.repository.RoleRepository;
import com.example.rest_api.database.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void save(RoleEntity role) {
        this.roleRepository.save(role);
    }

    public Boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    public Optional<RoleEntity> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public List<RoleEntity> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        roles.remove(roleRepository.findByName("ADMIN").get());
        roles.remove(roleRepository.findByName("USER").get());
        roles.remove(roleRepository.findByName("DEFAULT").get());
        return roles;
    }

    public List<RoleEntity> getUserRoles(Long id) {
        return roleRepository.getUserRoles(id);
    }

    public void updateUserRoles(Integer userId, List<String> roles) {
        roleRepository.deleteRolesByUserId(userId);
        for (String roleId : roles) {
            roleRepository.associateRoleToUser(Long.valueOf(userId), Long.valueOf(roleId));
        }
        roleRepository.associateRoleToUser(Long.valueOf(userId), 2L);
    }
}
