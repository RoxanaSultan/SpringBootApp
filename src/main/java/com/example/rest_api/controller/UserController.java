package com.example.rest_api.controller;

import com.example.rest_api.dto.RoleDTO;
import org.springframework.stereotype.Controller;
import com.example.rest_api.database.users.model.RoleEntity;
import com.example.rest_api.service.RoleService;
import com.example.rest_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/update_roles")
    public ResponseEntity<String> updateRoles(@RequestBody Map<String, Object> payload) {
        Long userId = Long.valueOf(payload.get("userId").toString());
        List<String> roles = (List<String>) payload.get("roles");

        roleService.updateUserRoles(Math.toIntExact(userId), roles);
        return ResponseEntity.ok("Roles updated successfully!");
    }


    @GetMapping("/get_roles/{id}")
    public ResponseEntity<Map<String, Object>> getUserRoles(@PathVariable Long id) {
        List<RoleEntity> allRoles = roleService.getAllRoles();
        List<RoleEntity> userRoles = roleService.getUserRoles(id);

        List<RoleDTO> allRolesDTO = allRoles.stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .toList();

        List<RoleDTO> userRolesDTO = userRoles.stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("allRoles", allRolesDTO);
        response.put("userRoles", userRolesDTO);

        return ResponseEntity.ok(response);
    }

}


