package ru.singularity.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Avatar {
    @Id
    @GeneratedValue
    private int id;

    private String filePath;
    private Long fileSize;
    private String mediaType;
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
