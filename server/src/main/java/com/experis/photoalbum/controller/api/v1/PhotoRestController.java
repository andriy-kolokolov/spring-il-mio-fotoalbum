package com.experis.photoalbum.controller.api.v1;

import com.experis.photoalbum.dto.PhotoDTO;
import com.experis.photoalbum.model.Photo;
import com.experis.photoalbum.model.User;
import com.experis.photoalbum.request.PhotoRequest;
import com.experis.photoalbum.response.ApiResponse;
import com.experis.photoalbum.service.PhotoService;
import com.experis.photoalbum.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/photo")
public class PhotoRestController {

    private final PhotoService photoService;
    private final UserService userService;

    public PhotoRestController(PhotoService photoService, UserService userService) {
        this.photoService = photoService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<PhotoDTO>> findPhotos(
            @RequestParam(value = "title", required = false) String title
    ) {
        List<Photo> photos = (title != null && !title.trim().isEmpty()) ?
                photoService.findByTitle(title) :
                photoService.finAllVisible();
        List<PhotoDTO> photoDTOs = photos
                .stream()
                .map(PhotoDTO::fromPhoto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(photoDTOs);
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

    //create photo
    @PostMapping("/add")
    public ResponseEntity<?> createPhoto(@RequestBody PhotoRequest photoRequest) {
        User user = userService.getUserById(photoRequest.getUserId());
        Photo photo = new Photo();
        photo.setUser(user);
        photo.setTitle(photoRequest.getTitle());
        photo.setDescription(photoRequest.getDescription());
        photo.setIsVisible(photoRequest.getIsVisible());

        photoService.save(photo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse("Photo created successfully", true));
    }

    // delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePhoto(@PathVariable(value = "id") Long id) {
        photoService.delete(id);
        return ResponseEntity
                .ok(new ApiResponse("Photo deleted successfully", true));
    }

    // put
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePhoto(@PathVariable(value = "id") Long id,
                                         @RequestBody PhotoRequest photoRequest) {
        Photo photo = photoService.findById(id);
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }
        photo.setTitle(photoRequest.getTitle());
        photo.setDescription(photoRequest.getDescription());
        photo.setIsVisible(photoRequest.getIsVisible());

        photoService.save(photo);

        return ResponseEntity
                .ok(new ApiResponse("Photo updated successfully", true));

    }
}
