package ru.singularity.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.singularity.school.service.AvatarServiceImpl;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarServiceImpl avatarService;

    public AvatarController(AvatarServiceImpl avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping(path = "/getFromDirectory")
    public ResponseEntity<String> getFromDirectory() {
        return ResponseEntity.ok("d");
    }

    @GetMapping(path = "/getFromDatabase")
    public ResponseEntity<String> getFromDatabase() {
        return ResponseEntity.ok("d");
    }

    @GetMapping(path = "/getById/{id}")
    public ResponseEntity<String> getById(@PathVariable int id) {
        return ResponseEntity.ok("d");
    }

    @PostMapping(path = "/upload/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> upload(@PathVariable Long id,
                                         @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(avatarService.uploadAvatar(id, file));
    }
}
