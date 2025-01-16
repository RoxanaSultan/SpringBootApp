package com.example.rest_api.database.secondary.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "photos", schema = "public", catalog = "resources_database")
public class PhotoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "album_id")
    private int albumId;
    @Basic
    @Column(name = "image_data")
    private byte[] imageData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoEntity that = (PhotoEntity) o;
        return id == that.id && albumId == that.albumId && Arrays.equals(imageData, that.imageData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, albumId);
        result = 31 * result + Arrays.hashCode(imageData);
        return result;
    }
}
