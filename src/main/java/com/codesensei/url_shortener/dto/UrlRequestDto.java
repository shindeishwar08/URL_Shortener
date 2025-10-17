package com.codesensei.url_shortener.dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UrlRequestDto {

    @NotBlank(message = "URL cannot be empty or blank.")
    @URL(protocol = "https", message = "Invalid URL format.")
    private String longUrl;
    
    @Size(min=2,max=10, message ="Please Enter valid sized Alias. Ref: 2-10.")
    private String alias;
    
    @Future(message = "Expiry date must be in the future.")
    private LocalDateTime expiresAt;
}
