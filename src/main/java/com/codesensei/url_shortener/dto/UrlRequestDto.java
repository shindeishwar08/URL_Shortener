package com.codesensei.url_shortener.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UrlRequestDto {
    private String longUrl;
    private String alias;
    private LocalDateTime expiresAt;
}
