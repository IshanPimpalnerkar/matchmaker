package com.matchmaker.controller;

import com.matchmaker.model.UserProfile;
import com.matchmaker.service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserImageController {

    @Autowired
    private UserImageService userImageService;

    @PostMapping("/{userId}/upload-images")
    public ResponseEntity<?> uploadUserImages(
            @PathVariable Long userId,
            @RequestParam("files") MultipartFile[] files) {
        try {
            List<MultipartFile> fileList = Arrays.asList(files);
            UserProfile updatedUser = userImageService.uploadUserImages(userId, fileList);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
