package com.experis.photoalbum.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class PhotoRequest {
    private Long userId;
    private String title;
    private String description;
    private Boolean isVisible;
    private List<Long> categoryIds;
    private String url;
}
