package ru.singularity.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.singularity.school.model.Avatar;
import ru.singularity.school.service.AvatarServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/student/avatar")
public class AvatarController {
    private final AvatarServiceImpl avatarService;

    public AvatarController(AvatarServiceImpl avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping(path = "/getFromDirectory/{id}")
    public void getFromDirectory(@PathVariable Long id, HttpServletResponse response) throws IOException {
        try {
            Avatar avatar = avatarService.getAvatar(id);

            Path path = Path.of(avatar.getFilePath());

            try(InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream()) {
                response.setStatus(200);
                response.setContentType(avatar.getMediaType());
                response.setContentLength(Math.toIntExact(avatar.getFileSize()));
                is.transferTo(os);
            }
        }
        catch (RuntimeException e) {
            response.setStatus(500);
        }
    }

    @GetMapping(path = "/getFromDatabase/{id}")
    public ResponseEntity<byte[]> getFromDatabase(@PathVariable Long id) {
        try {
            Avatar avatar = avatarService.getAvatar(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(avatar.getData().length);

            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/getPage/{page}/{size}")
    public ResponseEntity<List<Avatar>> getPage(@PathVariable Integer page, @PathVariable Integer size) {
        try {
            return ResponseEntity.ok(avatarService.getPage(page, size));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(path = "/upload/{id}", consumes = "multipart/form-data")
    public ResponseEntity<HttpStatus> upload(@PathVariable Long id,
                                             @RequestParam("file") MultipartFile file) {
        try {
            avatarService.uploadAvatar(id, file);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@RequestParam("id") Long id) {
        avatarService.deleteAvatar(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
