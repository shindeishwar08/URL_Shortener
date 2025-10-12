package com.codesensei.url_shortener.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.codesensei.url_shortener.dto.UrlRequestDto;
import com.codesensei.url_shortener.dto.UrlResponseDto;
import com.codesensei.url_shortener.entity.Url;
import com.codesensei.url_shortener.entity.UrlAnalytics;
import com.codesensei.url_shortener.exception.AliasAlreadyExistsException;
import com.codesensei.url_shortener.exception.UrlExpiredException;
import com.codesensei.url_shortener.exception.UrlNotFoundException;
import com.codesensei.url_shortener.repository.UrlAnalyticsRepository;
import com.codesensei.url_shortener.repository.UrlRepository;

import jakarta.servlet.http.HttpServletRequest;



@Service
public class UrlService {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 7;
    private static final SecureRandom RANDOM = new SecureRandom();

    
    //Dependency for Repositiry Layer
    private final UrlRepository urlRepository;
    private final UrlAnalyticsRepository urlAnalyticsRepository;

    public UrlService(UrlRepository urlRepository, UrlAnalyticsRepository urlAnalyticsRepository){
        this.urlRepository=urlRepository;
        this.urlAnalyticsRepository=urlAnalyticsRepository;
    }


    public UrlResponseDto createShortUrl(UrlRequestDto request) {

        String shortCode;

        if(request.getAlias()!=null && !request.getAlias().isBlank()){
            boolean aliasExists = urlRepository.existsByShortCode(request.getAlias());

            if(aliasExists){
                throw new AliasAlreadyExistsException("Alias is already in use");
            }

            shortCode = request.getAlias();
        }else{
            
            shortCode = generateShortCode();
        }


        //New Url Entity object and set its properties
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());

        if(request.getExpiresAt()!=null){
            url.setExpiresAt(request.getExpiresAt());
        }
        //Save new entity to db using repo layer
        urlRepository.save(url);

        UrlResponseDto response = new UrlResponseDto();
        response.setShortUrl("http://localhost:8080/api/v1/" + shortCode);
        
        return response;
    
    }
    
    //ShortCode Logic(Part1)
    public String generateShortCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public String getOriginalUrl(String shortCode, HttpServletRequest request){
        Url url = urlRepository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException("URL not found for code: " +shortCode));
        if(url.getExpiresAt()!=null && url.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new UrlExpiredException("This short-link has Expired!");
        }
        
        //analytics set and store
        UrlAnalytics analytics = new UrlAnalytics();
        analytics.setClickedAt(LocalDateTime.now());
        analytics.setIpAddress(request.getRemoteAddr());
        analytics.setUserAgent(request.getHeader("User-Agent"));
        analytics.setUrl(url);
        urlAnalyticsRepository.save(analytics);
        
        return url.getLongUrl();
    }

    
}