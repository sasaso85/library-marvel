package com.challenge.marvel.library.util;

import com.challenge.marvel.library.dto.CharacterRequestDto;

public class UriUtil {


    private static final String URI = "https://gateway.marvel.com:443/v1/public/characters%s";
    private static final String PATH_ID = "/%d%s";
    private static final String SECURITY = "?apikey=%s&hash=%s&ts=%s&limit=%s&offset=%s";

    public static String getCharactersUri(CharacterRequestDto request) {
        return String.format(URI, getSecurity(request));
    }

    public static String getCharactersUriById(CharacterRequestDto request) {
        return String.format(URI, String.format(PATH_ID, request.getCharacterId(), getSecurity(request)));
    }

    private static String getSecurity(CharacterRequestDto request) {
        return String.format(SECURITY,
                request.getApiKey(),
                request.getHash(),
                request.getTs(),
                request.getLimit(),
                request.getOffset());
    }
}
