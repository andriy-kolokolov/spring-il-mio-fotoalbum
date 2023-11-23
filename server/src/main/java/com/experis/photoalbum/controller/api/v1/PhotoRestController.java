package com.experis.photoalbum.controller.api.v1;

import com.experis.photoalbum.model.Photo;
import com.experis.photoalbum.service.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/photo")
public class PhotoRestController {

    private final PhotoService photoService;

    public PhotoRestController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Photo>> findPhotos(
            @RequestParam(value = "title", required = false) String title
    ) {
        List<Photo> photos = (title != null && !title.trim().isEmpty()) ?
                photoService.findByTitle(title) :
                photoService.findAll();
        return ResponseEntity.ok(photos);
    }
}
