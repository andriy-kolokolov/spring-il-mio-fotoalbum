package com.experis.photoalbum.controller.mvc;

import com.experis.photoalbum.model.Category;
import com.experis.photoalbum.model.Photo;
import com.experis.photoalbum.model.User;
import com.experis.photoalbum.request.PhotoRequest;
import com.experis.photoalbum.service.CategoryService;
import com.experis.photoalbum.service.PhotoService;
import com.experis.photoalbum.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/photo")
@AllArgsConstructor
public class PhotoController {
    private final PhotoService photoService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String findPhotos(
            @RequestParam(value = "title", required = false) String title,
            Model model
    ) {
        List<Photo> photos = (title != null && !title.trim().isEmpty()) ?
                photoService.findByTitle(title) :
                photoService.findAllVisible();

        User user = userService.getAuthenticatedUser();

        model.addAttribute("photos", photos);
        model.addAttribute("user", user);

        return "photo/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Photo photo = photoService.findById(id);
        if (photo == null) {
            return "redirect:/photo/list";
        }

        model.addAttribute("photo", photo);
        model.addAttribute("categories", categoryService.findAll());
        return "photo/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePhoto(
            @PathVariable Long id,
            @ModelAttribute @Valid PhotoRequest photoRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("photo", photoRequest);
            model.addAttribute("categories", categoryService.findAll());
            return "photo/edit";
        }

        Photo existingPhoto = photoService.findById(id);
        if (existingPhoto == null) {
            return "redirect:/photo/list";
        }

        updatePhotoEntity(existingPhoto, photoRequest);
        photoService.save(existingPhoto);
        redirectAttributes.addFlashAttribute("message", "Photo updated successfully!");
        return "redirect:/photo/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePhoto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Photo photo = photoService.findById(id);
        if (photo != null) {
            photoService.delete(photo.getId());
            redirectAttributes.addFlashAttribute("message", "Photo deleted successfully!");
        }
        return "redirect:/photo/list";
    }

    // Helper method to convert PhotoRequest to Photo entity
    private Photo convertToPhotoEntity(PhotoRequest photoRequest) {
        Photo photo = new Photo();
        photo.setTitle(photoRequest.getTitle());
        photo.setDescription(photoRequest.getDescription());
        photo.setUrl(photoRequest.getUrl());
        photo.setIsVisible(photoRequest.getIsVisible());

        // Handling the categories
        if (photoRequest.getCategoryIds() != null) {
            Set<Category> categories = photoRequest.getCategoryIds().stream()
                    .map(categoryService::findById)
                    .collect(Collectors.toSet());
            photo.setCategories(categories);
        }

        return photo;
    }

    // Helper method to update existing Photo entity from PhotoRequest
    private void updatePhotoEntity(Photo existingPhoto, PhotoRequest photoRequest) {
        existingPhoto.setTitle(photoRequest.getTitle());
        existingPhoto.setDescription(photoRequest.getDescription());
        existingPhoto.setUrl(photoRequest.getUrl());
        existingPhoto.setIsVisible(photoRequest.getIsVisible());

        // Handling the categories
        if (photoRequest.getCategoryIds() != null) {
            Set<Category> categories = photoRequest.getCategoryIds().stream()
                    .map(categoryService::findById)
                    .collect(Collectors.toSet());
            existingPhoto.setCategories(categories);
        }
    }

}
