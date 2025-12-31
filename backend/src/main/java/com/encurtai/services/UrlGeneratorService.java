package com.encurtai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.encurtai.dto.UrlGeneratorDTO;
import com.encurtai.models.Url;
import com.encurtai.models.User;
import com.encurtai.repository.UrlRepository;

@Service
public class UrlGeneratorService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ShortCodeGeneratorService shortCodeGeneratorService;

    public String generator(UrlGeneratorDTO url, User user){
        String hash = shortCodeGeneratorService.generate();

        Url newUrl = new Url();
        newUrl.setHash(hash);
        newUrl.setUrl(url.getUrl());
        newUrl.setUser(user);
        urlRepository.save(newUrl);

        return hash;
    }
}