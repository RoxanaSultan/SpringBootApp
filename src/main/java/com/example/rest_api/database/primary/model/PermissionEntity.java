package com.example.rest_api.database.primary.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode
@Table(name = "permissions")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String http_method;
    private String url;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}