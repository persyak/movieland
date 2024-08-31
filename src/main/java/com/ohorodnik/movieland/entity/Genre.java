package com.ohorodnik.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "genre", schema = "movieland")
@JsonIgnoreProperties(value = "movies")
public class Genre implements Cloneable {

    @Id
    private int id;
    private String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER)
    private List<Movie> movies = new ArrayList<>();

    public Genre clone() throws CloneNotSupportedException {
        return (Genre) super.clone();
    }
}
