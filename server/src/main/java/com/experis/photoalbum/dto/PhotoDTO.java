package com.experis.photoalbum.dto;

import com.experis.photoalbum.model.Photo;
import com.experis.photoalbum.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {
    private int id;
    private String title;
    private String description;
    private boolean isVisible;
    private UserDTO user;

    public static PhotoDTO fromPhoto(Photo photo) {
        PhotoDTO dto = new PhotoDTO();
        dto.setId(photo.getId());
        dto.setTitle(photo.getTitle());
        dto.setDescription(photo.getDescription());
        dto.setVisible(photo.isVisible());
        dto.setUser(UserDTO.fromUser(photo.getUser()));

        return dto;
    }
}
