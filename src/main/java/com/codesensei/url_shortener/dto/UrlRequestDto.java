package com.codesensei.url_shortener.dto;

import lombok.Data;

@Data
public class UrlRequestDto {
    private String longUrl;
    private String alias;
}
