package com.encurtai.services;

import com.encurtai.validation.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.encurtai.dto.UrlGeneratorDTO;
import com.encurtai.models.Url;
import com.encurtai.models.User;
import com.encurtai.repository.UrlRepository;

@Service
public class  UrlGeneratorService {

    private final UrlRepository urlRepository;
    private final ShortCodeGeneratorService shortCodeGeneratorService;
    private final UrlVerifier urlVerifier;

    public UrlGeneratorService(UrlRepository urlRepository, ShortCodeGeneratorService shortCodeGeneratorService, UrlVerifier urlVerifier) {
        this.urlRepository = urlRepository;
        this.shortCodeGeneratorService = shortCodeGeneratorService;
        this.urlVerifier = urlVerifier;
    }

    public String generator(String url, User user){

        String validUrl = UrlValidator.validateAndNormalize(url);

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