package com.encurtai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.encurtai.models.BlockedUrl;

@Repository
public interface BlockedUrlRepository extends JpaRepository<BlockedUrl, Long> {
    boolean existsByUrl(String url);
    BlockedUrl findByUrl(String url);
}
