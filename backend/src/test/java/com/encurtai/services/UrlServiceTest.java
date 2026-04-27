package com.encurtai.services;

import com.encurtai.dto.UrlDTO;
import com.encurtai.exception.UrlAccessDeniedException;
import com.encurtai.exception.UrlByHashNotFoundException;
import com.encurtai.exception.UrlByIdNotFoundException;
import com.encurtai.models.Url;
import com.encurtai.models.User;
import com.encurtai.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @Test
    void getUrlIncreasesViewsAndReturnsOriginalUrl() {
        Url url = new Url();
        url.setHash("abc");
        url.setUrl("https://example.com");
        url.setViews(0L);

        when(urlRepository.findByHash("abc")).thenReturn(Optional.of(url));

        assertEquals("https://example.com", urlService.getUrl("abc"));
        assertEquals(1L, url.getViews());
        verify(urlRepository).findByHash("abc");
    }

    @Test
    void getUrlThrowsWhenHashNotFound() {
        when(urlRepository.findByHash("missing")).thenReturn(Optional.empty());

        assertThrows(UrlByHashNotFoundException.class, () -> urlService.getUrl("missing"));
        verify(urlRepository).findByHash("missing");
    }

    @Test
    void getUrlsReturnsListFromRepository() {
        User user = new User();
        user.setId(1L);
        List<UrlDTO> expected = List.of(
                new UrlDTO(1L, "h1", "https://a.com", 3L),
                new UrlDTO(2L, "h2", "https://b.com", 0L)
        );
        when(urlRepository.findByUser(user)).thenReturn(expected);

        assertEquals(expected, urlService.getUrls(user));
        verify(urlRepository).findByUser(user);
    }

    @Test
    void deleteUrlDeletesWhenUserOwnsUrl() {
        User owner = new User();
        owner.setId(10L);
        Url url = new Url();
        url.setId(100L);
        url.setUser(owner);

        when(urlRepository.findById(100L)).thenReturn(Optional.of(url));

        urlService.deleteUrl(100L, owner);

        verify(urlRepository).delete(url);
    }

    @Test
    void deleteUrlThrowsWhenIdNotFound() {
        User user = new User();
        user.setId(1L);
        when(urlRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(UrlByIdNotFoundException.class, () -> urlService.deleteUrl(999L, user));
        verify(urlRepository, never()).delete(any());
    }

    @Test
    void deleteUrlThrowsWhenUserDoesNotOwnUrl() {
        User owner = new User();
        owner.setId(1L);
        User other = new User();
        other.setId(2L);
        Url url = new Url();
        url.setId(50L);
        url.setUser(owner);

        when(urlRepository.findById(50L)).thenReturn(Optional.of(url));

        assertThrows(UrlAccessDeniedException.class, () -> urlService.deleteUrl(50L, other));
        verify(urlRepository, never()).delete(any());
    }

    @Test
    void increaseViewIncrementsCounter() {
        Url url = new Url();
        url.setViews(4L);

        urlService.increaseView(url);

        assertEquals(5L, url.getViews());
    }
}
