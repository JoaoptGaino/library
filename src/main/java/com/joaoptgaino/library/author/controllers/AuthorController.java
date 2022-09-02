package com.joaoptgaino.library.author.controllers;

import com.joaoptgaino.library.author.dto.AuthorFormDto;
import com.joaoptgaino.library.author.models.Author;
import com.joaoptgaino.library.author.services.AuthorServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorServiceImpl authorService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid AuthorFormDto body) {
        log.info("AuthorController.create - Start");
        Author author = authorService.create(body);

        log.debug("AuthorController.create - End - Output: {}", author);
        return ResponseEntity.status(CREATED).body(author);
    }

    @GetMapping
    public ResponseEntity<Page<Author>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("AuthorController.findAll - Start");
        Page<Author> authors = authorService.findAll(pageable);

        log.debug("AuthorController.findAll - End - Output: {}", authors);
        return ResponseEntity.status(OK).body(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findOne(@PathVariable(value = "id") UUID id) {
        log.info("AuthorController.findOne - Start");

        Author author = authorService.findOne(id);

        log.debug("AuthorController.findOne - End - Output: {}", author);
        return ResponseEntity.status(OK).body(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid AuthorFormDto body) {
        log.info("AuthorController.update - Start");

        Author author = authorService.update(id, body);

        log.debug("AuthorController.update - End - Output: {}", author);
        return ResponseEntity.status(OK).body(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        log.info("AuthorController.delete - Start");
        Author author = authorService.delete(id);

        log.debug("AuthorController.delete - End - Output: {}", author);
        return ResponseEntity.status(OK).body(author);
    }
}
