package com.ohorodnik.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "genre")
public class Genre implements Cloneable{

    @Id
    private int id;
    private String name;

    public Genre clone() throws CloneNotSupportedException {
        return (Genre) super.clone();
    }
}
