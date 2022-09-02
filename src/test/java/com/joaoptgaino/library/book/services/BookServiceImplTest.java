package com.joaoptgaino.library.book.services;

import com.joaoptgaino.library.ApplicationConfigTest;
import com.joaoptgaino.library.book.repositories.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@DisplayName("BookServiceImplTest")
@RunWith(MockitoJUnitRunner.Silent.class
)
public class BookServiceImplTest extends ApplicationConfigTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    @DisplayName("Should create a book")
    public void shouldCreateABook(){

    }
}
