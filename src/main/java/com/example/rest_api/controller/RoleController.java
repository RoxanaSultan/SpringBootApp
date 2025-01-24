package com.example.rest_api.controller;

import com.example.rest_api.database.users.model.PermissionEntity;
import com.example.rest_api.dto.PermissionDTO;
import com.example.rest_api.dto.RoleDTO;
import com.example.rest_api.service.PermissionService;
import org.springframework.stereotype.Controller;
import com.example.rest_api.database.users.model.RoleEntity;
import com.example.rest_api.service.RoleService;
import com.example.rest_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RoleController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/update_permissions")
    public ResponseEntity<String> updatePermissions(@RequestBody Map<String, Object> payload) {
        Long userId = Long.valueOf(payload.get("userId").toString());
        List<String> roles = (List<String>) payload.get("roles");

        roleService.updateUserRoles(Math.toIntExact(userId), roles);
        return ResponseEntity.ok("Roles updated successfully!");
    }

    @GetMapping("/get_permissions/{role_id}")
    public ResponseEntity<Map<String, Object>> getPermissions(@PathVariable Long role_id) {
        List<PermissionEntity> rolePermissions = permissionService.getRolePermissions(role_id);

        // Maparea entităților PermissionEntity în DTO-uri
        List<PermissionDTO> rolePermissionsDTO = rolePermissions.stream()
                .map(permission -> new PermissionDTO(permission.getId(), permission.getHttp_method(), permission.getUrl()))
                .toList();


        Map<String, Object> response = new HashMap<>();
        response.put("rolePermissions", rolePermissionsDTO);
        return ResponseEntity.ok(response);
    }

}


