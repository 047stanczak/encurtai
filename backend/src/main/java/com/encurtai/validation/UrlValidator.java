package com.encurtai.validation;

import com.encurtai.exception.InvalidUrlException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlValidator {

    public static String validateAndNormalize(String url){
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL vazia");
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

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
