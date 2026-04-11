package com.springproject.spring_boot_jwt.models;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="token")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean expired;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
