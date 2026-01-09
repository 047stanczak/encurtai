package com.encurtai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.encurtai.dto.UrlDTO;
import com.encurtai.models.Url;
import com.encurtai.models.User;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{
    Url findByUrl(String url);
    boolean existsByHash(String hash);
    List<UrlDTO> findByUser(User user);
    boolean existsByUserAndUrl(User user, String url);
}
