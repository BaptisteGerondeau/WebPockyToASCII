package com.pocky.webpockytoascii.model.ASCIIMatrix;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "asciimatrix")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ASCIIMatrix {

    @Id
    @GeneratedValue
    private long id;

    @Column(name="imageid", nullable=false, length=255)
    private long imageId;

    @Column(name="type", nullable=false, length=255)
    @NotBlank(message = "type cannot be empty")
    private String type;

    @Lob
    @Column(name="matrix_data", nullable=true, length=409600)
    @JsonIgnore
    private byte[] matrixData;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ASCIIMatrix))
            return false;
        ASCIIMatrix image = (ASCIIMatrix) o;
        return Objects.equals(this.id, image.id) && Objects.equals(this.imageId, image.imageId)
                && Objects.equals(this.type, image.type) && Arrays.equals(this.matrixData, image.matrixData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.imageId);
    }

    @Override
    public String toString() {
        return "ASCIIMatrix{" + "id=" + this.id + ", imageId='" + this.imageId + '\'' + ", type='" + this.type + '\'' + '}';
    }

}
