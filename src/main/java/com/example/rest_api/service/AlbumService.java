package com.example.rest_api.service;

import com.example.rest_api.database.users.model.PermissionEntity;
import com.example.rest_api.database.users.model.RoleEntity;
import com.example.rest_api.database.resources.repository.AlbumRepository;
import com.example.rest_api.database.resources.model.AlbumEntity;
import com.example.rest_api.database.users.model.UserEntity;
import com.example.rest_api.database.users.repository.PermissionRepository;
import com.example.rest_api.database.users.repository.RoleRepository;
import com.example.rest_api.database.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<AlbumEntity> findAll() {
        return this.albumRepository.findAll();
    }

    public void createAlbum(String albumName, UserEntity user) {
        AlbumEntity album = new AlbumEntity();
        album.setName(albumName);
        albumRepository.save(album);

        albumName = albumName.toUpperCase().trim();
        String adminRoleName = albumName + "_ALBUM_ADMIN";
        String userRoleName = albumName + "_ALBUM";

        RoleEntity adminRole = new RoleEntity();
        adminRole.setName(adminRoleName);
        roleRepository.save(adminRole);

        RoleEntity userRole = new RoleEntity();
        userRole.setName(userRoleName);
        roleRepository.save(userRole);

        user.getRoles().add(adminRole);
        userRepository.save(user);

        List<String> httpMethods = new ArrayList<>();
        httpMethods.add("GET");
        httpMethods.add("POST");
        httpMethods.add("PATCH");
        httpMethods.add("DELETE");

        PermissionEntity permission = new PermissionEntity();

        for (String method : httpMethods) {
            permission = new PermissionEntity();
            permission.setHttp_method(method);
            permission.setRole(adminRole);
            if (method.equals("DELETE")) {
                permission.setUrl("/photos/" + albumName + "/**");
            } else {
                permission.setUrl("/photos/" + albumName);
            }
            permissionRepository.save(permission);
        }

        permission = new PermissionEntity();
        permission.setHttp_method("GET");
        permission.setUrl("/photos/" + albumName);
        permission.setRole(userRole);
        permissionRepository.save(permission);
    }

    public void deleteAlbum(int albumId) {
        String albumName = albumRepository.findNameById(albumId);
        albumRepository.deleteById(albumId);
        permissionRepository.deletePermissionsForAlbum(albumName);

//        String adminRoleName = albumName.toUpperCase() + "_ALBUM_ADMIN";
//        String userRoleName = albumName.toUpperCase() + "_ALBUM";

//        Integer adminRoleId = roleRepository.findRoleByName(adminRoleName);
//        Integer userRoleId = roleRepository.findRoleByName(userRoleName);

//        permissionRepository.deletePermissionsForRole(Long.valueOf(adminRoleId));
//        permissionRepository.deletePermissionsForRole(Long.valueOf(userRoleId));

//        roleRepository.deleteAssociatedRole(adminRoleId);
//        roleRepository.deleteAssociatedRole(userRoleId);

//        roleRepository.deleteRole(adminRoleName);
//        roleRepository.deleteRole(userRoleName);
    }

    public Optional<AlbumEntity> findAlbumById(Integer albumId) {
        return albumRepository.findById(albumId);
    }

    public Optional<AlbumEntity> findByName(String albumName) {
        return albumRepository.findByName(albumName);
    }

//    public boolean canDelete(Integer albumId, UserEntity user) {
//        AlbumEntity album = albumRepository.findById(albumId).orElse(null);
//        Iterable<Long> roleIds = userRepository.findAdminRoles(user.getId());
//        for (Long roleId : roleIds) {
//            RoleEntity role = roleRepository.findById(roleId).orElse(null);
//            if (role.getName().startsWith(album.getName())) {
//                Iterable<PermissionEntity> permissions = permissionRepository.findPermissionsByRole(roleId);
//                for (PermissionEntity permission : permissions) {
//                    if (permission.getHttp_method().equals("DELETE")) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    public boolean canDelete(Integer albumId, UserEntity user) {
        AlbumEntity album = albumRepository.findById(albumId).orElse(null);
        Iterable<Long> roleIds = userRepository.findAdminRoles(user.getId());
        for (Long roleId : roleIds) {
            Iterable<PermissionEntity> permissions = permissionRepository.findPermissionsByRole(roleId);
            for (PermissionEntity permission : permissions) {
                if (permission.getHttp_method().equals("DELETE"))
                {
                    if (permission.getUrl().equals("/photos/" + album.getName())
                        || permission.getUrl().equals("/photos/" + album.getName() + "/**")
                            || permission.getUrl().equals("/**"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

//    public boolean canAdd(Integer albumId, UserEntity user) {
//        AlbumEntity album = albumRepository.findById(albumId).orElse(null);
//        Iterable<Long> roleIds = userRepository.findAdminRoles(user.getId());
//        for (Long roleId : roleIds) {
//            RoleEntity role = roleRepository.findById(roleId).orElse(null);
//            if (role.getName().startsWith(album.getName())) {
//                Iterable<PermissionEntity> permissions = permissionRepository.findPermissionsByRole(roleId);
//                for (PermissionEntity permission : permissions) {
//                    if (permission.getHttp_method().equals("POST") || permission.getHttp_method().equals("PATCH")) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    public boolean canPostPatch(Integer albumId, UserEntity user) {
        AlbumEntity album = albumRepository.findById(albumId).orElse(null);
        Iterable<Long> roleIds = userRepository.findAdminRoles(user.getId());
        for (Long roleId : roleIds) {
            Iterable<PermissionEntity> permissions = permissionRepository.findPermissionsByRole(roleId);
            for (PermissionEntity permission : permissions) {
                if (permission.getHttp_method().equals("POST") || permission.getHttp_method().equals("PATCH"))
                {
                    if (permission.getUrl().equals("/photos/" + album.getName())
                        || permission.getUrl().equals("/photos/" + album.getName() + "/**")
                        || permission.getUrl().equals("/**"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<AlbumEntity> findAlbums(UserEntity user) {
        List<AlbumEntity> albums = new ArrayList<>();
        Iterable<Long> roleIds = userRepository.findAdminRoles(user.getId());

        for (Long roleId : roleIds) {
            RoleEntity role = roleRepository.findById(roleId).orElse(null);
            if (role == null) continue;

            Iterable<PermissionEntity> permissions = permissionRepository.findPermissionsByRole(roleId);

            String suffix = role.getName().endsWith("_ALBUM_ADMIN") ? "_ALBUM_ADMIN" :
                    role.getName().endsWith("_ALBUM") ? "_ALBUM" : null;

            if (suffix != null) {
                String albumName = role.getName().substring(0, role.getName().length() - suffix.length());
                for (PermissionEntity permission : permissions) {
                    if ("GET".equals(permission.getHttp_method())) {
                        albumRepository.findByName(albumName).ifPresent(albums::add);
                        break;
                    }
                }
            }
        }
        return albums;
    }

    public AlbumEntity findAlbumByName(String albumName) {
        return albumRepository.findAlbumByName(albumName);
    }
}