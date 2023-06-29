package com.challenge.marvel.library.dto;

import lombok.Data;

@Data
public class CharacterResponseDto {
    private String code;
    private String status;
    private DataDto data;
}
