package com.experis.photoalbum.dto;

import com.experis.photoalbum.model.Role;
import com.experis.photoalbum.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private static final Logger log = LoggerFactory.getLogger(UserDTO.class);

    private Long id;
    private String username;
    private Set<Role> roles;

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles());

        return dto;
    }
}
