package com.experis.photoalbum.repository;

import com.experis.photoalbum.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

   @Query("SELECT c FROM Category c JOIN c.photos p WHERE p.id = :photoId")
   List<Category> findCategoriesByPhotoId(@Param("photoId") Long photoId);

   // get categories by ids
   Set<Category> findCategoriesByIdIn(List<Long> ids);

}
