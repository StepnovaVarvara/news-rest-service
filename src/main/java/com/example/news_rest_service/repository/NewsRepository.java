package com.example.news_rest_service.repository;

import com.example.news_rest_service.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    @Override
    @EntityGraph(attributePaths = {"commentList"})
    List<News> findAll();

    @Override
    @EntityGraph(attributePaths = {"commentList", "user", "category"})
    Optional<News> findById(Long newsId);

    Page<News> findAllByCategoryId(Long categoryId, Pageable pageable);

}

