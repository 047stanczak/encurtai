package com.encurtai.services;

import java.util.List;
import java.util.Optional;

import com.encurtai.exception.UrlAccessDeniedException;
import com.encurtai.exception.UrlByHashNotFoundException;
import com.encurtai.exception.UrlByIdNotFoundException;
import com.encurtai.models.Url;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.encurtai.dto.UrlDTO;
import com.encurtai.models.User;
import com.encurtai.repository.UrlRepository;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public List<UrlDTO> getUrls(User user){

        List<UrlDTO> urls = urlRepository.findByUser(user);

        return urls;        
    }

    public void deleteUrl(Long id, User user) {
        Url url = urlRepository.findById(id)
                .orElseThrow(() ->
                        new UrlByIdNotFoundException("Url não encontrada")
                );
        if (!url.getUser().getId().equals(user.getId())) {
            throw new UrlAccessDeniedException(
                    "Você não tem permissão para excluir esta URL"
            );
        }
        urlRepository.delete(url);
    }

    @Transactional
    public String getUrl(String hash){

        Url url = urlRepository.findByHash(hash)
                .orElseThrow(() -> new UrlByHashNotFoundException("Url não encontrada"));

        increaseView(url);

        return url.getUrl();
    }

    public void increaseView(Url url){
        url.setViews(url.getViews() + 1);
    }

}
