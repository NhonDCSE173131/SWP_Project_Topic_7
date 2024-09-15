package com.product.server.koi_control_application.controller;


import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@RolesAllowed({"ROLE_ADMIN"})
public class ImageController {
    private static final String IMAGE_DIR = "image/";

    @GetMapping("/{filename:.+}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String filename) {
        try {
            ClassPathResource imgFile = new ClassPathResource(IMAGE_DIR + filename);

            if (imgFile.exists()) {
                InputStream in = imgFile.getInputStream();
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Hoặc xác định loại MIME dựa trên phần mở rộng của file
                        .body(new InputStreamResource(in));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
