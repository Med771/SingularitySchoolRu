package ru.singularity.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.singularity.school.model.Avatar;
import ru.singularity.school.model.Student;
import ru.singularity.school.repository.AvatarRepository;
import ru.singularity.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    @Value("file.local.path")
    private String UPLOAD_DIR;

    @Value("file.url.path")
    private String UPLOAD_URL;

    public AvatarServiceImpl(AvatarRepository avatarRepository,
                             StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile)
            throws RuntimeException, IOException {
        Optional<Student> isStudent = studentRepository.findById(studentId);

        if (isStudent.isEmpty()) {
            throw new RuntimeException("Student not found");
        }

        if (avatarFile.isEmpty()) {
            throw new RuntimeException("Avatar file is empty");
        }

        Student student = isStudent.get();

        Path filePath = createPathForFile(studentId, avatarFile);

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = new Avatar();

        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepository.save(avatar);
    }

    public Avatar getAvatar(Long studentId) {
        Optional<Student> isStudent = studentRepository.findById(studentId);

        if (isStudent.isEmpty()) {
            throw new RuntimeException("Student not found");
        }

        Student student = isStudent.get();

        if (student.getAvatar() == null) {
            throw new RuntimeException("Avatar not found");
        }

        return student.getAvatar();
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Path createPathForFile(Long id, MultipartFile file) {
        return Path.of(UPLOAD_DIR, "/", id + "." + getExtensions(Objects.requireNonNull(file.getOriginalFilename())));
    }
}
