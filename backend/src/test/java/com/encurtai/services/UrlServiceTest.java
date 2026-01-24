package com.encurtai.services;

import com.encurtai.models.Url;
import com.encurtai.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @Test
    public void getUrlIncreasingViewsCalledByFindByHash(){

        Url url = new Url();
        url.setHash("hash");
        url.setUrl("www.exemplo.com.br");
        url.setViews(0L);

        when(urlRepository.findByHash("hash"))
                .thenReturn(Optional.of(url));

        urlService.getUrl("hash");

        assertEquals(1L, url.getViews());
        verify(urlRepository).findByHash("hash");
    }

}