package com.experis.photoalbum.controller.api.v1;

import com.experis.photoalbum.dto.PhotoDTO;
import com.experis.photoalbum.model.Category;
import com.experis.photoalbum.model.Photo;
import com.experis.photoalbum.model.User;
import com.experis.photoalbum.request.PhotoRequest;
import com.experis.photoalbum.response.ApiResponse;
import com.experis.photoalbum.service.CategoryService;
import com.experis.photoalbum.service.PhotoService;
import com.experis.photoalbum.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/photo")
public class PhotoRestController {

    private final PhotoService photoService;
    private final UserService userService;
    private final CategoryService categoryService;

    public PhotoRestController(PhotoService photoService, UserService userService, CategoryService categoryService) {
        this.photoService = photoService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<PhotoDTO>> findPhotos(
            @RequestParam(value = "title", required = false) String title
    ) {
        List<Photo> photos = (title != null && !title.trim().isEmpty()) ?
                photoService.findByTitle(title) :
                photoService.findAllVisible();
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
        // retrieve all photos for super admin user
        boolean userIsSuperAdmin = userService.isUserSuperAdmin(id);

        List<Photo> photos;
        if (userIsSuperAdmin) {
            photos = photoService.findAll();
        } else {
            photos = photoService.findByUserId(id);
        }

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
        photo.setUrl(photoRequest.getUrl());

        Set<Category> categories = categoryService.findByIds(photoRequest.getCategoryIds());
        photo.setCategories(categories);

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
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePhoto(@PathVariable(value = "id") Long id,
                                         @RequestBody PhotoRequest photoRequest) {
        Photo photo = photoService.findById(id);
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        photo.setTitle(photoRequest.getTitle());
        photo.setDescription(photoRequest.getDescription());
        photo.setIsVisible(photoRequest.getIsVisible());

        // Retrieve the updated categories from the database
        Set<Category> updatedCategories = categoryService.findByIds(photoRequest.getCategoryIds());
        photo.setCategories(updatedCategories); // Update the photo with new categories

        photoService.save(photo);

        return ResponseEntity
                .ok(new ApiResponse("Photo updated successfully", true));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            // define the directory where files will be saved
            String uploadDir = "server/src/main/resources/static/uploads";

            // create the directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdir();
            }

            // Save the file
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);

            return ResponseEntity.ok("File uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not upload the file: " + e.getMessage());
        }
    }
}
