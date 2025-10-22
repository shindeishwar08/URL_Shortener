package com.codesensei.url_shortener.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesensei.url_shortener.dto.UrlRequestDto;
import com.codesensei.url_shortener.dto.UrlResponseDto;
import com.codesensei.url_shortener.service.UrlService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService=urlService;
    }
    
    @PostMapping("/url")
    public ResponseEntity<UrlResponseDto> createShortUrl(@Valid @RequestBody UrlRequestDto request, HttpServletRequest httpRequest){
        UrlResponseDto response = urlService.createShortUrl(request, httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode, HttpServletRequest request){
        String originalUrl = urlService.getOriginalUrl(shortCode, request);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();

    }
    
    
}


