package com.joaoptgaino.library.book.services;

import com.joaoptgaino.library.author.models.Author;
import com.joaoptgaino.library.author.repositories.AuthorRepository;
import com.joaoptgaino.library.book.dto.BookFormDto;
import com.joaoptgaino.library.book.models.Book;
import com.joaoptgaino.library.book.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book create(BookFormDto body) {
        Author author = authorRepository.findById(body.getAuthorId()).orElseThrow();

        Book book = new Book();
        book.setAuthor(author);
        BeanUtils.copyProperties(body, book);

        return bookRepository.save(book);
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book findOne(UUID id) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found.");
        }
        return bookOptional.get();
    }

    @Override
    @Transactional
    public Book update(UUID id, BookFormDto body) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Optional<Author> authorOptional = authorRepository.findById(body.getAuthorId());

        if (bookOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found.");
        }

        bookOptional.map(book -> {
            book.setId(id);
            book.setName(body.getName());
            book.setGenre(body.getGenre());
            book.setSection(body.getSection());
            book.setAuthor(authorOptional.get());
            return book;
        });
        return bookOptional.get();
    }

    @Override
    public void delete(UUID id) {
        bookRepository.deleteById(id);
        System.out.printf("Deleted {} successfully", id);
    }
}
