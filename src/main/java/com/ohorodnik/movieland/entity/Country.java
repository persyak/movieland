package com.ohorodnik.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "country", schema = "movieland")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_sequence_generator")
    @SequenceGenerator(name = "country_sequence_generator", sequenceName = "country_id_seq", schema = "movieland", allocationSize = 1)
    private Integer id;
    @NotBlank(message = "Please add country name")
    @Size(min = 3)
    private String name;
}
