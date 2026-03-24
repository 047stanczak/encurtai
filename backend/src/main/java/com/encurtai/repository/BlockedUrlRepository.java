package com.encurtai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.encurtai.models.BlockType;
import com.encurtai.models.BlockedUrl;

@Repository
public interface BlockedUrlRepository extends JpaRepository<BlockedUrl, Long> {
    boolean existsByValueAndBlockType(String value, BlockType blockType);
    boolean existsByValue(String value);
    BlockedUrl findByValue(String value);
}