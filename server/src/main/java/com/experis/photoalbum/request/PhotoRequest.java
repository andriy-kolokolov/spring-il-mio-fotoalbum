package com.experis.photoalbum.request;

import lombok.Data;

@Data
public class PhotoRequest {
    private Long userId;
    private String title;
    private String description;
    private boolean isVisible;
}
