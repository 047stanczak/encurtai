package com.encurtai.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.encurtai.dto.BlockUrlDTO;
import com.encurtai.exception.UrlByIdNotFoundException;
import com.encurtai.exception.UrlDuplicatedException;
import com.encurtai.models.BlockedUrl;
import com.encurtai.repository.BlockedUrlRepository;

import jakarta.transaction.Transactional;

@Service
public class BlockUrlService {

    private final BlockedUrlRepository blockedUrlRepository;

    public BlockUrlService(BlockedUrlRepository blockedUrlRepository) {
        this.blockedUrlRepository = blockedUrlRepository;
    }

    @Transactional
    public void blockUrl(BlockUrlDTO blockUrl) {
        if(blockedUrlRepository.existsByUrl(blockUrl.url())) {
            throw new UrlDuplicatedException("URL já bloqueada");
        }

        BlockedUrl newBlockedUrls = new BlockedUrl();
        newBlockedUrls.setUrl(blockUrl.url());
        blockedUrlRepository.save(newBlockedUrls);
    }

    @Transactional
    public List<BlockUrlDTO> getBlockedUrls(){
        return blockedUrlRepository.findAll()
        .stream()
        .map(b -> new BlockUrlDTO(b.getUrl()))
        .toList();
    }

    @Transactional
    public void deleteBlockedUrl(Long id){
        BlockedUrl blockedUrl = blockedUrlRepository.findById(id)
            .orElseThrow(() ->
                        new UrlByIdNotFoundException("Url não encontrada")
                );     
            blockedUrlRepository.delete(blockedUrl);
    }
    
}
