package com.joaoptgaino.library.author.services;

import com.joaoptgaino.library.author.models.Author;
import com.joaoptgaino.library.author.dto.AuthorFormDto;
import com.joaoptgaino.library.author.repositories.AuthorRepository;
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
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Author create(AuthorFormDto body) {

        Author author = new Author();
        BeanUtils.copyProperties(body, author);

        return authorRepository.save(author);
    }

    @Override
    public Page<Author> findAll(Pageable pageable) {
        Page<Author> authors = authorRepository.findAll(pageable);
        System.out.println(authors);

        return authors;
    }

    @Override
    public Author findOne(UUID id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }

        return authorOptional.get();
    }

    @Override
    @Transactional
    public Author update(UUID id, AuthorFormDto body) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }

        authorOptional.map(author -> {
            author.setId(id);
            author.setName(body.getName());
            return author;
        });

        return authorOptional.get();
    }

    @Override
    public Author delete(UUID id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }

        authorRepository.delete(authorOptional.get());

        return authorOptional.get();
    }
}
