package com.example.springessentialguide.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String name;
    private String email;
    private String role;
}
