package com.ohorodnik.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "review", schema = "movieland")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_sequence_generator")
    @SequenceGenerator(name = "review_sequence_generator", sequenceName = "review_id_seq", schema = "movieland", allocationSize = 1)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @NotBlank(message = "Please add review")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
