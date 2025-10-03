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

@RestController
@RequestMapping("/api/v1")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService=urlService;
    }
    
    @PostMapping("/url")
    public ResponseEntity<UrlResponseDto> createShortUrl(@RequestBody UrlRequestDto request){
        UrlResponseDto response = urlService.createShortUrl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode){
        String originalUrl = urlService.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();

    }
    
    
}


