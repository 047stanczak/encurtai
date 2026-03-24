package com.encurtai.validation;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Component;

import com.encurtai.exception.InvalidUrlException;

@Component
public class ExtractHost {

    private final UrlNormalize urlNormalize;

    public ExtractHost(UrlNormalize urlNormalize) {
        this.urlNormalize = urlNormalize;
    }

    public String extract(String url) {
        String value = urlNormalize.ensureScheme(url);

        try {
            URI uri = new URI(value);
            String host = uri.getHost();

            if (host == null || host.isBlank()) {
                throw new InvalidUrlException("URL inválida");
            }

            return urlNormalize.normalizeHost(host);
        } catch (URISyntaxException e) {
            throw new InvalidUrlException("URL inválida");
        }
    }
}