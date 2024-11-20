package com.ohorodnik.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
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
@Table(name = "user", schema = "movieland")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @NotBlank(message = "Please add nickName")
    @Size(min = 3)
    private String nickname;
    @NotBlank(message = "Please add email")
    //TODO: add email validation regexp
    @Email
    private String email;
    @Transient
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Getter
    public enum Type {
        U
    }
}
