package com.experis.photoalbum.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SigninResponse {
    private String token;
    private String message;
    private Boolean success;
}
