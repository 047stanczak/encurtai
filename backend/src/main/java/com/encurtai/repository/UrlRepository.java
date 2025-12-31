package com.encurtai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.encurtai.models.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{
    Url findByUrl(String url);
    boolean existsByHash(String hash);
}
