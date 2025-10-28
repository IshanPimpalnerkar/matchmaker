package com.matchmaker.service;

import com.matchmaker.model.UserProfile;
import com.matchmaker.repository.UserRepository;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserImageService {

    @Autowired
    private ImageKit imageKit;

    @Autowired
    private UserRepository userRepository;

    public UserProfile uploadUserImages(Long userId, List<MultipartFile> files) throws IOException, ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {
        Optional<UserProfile> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        UserProfile user = optionalUser.get();
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String base64 = Base64.getEncoder().encodeToString(file.getBytes());
            FileCreateRequest request = new FileCreateRequest(base64, file.getOriginalFilename());
            request.setFolder("/users/" + userId);

            Result result = imageKit.upload(request);
            uploadedUrls.add(result.getUrl());
        }

        user.getImagePaths().addAll(uploadedUrls);
        userRepository.save(user);

        return user;
    }
}
