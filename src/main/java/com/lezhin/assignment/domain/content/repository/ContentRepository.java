package com.lezhin.assignment.domain.content.repository;

import com.lezhin.assignment.domain.content.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findAllById(Iterable<Long> ids);
}
