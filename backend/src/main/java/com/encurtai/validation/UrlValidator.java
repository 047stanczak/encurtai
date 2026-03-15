package com.encurtai.validation;

import com.encurtai.exception.InvalidUrlException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlValidator {

    private final UrlBlockedValidator urlBlockedValidator;

    public UrlValidator(UrlBlockedValidator urlBlockedValidator) {
        this.urlBlockedValidator = urlBlockedValidator;
    }



    public String urlValidator(String url){
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL vazia");
        }

        url = UrlNormalize.normalize(url);
        
        urlBlockedValidator.urlBlockedChecker(url);

        try {
            URI uri = new URI(url);

            if (uri.getHost() == null) {
                throw new InvalidUrlException("URL inválida");
            }
            return url;

        } catch (URISyntaxException e) {
            throw new InvalidUrlException("URL inválida");
        }
    }
}
