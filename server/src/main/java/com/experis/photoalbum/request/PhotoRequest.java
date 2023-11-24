package com.experis.photoalbum.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PhotoRequest {
    private Long userId;
    private String title;
    private String description;
    private Boolean isVisible;
}
