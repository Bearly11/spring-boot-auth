package com.springproject.spring_boot_jwt.models;

import com.springproject.spring_boot_jwt.enums.Role;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "password")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


}
