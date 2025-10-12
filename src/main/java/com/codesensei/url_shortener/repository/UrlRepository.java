package com.codesensei.url_shortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codesensei.url_shortener.entity.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    
    Optional<Url> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);
    
}
