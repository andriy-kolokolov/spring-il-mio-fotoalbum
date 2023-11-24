package com.experis.photoalbum.controller.api.v1;

import com.experis.photoalbum.dto.PhotoDTO;
import com.experis.photoalbum.dto.UserDTO;
import com.experis.photoalbum.model.Photo;
import com.experis.photoalbum.model.User;
import com.experis.photoalbum.service.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/list/{id}")
    public ResponseEntity<List<PhotoDTO>> findPhotosByUserId(
            @PathVariable(value = "id") Long id
    ) {
        List<Photo> photos = photoService.findByUserId(id);

        if (photos == null || photos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PhotoDTO> photoDTOs = photos.stream()
                .map(PhotoDTO::fromPhoto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(photoDTOs);
    }
}
