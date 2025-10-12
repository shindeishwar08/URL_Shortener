package com.codesensei.url_shortener.dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UrlRequestDto {

    @NotBlank(message = "URL cannot be empty or blank.")
    @URL(protocol = "https", message = "Invalid URL format.")
    private String longUrl;
    
    private String alias;
    
    @Future(message = "Expiry date must be in the future.")
    private LocalDateTime expiresAt;
}
