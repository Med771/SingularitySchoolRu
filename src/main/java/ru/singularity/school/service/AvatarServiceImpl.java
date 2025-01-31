package ru.singularity.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.singularity.school.model.Avatar;
import ru.singularity.school.model.Student;
import ru.singularity.school.repository.AvatarRepository;
import ru.singularity.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Value("file.local.file")
    private String UPLOAD_DIR;

    public AvatarServiceImpl(AvatarRepository avatarRepository,
                             StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile)
            throws RuntimeException, IOException {
        logger.info("Uploading avatar to {}", UPLOAD_DIR);

        Optional<Student> isStudent = studentRepository.findById(studentId);

        if (isStudent.isEmpty()) {
            logger.error("Student with id {} not found", studentId);
            throw new RuntimeException("Student with Id {} not found during uploadAvatar method call");
        }

        if (avatarFile.isEmpty()) {
            logger.error("Avatar file is empty");
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
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
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
        logger.info("Getting avatar from {}", UPLOAD_DIR);
        Optional<Student> isStudent = studentRepository.findById(studentId);

        if (isStudent.isEmpty()) {
            logger.error("Student with Id {} not found during getAvatar method call", studentId);
            throw new RuntimeException("Student not found");
        }

        Student student = isStudent.get();

        if (student.getAvatar() == null) {
            logger.error("Avatar with id {} not found during getAvatar method call", studentId);
            throw new RuntimeException("Avatar not found");
        }

        return student.getAvatar();
    }

    public List<Avatar> getPage(Integer page, Integer size) {
        logger.info("Getting page {} of {}", page, size);
        Pageable pageable = PageRequest.of(page, size);

        return avatarRepository.findAll(pageable).getContent();
    }

    public void deleteAvatar(Long studentId) {
        logger.info("Deleting avatar from {}", UPLOAD_DIR);
        Optional<Student> isStudent = studentRepository.findById(studentId);

        if (isStudent.isEmpty()) {
            return;
        }

        avatarRepository.deleteById((long) isStudent.get().getAvatar().getId());
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Path createPathForFile(Long id, MultipartFile file) {
        return Path.of(UPLOAD_DIR, "/", id + "." + getExtensions(Objects.requireNonNull(file.getOriginalFilename())));
    }
}
