package com.example.rest_api.service;

import com.example.rest_api.database.users.model.PermissionEntity;
import com.example.rest_api.database.users.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public void save(PermissionEntity permissionEntity) {
        this.permissionRepository.save(permissionEntity);
    }

    public List<PermissionEntity> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public List<PermissionEntity> getRolePermissions(Long roleId) {
        return permissionRepository.getRolePermissions(roleId);
    }
}
