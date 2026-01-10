package com.encurtai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.encurtai.repository.UrlRepository;

@Service
public class ShortCodeGeneratorService {

    @Autowired
    private UrlRepository urlRepository;

    private static final int SIZE = 8;

    public String generate(){
        String hash;

        do {
        hash = NanoIdUtils.randomNanoId(
                NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                NanoIdUtils.DEFAULT_ALPHABET,
                SIZE
        );
        } while (urlRepository.existsByHash(hash));

        return hash;
    }
}
