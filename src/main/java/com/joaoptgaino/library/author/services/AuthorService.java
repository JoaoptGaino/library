package com.joaoptgaino.library.author.services;

import com.joaoptgaino.library.author.models.Author;
import com.joaoptgaino.library.author.dto.AuthorFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AuthorService {
    Author create(AuthorFormDto body);

    Page<Author> findAll(Pageable pageable);

    Author findOne(UUID id);

    Author update(UUID id, AuthorFormDto body);

    Author delete(UUID id);
}
