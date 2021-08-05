package com.group3.chefkoch.repository;

import com.group3.chefkoch.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag,Long> {
}
