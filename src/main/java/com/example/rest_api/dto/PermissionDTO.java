package com.example.rest_api.dto;

public class PermissionDTO {
    private Long id;
    private String http_method;
    private String url;

    public PermissionDTO(Long id, String http_method, String url) {
        this.id = id;
        this.http_method = http_method;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getHttp_method() {
        return http_method;
    }
    public String getUrl() {
        return url;
    }
}
