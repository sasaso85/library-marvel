package com.challenge.marvel.library.service;

import com.challenge.marvel.library.dto.CharacterDto;
import com.challenge.marvel.library.dto.CharacterRequestDto;
import com.challenge.marvel.library.dto.CharacterResponseDto;
import com.challenge.marvel.library.util.UriUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CharacterConsumerServiceImpl implements CharacterConsumerService {

    private final RestTemplate restTemplate;

    public CharacterConsumerServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CharacterDto> getCharacters(CharacterRequestDto request) {
        return getInformation(UriUtil.getCharactersUri(request));
    }

    @Override
    public Optional<CharacterDto> getCharacterById(CharacterRequestDto request) {
        List<CharacterDto> response = getInformation(UriUtil.getCharactersUriById(request));
        return response.isEmpty() ? Optional.empty() : Optional.of(response.get(0));
    }

    private List<CharacterDto> getInformation(String URI) {
        CharacterResponseDto response = restTemplate.getForObject(URI, CharacterResponseDto.class);
        log.debug("API RESPONSE: " + response);
        if ((response != null ? response.getData() : null) != null) {
            return response.getData().getResults();
        }
        return Collections.emptyList();
    }

}
