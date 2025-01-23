package ru.singularity.school.dto;

import lombok.Data;

@Data
public class CurrentStudent {
    private Long id;
    private String name;
    private int age;
    private Long faculty;
    private String Avatar;
}
