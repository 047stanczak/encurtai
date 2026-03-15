package com.encurtai.validation;

import org.springframework.stereotype.Component;

import com.encurtai.exception.InvalidUrlException;
import com.encurtai.repository.BlockedUrlRepository;

@Component
public class UrlBlockedValidator {

    private final BlockedUrlRepository blockedUrlRepository;

    public UrlBlockedValidator(BlockedUrlRepository blockedUrlRepository) {
        this.blockedUrlRepository = blockedUrlRepository;
    }

    public void urlBlockedChecker(String url){
            if(blockedUrlRepository.existsByUrl(url)){
                throw new InvalidUrlException("URL bloqueada");
            }
    }

}
