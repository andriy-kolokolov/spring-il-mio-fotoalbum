package com.experis.photoalbum.repository;

import com.experis.photoalbum.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByTitleContainingIgnoreCaseOrderByTitle(String title);

    // get user photos
    List<Photo> findByUserId(Long userId);

    List<Photo> getAllByIsVisibleTrue();
}
