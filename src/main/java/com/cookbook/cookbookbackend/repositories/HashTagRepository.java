package com.cookbook.cookbookbackend.repositories;

import com.cookbook.cookbookbackend.models.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    Optional<HashTag> findHashTagByText(String text);

}
