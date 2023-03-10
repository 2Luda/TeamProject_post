package com.sparta.teamproject_post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Entity(name="USERS")
@NoArgsConstructor
@Getter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
}


