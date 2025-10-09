package com.codesensei.url_shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesensei.url_shortener.entity.UrlAnalytics;

public interface UrlAnalyticsRepository extends JpaRepository<UrlAnalytics, Long> {
    
    
}
