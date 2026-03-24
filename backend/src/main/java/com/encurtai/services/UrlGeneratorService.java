package com.encurtai.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encurtai.models.Url;
import com.encurtai.models.User;
import com.encurtai.repository.UrlRepository;
import com.encurtai.validation.UrlValidator;

@Service
public class UrlGeneratorService {

    private final UrlRepository urlRepository;
    private final ShortCodeGeneratorService shortCodeGeneratorService;
    private final UrlVerifier urlVerifier;
    private final UrlValidator urlValidator;

    public UrlGeneratorService(
            UrlRepository urlRepository,
            ShortCodeGeneratorService shortCodeGeneratorService,
            UrlVerifier urlVerifier,
            UrlValidator urlValidator
    ) {
        this.urlRepository = urlRepository;
        this.shortCodeGeneratorService = shortCodeGeneratorService;
        this.urlVerifier = urlVerifier;
        this.urlValidator = urlValidator;
    }

    @Transactional
    public String generator(String url, User user) {
        String normalizedUrl = urlValidator.urlValidator(url);
        urlVerifier.urlDuplicate(normalizedUrl, user);

        String hash = shortCodeGeneratorService.generate();

        Url newUrl = new Url();
        newUrl.setHash(hash);
        newUrl.setUrl(normalizedUrl);
        newUrl.setUser(user);

        urlRepository.save(newUrl);

        return hash;
    }
}