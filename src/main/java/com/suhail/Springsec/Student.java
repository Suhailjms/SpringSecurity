package com.suhail.Springsec;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Student {
    private int id;
    private String name;
    private int marks;
}
