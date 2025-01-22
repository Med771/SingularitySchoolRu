package ru.singularity.school.model;

import jakarta.persistence.*;

@Entity
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

    // Get
    public int getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }
    public byte[] getData() {
        return data;
    }

    public Student getStudent() {
        return student;
    }

    // Set
    public void setId(int id) {
        this.id = id;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
