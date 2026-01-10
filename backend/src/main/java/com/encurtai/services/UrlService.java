package com.encurtai.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.encurtai.dto.UrlDTO;
import com.encurtai.models.User;
import com.encurtai.repository.UrlRepository;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;
    
    public List<UrlDTO> getUrls(User user){

        List<UrlDTO> urls = urlRepository.findByUser(user);

        return urls;        
    }
}
