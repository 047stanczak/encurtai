package com.encurtai.validation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.encurtai.exception.InvalidUrlException;

@Component
public class UrlNormalize {

    public String ensureScheme(String url) {
        String value = sanitize(url);

        if (value.startsWith("http://") || value.startsWith("https://")) {
            return value;
        }

        return "https://" + value;
    }

    public String canonicalize(String url) {
        String value = ensureScheme(url);

        try {
            URI uri = new URI(value);

            String host = uri.getHost();
            if (host == null || host.isBlank()) {
                throw new InvalidUrlException("URL inválida");
            }

            String scheme = uri.getScheme() == null
                    ? "https"
                    : uri.getScheme().toLowerCase(Locale.ROOT);

            host = normalizeHost(host);

            int port = uri.getPort();
            String path = normalizePath(uri.getRawPath());
            String query = uri.getRawQuery();

            URI normalized = new URI(
                    scheme,
                    null,
                    host,
                    port,
                    path,
                    query,
                    null
            );

            return normalized.toString();
        } catch (URISyntaxException e) {
            throw new InvalidUrlException("URL inválida");
        }
    }

    public String normalizeHost(String host) {
        String value = host.toLowerCase(Locale.ROOT);

        if (value.startsWith("www.")) {
            value = value.substring(4);
        }

        return value;
    }

    public String normalize(String url) {
        return canonicalize(url);
    }

    public String accessible(String url) {
        return ensureScheme(url);
    }

    @Deprecated
    public String accessable(String url) {
        return ensureScheme(url);
    }

    private String sanitize(String url) {
        if (url == null) {
            throw new IllegalArgumentException("URL vazia");
        }
        return url.trim();
    }

    private String normalizePath(String path) {
        if (path == null || path.isBlank()) {
            return "/";
        }

        String normalized = path.replaceAll("/{2,}", "/");
        return normalized.startsWith("/") ? normalized : "/" + normalized;
    }
}