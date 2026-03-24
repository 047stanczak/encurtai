package com.encurtai.validation;

import org.springframework.stereotype.Component;

import com.encurtai.exception.InvalidUrlException;
import com.encurtai.models.BlockType;
import com.encurtai.repository.BlockedUrlRepository;

@Component
public class UrlBlockedValidator {

    private final BlockedUrlRepository blockedUrlRepository;
    private final UrlNormalize urlNormalize;
    private final ExtractHost extractHost;

    public UrlBlockedValidator(
            BlockedUrlRepository blockedUrlRepository,
            UrlNormalize urlNormalize,
            ExtractHost extractHost
    ) {
        this.blockedUrlRepository = blockedUrlRepository;
        this.urlNormalize = urlNormalize;
        this.extractHost = extractHost;
    }

    public void urlBlockedChecker(String url) {
        String normalizedUrl = urlNormalize.canonicalize(url);
        String host = extractHost.extract(normalizedUrl);

        boolean blockedByHost = blockedUrlRepository.existsByValueAndBlockType(host, BlockType.HOST);
        boolean blockedByExactUrl = blockedUrlRepository.existsByValueAndBlockType(normalizedUrl, BlockType.EXACT_URL);

        if (blockedByHost || blockedByExactUrl) {
            throw new InvalidUrlException("URL bloqueada: " + url);
        }
    }
}