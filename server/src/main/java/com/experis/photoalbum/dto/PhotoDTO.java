package com.experis.photoalbum.dto;

import com.experis.photoalbum.model.Photo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {
    private int id;
    private String title;
    private String description;
    private Boolean isVisible;
    private UserDTO user;
    private List<CategoryDTO> categories;

    public static PhotoDTO fromPhoto(Photo photo) {
        PhotoDTO dto = new PhotoDTO();
        dto.setId(photo.getId());
        dto.setTitle(photo.getTitle());
        dto.setDescription(photo.getDescription());
        dto.setIsVisible(photo.getIsVisible());
        dto.setUser(UserDTO.fromUser(photo.getUser()));

        if (photo.getCategories() != null) {
            dto.setCategories(photo
                    .getCategories()
                    .stream()
                    .map(CategoryDTO::fromCategory)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
