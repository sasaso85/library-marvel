package com.challenge.marvel.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterRequestDto {
    private String apiKey;
    private String ts;
    private String hash;

    private String limit;
    private String offset;

    private Long characterId;
}
