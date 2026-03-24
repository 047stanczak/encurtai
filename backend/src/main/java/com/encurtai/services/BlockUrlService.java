package com.encurtai.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.encurtai.dto.BlockUrlDTO;
import com.encurtai.exception.UrlByIdNotFoundException;
import com.encurtai.exception.UrlDuplicatedException;
import com.encurtai.models.BlockType;
import com.encurtai.models.BlockedUrl;
import com.encurtai.repository.BlockedUrlRepository;
import com.encurtai.validation.ExtractHost;
import com.encurtai.validation.UrlNormalize;

import jakarta.transaction.Transactional;

@Service
public class BlockUrlService {

    private final BlockedUrlRepository blockedUrlRepository;
    private final UrlNormalize urlNormalize;
    private final ExtractHost extractHost;

    public BlockUrlService(
        BlockedUrlRepository blockedUrlRepository,
        UrlNormalize urlNormalize,
        ExtractHost extractHost
    ) {
        this.blockedUrlRepository = blockedUrlRepository;
        this.urlNormalize = urlNormalize;
        this.extractHost = extractHost;
    }

    @Transactional
    public void blockUrl(BlockUrlDTO blockUrl) {

        BlockType type = BlockType.valueOf(blockUrl.blockType());

        String value;

        if (type == BlockType.HOST) {
            value = extractHost.extract(blockUrl.url());
        } else {
            value = urlNormalize.canonicalize(blockUrl.url());
        }

        if (blockedUrlRepository.existsByValueAndBlockType(value, type)) {
            throw new UrlDuplicatedException("URL já bloqueada");
        }

        BlockedUrl newBlockedUrl = new BlockedUrl();
        newBlockedUrl.setValue(value);
        newBlockedUrl.setBlockType(type);

        blockedUrlRepository.save(newBlockedUrl);
    }

    @Transactional
    public List<BlockUrlDTO> getBlockedUrls() {
        return blockedUrlRepository.findAll()
            .stream()
            .map(b -> new BlockUrlDTO(b.getValue(), b.getBlockType().name()))
            .toList();
    }

    @Transactional
    public void deleteBlockedUrl(Long id) {
        BlockedUrl blockedUrl = blockedUrlRepository.findById(id)
            .orElseThrow(() ->
                new UrlByIdNotFoundException("Url não encontrada")
            );

        blockedUrlRepository.delete(blockedUrl);
    }
}