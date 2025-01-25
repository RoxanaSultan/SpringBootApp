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
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/get_permissions/{role_id}")
    public ResponseEntity<Map<String, Object>> getPermissions(@PathVariable Long role_id) {
        List<PermissionEntity> rolePermissions = permissionService.getRolePermissions(role_id);
        List<PermissionDTO> rolePermissionsDTO = rolePermissions.stream()
                .map(permission -> new PermissionDTO(permission.getId(), permission.getHttp_method(), permission.getUrl()))
                .toList();


        Map<String, Object> response = new HashMap<>();
        response.put("rolePermissions", rolePermissionsDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add_permission")
    public ResponseEntity<String> addPermission(@RequestBody Map<String, Object> newPermission) {
        String http_method = (String) newPermission.get("http_method");
        String url = (String) newPermission.get("url");
        Long roleId = Long.valueOf(newPermission.get("role_id").toString());

        PermissionEntity permission = new PermissionEntity();
        permission.setHttp_method(http_method);
        permission.setUrl(url);
        permission.setRole(roleService.findById(roleId));
        permissionService.save(permission);

        return ResponseEntity.ok("Permission added successfully!");
    }

    @PostMapping("/delete_permission")
    public ResponseEntity<String> deletePermission(@RequestBody Map<String, Object> payload) {
        Long permissionId = Long.valueOf(payload.get("permissionId").toString());

        permissionService.deletePermission(permissionId);

        return ResponseEntity.ok("Permission deleted successfully!");
    }

    @PostMapping("/admin/roles/{id}/delete")
    public ModelAndView deleteRole(@PathVariable Long id) {
        roleService.deleteRoleById(id);
        return new ModelAndView("redirect:/admin/roles");
    }
}


