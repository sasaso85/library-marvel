package com.challenge.marvel.library.service;

import com.challenge.marvel.library.dto.CharacterRequestDto;
import com.challenge.marvel.library.dto.CharacterDto;

import java.util.List;
import java.util.Optional;

public interface CharacterConsumerService {
    List<CharacterDto> getCharacters(CharacterRequestDto request);
    Optional<CharacterDto> getCharacterById(CharacterRequestDto request);
}
