package ru.singularity.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.singularity.school.model.Avatar;
import ru.singularity.school.repository.AvatarRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class AvatarServiceImpl {
    private final AvatarRepository avatarRepository;

    @Value("upload.file.path")
    private String UPLOAD_DIR;

    public AvatarServiceImpl(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public String uploadAvatar(Long id, MultipartFile file) {
        try {
            Path uploadDir = Path.of(UPLOAD_DIR);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String fileName = file.getOriginalFilename();

            if (fileName == null) {
                throw new NullPointerException("Filename is null");
            }

            Path filePath = uploadDir.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Avatar avatar = new Avatar();

            avatar.setFilePath(filePath.toString());
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(file.getBytes());

            avatarRepository.save(avatar);
        }
        catch (Exception e) {
            return "Failed to upload file:" + e.getMessage();
        }

        return "Successfully uploaded " + file.getOriginalFilename();
    }

    private String createPathForFile(MultipartFile file) {
        return "";
    }
}
