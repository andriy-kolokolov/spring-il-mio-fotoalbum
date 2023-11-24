package com.experis.photoalbum.dto;

import com.experis.photoalbum.model.Photo;
import com.experis.photoalbum.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {
    private int id;
    private String title;
    private String description;
    private Boolean isVisible;
    private UserDTO user;

    public static PhotoDTO fromPhoto(Photo photo) {
        PhotoDTO dto = new PhotoDTO();
        dto.setId(photo.getId());
        dto.setTitle(photo.getTitle());
        dto.setDescription(photo.getDescription());
        dto.setIsVisible(photo.getIsVisible());
        dto.setUser(UserDTO.fromUser(photo.getUser()));

        return dto;
    }
}
