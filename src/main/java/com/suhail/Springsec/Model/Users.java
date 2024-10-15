package com.suhail.Springsec.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
@Entity
public class Users {
    @Id // primary key
    private int id;
    private String username;
    private String password;
}
