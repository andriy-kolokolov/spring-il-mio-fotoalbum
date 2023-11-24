package com.experis.photoalbum.response;

import com.experis.photoalbum.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class SigninResponse {
    private String token;
    private String message;
    private Boolean success;
    private UserDTO user;
}
