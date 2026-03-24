package com.encurtai.validation;

import org.springframework.stereotype.Component;

import com.encurtai.exception.InvalidUrlException;

@Component
public class UrlValidator {

    private final UrlBlockedValidator urlBlockedValidator;
    private final UrlNormalize urlNormalize;

    public UrlValidator(UrlBlockedValidator urlBlockedValidator, UrlNormalize urlNormalize) {
        this.urlBlockedValidator = urlBlockedValidator;
        this.urlNormalize = urlNormalize;
    }

    public String urlValidator(String url) {
        if (url == null || url.isBlank()) {
            throw new InvalidUrlException("URL vazia");
        }

        String normalizedUrl = urlNormalize.canonicalize(url);
        urlBlockedValidator.urlBlockedChecker(normalizedUrl);

        return normalizedUrl;
    }
}