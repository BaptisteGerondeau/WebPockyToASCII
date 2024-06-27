package com.pocky.webpockytoascii.model.Image;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="name", nullable=false, length=255)
    @NotBlank(message = "name is mandatory")
    private String name;

    @Column(name="type", nullable=false, length=80)
    @NotBlank(message = "type is mandatory")
    private String type;

    @Lob
    @Column(name="image_data", nullable=true, length=409600)
    @JsonIgnore
    private byte[] imageData;

    public Image(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Image))
            return false;
        Image image = (Image) o;
        return Objects.equals(this.id, image.id) && Objects.equals(this.name, image.name)
                && Objects.equals(this.type, image.type) && Arrays.equals(this.imageData, image.imageData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "{" + "id=" + this.id + ", name='" + this.name + '\'' + ", type='" + this.type + '\'' + '}';
    }
}