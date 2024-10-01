package com.ohorodnik.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "genre", schema = "movieland")
public class Genre {

    @Id
    private int id;
    @NotBlank(message = "Please add item name")
    @Size(min = 3)
    private String name;
}
