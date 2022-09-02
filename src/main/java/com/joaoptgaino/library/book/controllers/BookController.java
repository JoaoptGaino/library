package com.joaoptgaino.library.book.controllers;

import com.joaoptgaino.library.book.dto.BookFormDto;
import com.joaoptgaino.library.book.models.Book;
import com.joaoptgaino.library.book.services.BookServiceImpl;
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
@RestController()
@CrossOrigin("*")
@RequestMapping("v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImpl bookService;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody @Valid BookFormDto body) {
        log.info("BookController.create - Start");
        Book book = bookService.create(body);

        log.debug("BookController.create - End - Output: {}", book);

        return ResponseEntity.status(CREATED).body(book);
    }

    @GetMapping
    public ResponseEntity<Page<Book>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("BookController.findAll - Start");

        Page<Book> books = bookService.findAll(pageable);

        log.debug("BookController.findAll - End - Output: {}", books);
        return ResponseEntity.status(OK).body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findOne(@PathVariable(value = "id") UUID id) {
        log.info("BookController.findOne - Start");

        Book book = bookService.findOne(id);

        log.debug("BookController.findOne - End - Output: {}", book);

        return ResponseEntity.status(OK).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid BookFormDto body) {
        log.info("BookController.update - Start");

        Book book = bookService.update(id, body);

        log.debug("BookController.update - End - Output: {} ", book);
        return ResponseEntity.status(OK).body(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        log.info("BookController.delete - Start");

        bookService.delete(id);

        log.debug("BookController.delete - End - Output: {} ", id);
        return ResponseEntity.status(OK).body("Book deleted successfully");
    }
}
