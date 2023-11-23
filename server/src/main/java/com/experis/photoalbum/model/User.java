package com.experis.photoalbum.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @OneToMany(
            mappedBy = "sender",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Message> sentMessages = new HashSet<>();

    @OneToMany(
            mappedBy = "receiver",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Message> receivedMessages = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Photo> photos = new HashSet<>();
}
