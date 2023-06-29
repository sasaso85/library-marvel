package com.challenge.marvel.library.service;

import com.challenge.marvel.library.dto.CharacterDto;
import com.challenge.marvel.library.dto.CharacterRequestDto;
import com.challenge.marvel.library.dto.CharacterResponseDto;
import com.challenge.marvel.library.util.UriUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

@Slf4j
public class CharacterConsumerServiceTest {
    private RestTemplate restTemplate;

    private CharacterConsumerService consumerService;
    private CharacterRequestDto request;

    public static final String RESPONSE = "{\"code\":200,\"status\":\"Ok\"," +
            "\"data\":{\"results\":[{" +
            "\"id\":1011334,\"name\":\"3-D Man\"," +
            "\"description\":\"\",\"thumbnail\":{" +
            "\"path\":\"http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784\"," +
            "\"extension\":\"jpg\"" +
            "}}]}}";
    private CharacterResponseDto response;

    @BeforeEach
    public void setup() {
        restTemplate = Mockito.mock(RestTemplate.class);
        try {
            response = new ObjectMapper().readValue(RESPONSE, CharacterResponseDto.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        request = CharacterRequestDto.builder()
                .apiKey("abccc4e39d31cd232eb102f2fa3d1234")
                .ts("1234")
                .hash("53b8a947303626b9db819b850cf7c294")
                .limit("1")
                .offset("0")
                .build();
        consumerService = new CharacterConsumerServiceImpl(restTemplate);
    }

    @Test
    @DisplayName("JUnit test for getCharacters method return Character List")
    public void givenRequest_whenGetCharacters_thenReturnCharacterList() {
        // given
        given(restTemplate.getForObject(UriUtil.getCharactersUri(request), CharacterResponseDto.class))
                .willReturn(response);
        // when
        List<CharacterDto> characterList = consumerService.getCharacters(request);
        // then
        assertThat(characterList).isNotNull();
        assertThat(characterList.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("JUnit test for getCharacterById method return Character")
    public void givenRequest_whenGetCharacterById_thenReturnCharacterOptional() {
        // given
        request.setCharacterId(1011334L);
        given(restTemplate.getForObject(UriUtil.getCharactersUriById(request), CharacterResponseDto.class))
                .willReturn(response);
        // when
        Optional<CharacterDto> characterOptional = consumerService.getCharacterById(request);
        // then
        assertThat(characterOptional).isNotNull();
        assertThat(characterOptional.isPresent()).isTrue();
    }

    @Test
    @DisplayName("JUnit test for getCharacterById method throw exception")
    public void givenRequest_whenGetCharacterById_thenThrowException() {
        // given
        request.setCharacterId(101133L);
        willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not found"))
                .given(restTemplate)
                .getForObject(any(String.class), eq(CharacterResponseDto.class));
        // when
        RestClientResponseException thrown =
                Assertions.assertThrows(RestClientResponseException.class,
                        () -> consumerService.getCharacterById(request));
        // then
        assertThat(thrown.getRawStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("JUnit test for getCharacters method return empty list")
    public void givenRequest_whenGetCharacters_thenReturnEmptyList() {
        // given
        response.setData(null);
        given(restTemplate.getForObject(UriUtil.getCharactersUri(request), CharacterResponseDto.class))
                .willReturn(response);
        // when
        List<CharacterDto> characterList = consumerService.getCharacters(request);
        // then
        assertThat(characterList).isNotNull();
        assertThat(characterList.size()).isEqualTo(0);
    }
}
