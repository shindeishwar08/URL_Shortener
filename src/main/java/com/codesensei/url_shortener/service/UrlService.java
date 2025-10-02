package com.codesensei.url_shortener.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.codesensei.url_shortener.dto.UrlRequestDto;
import com.codesensei.url_shortener.dto.UrlResponseDto;
import com.codesensei.url_shortener.entity.Url;
import com.codesensei.url_shortener.repository.UrlRepository;

@Service
public class UrlService {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 7;
    private static final SecureRandom RANDOM = new SecureRandom();

    
    //Dependency for Repositiry Layer
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository){
        this.urlRepository=urlRepository;
    }


    public UrlResponseDto createShortUrl(UrlRequestDto request) {

        String shortCode = generateShortCode();

        //New Url Entity object and set its properties
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());

        //Save new entity to db using repo layer
        urlRepository.save(url);

        UrlResponseDto response = new UrlResponseDto();
        response.setShortUrl("http://localhost:8080/" + shortCode);
        
        return response;
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'createShortUrl'");
    }
    
    //Base Logic
    public String generateShortCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    
}