package com.experis.photoalbum.service;

import com.experis.photoalbum.model.Photo;
import com.experis.photoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    public List<Photo> findByTitle(String title) {
        return photoRepository.findByTitleContainingIgnoreCaseOrderByTitle(title);
    }

    public Photo getById(Long id) {
        return photoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Photo with id " + id + " does not exist")
        );
    }

    public void save(Photo pizza) {
        photoRepository.save(pizza);
    }

    public void delete(Long id) {
        photoRepository.deleteById(id);
    }
}
