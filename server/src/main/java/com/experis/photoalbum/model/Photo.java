package com.experis.photoalbum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Title is required")
    @Size(min = 4, message = "Title must be at least 4 characters")
    private String title;

    @Column(length = 500)
    @NotEmpty(message = "Description is required")
    @Size(min = 6, max = 500, message = "Description must be min 6 characters and max 500")
    private String description;

    private Boolean isVisible;

    private String url;

    @ManyToMany()
    @JoinTable(
            name = "photo_category",
            joinColumns = {@JoinColumn(name = "photo_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}