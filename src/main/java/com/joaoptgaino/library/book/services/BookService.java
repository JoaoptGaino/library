package com.joaoptgaino.library.book.services;

import com.joaoptgaino.library.book.dto.BookFormDto;
import com.joaoptgaino.library.book.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BookService {
    Book create(BookFormDto body);

    Page<Book> findAll(Pageable pageable);

    Book findOne(UUID id);

    Book update(UUID id, BookFormDto body);

    void delete(UUID id);
}
