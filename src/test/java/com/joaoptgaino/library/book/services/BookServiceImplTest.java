package com.joaoptgaino.library.book.services;

import com.joaoptgaino.library.ApplicationConfigTest;
import com.joaoptgaino.library.author.models.Author;
import com.joaoptgaino.library.author.repositories.AuthorRepository;
import com.joaoptgaino.library.book.dto.BookFormDto;
import com.joaoptgaino.library.book.models.Book;
import com.joaoptgaino.library.book.repositories.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DisplayName("BookServiceImplTest")
@RunWith(MockitoJUnitRunner.Silent.class
)
public class BookServiceImplTest extends ApplicationConfigTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Should create a book")
    public void shouldCreateABook() {
        UUID bookId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Book book = createMockBook(bookId, authorId);
        BookFormDto bookFormDto = new BookFormDto();

        BeanUtils.copyProperties(book, bookFormDto);


        when(bookRepository.save(eq(book))).thenReturn(book);
        when(authorRepository.findById(eq(authorId))).
                thenReturn(Optional.of(createMockAuthor(authorId)));

        bookService.create(bookFormDto);

        assertThat(book.getName()).isEqualTo(bookFormDto.getName());

    }

    private Book createMockBook(UUID id, UUID authorId) {
        Book book = new Book();

        book.setId(id);
        book.setName("Fight club");
        book.setSection("B-123");
        book.setGenre("The best");
        book.setAuthor(createMockAuthor(authorId));

        return book;
    }

    private Author createMockAuthor(UUID id) {
        Author author = new Author();

        author.setId(id);
        author.setName("Chuck Palahniuk");

        return author;
    }
}
