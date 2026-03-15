package com.encurtai.services;

import com.encurtai.validation.UrlValidator;
import org.springframework.stereotype.Service;

import com.encurtai.models.Url;
import com.encurtai.models.User;
import com.encurtai.repository.UrlRepository;

@Service
public class  UrlGeneratorService {

    private final UrlRepository urlRepository;
    private final ShortCodeGeneratorService shortCodeGeneratorService;
    private final UrlVerifier urlVerifier;
    private final UrlValidator urlValidator;

    public UrlGeneratorService(UrlRepository urlRepository, ShortCodeGeneratorService shortCodeGeneratorService, UrlVerifier urlVerifier, UrlValidator urlValidator) {
        this.urlRepository = urlRepository;
        this.shortCodeGeneratorService = shortCodeGeneratorService;
        this.urlVerifier = urlVerifier;
        this.urlValidator = urlValidator;
    }

    public String generator(String url, User user){

        String validUrl = urlValidator.urlValidator(url);

        urlVerifier.urlDuplicate(validUrl, user);

        String hash = shortCodeGeneratorService.generate();

        Url newUrl = new Url();
        newUrl.setHash(hash);
        newUrl.setUrl(validUrl);
        newUrl.setUser(user);
        urlRepository.save(newUrl);

        return hash;
    }
}