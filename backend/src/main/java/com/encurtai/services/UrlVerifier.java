package com.encurtai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.encurtai.exception.UrlDuplicatedException;
import com.encurtai.models.User;
import com.encurtai.repository.UrlRepository;

@Service
public class UrlVerifier {

    @Autowired
    private UrlRepository urlRepository;

    public void urlDuplicate(String url, User user){

        boolean urlDuplicate = urlRepository.existsByUserAndUrl(user, url);

        if(urlDuplicate){
            throw new UrlDuplicatedException("Url já encurtada.");
        }

    }
}
