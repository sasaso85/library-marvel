package com.challenge.marvel.library.dto;

import lombok.Data;

@Data
public class CharacterDto {
    private Long id;
    private String name;
    private String description;

    private ThumbnailDto thumbnail;
}
