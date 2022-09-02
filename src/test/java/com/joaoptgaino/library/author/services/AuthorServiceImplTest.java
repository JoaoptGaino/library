package com.joaoptgaino.library.author.services;


import com.joaoptgaino.library.ApplicationConfigTest;
import com.joaoptgaino.library.author.dto.AuthorFormDto;
import com.joaoptgaino.library.author.models.Author;
import com.joaoptgaino.library.author.repositories.AuthorRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DisplayName("AuthorServiceImplTest")
@RunWith(MockitoJUnitRunner.Silent.class)
public class AuthorServiceImplTest extends ApplicationConfigTest {

    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Should create an author")
    public void shouldCreateAnAuthor() {
        UUID id = UUID.randomUUID();
        Author author = createMockAuthor(id);
        AuthorFormDto authorFormDto = new AuthorFormDto();

        BeanUtils.copyProperties(author, authorFormDto);

        when(authorRepository.save(eq(author))).thenReturn(author);

        authorService.create(authorFormDto);

        assertThat(author.getName()).isEqualTo(authorFormDto.getName());

    }

    @Test
    @DisplayName("Should return Page of authors")
    public void shouldFindAllAuthors() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Author> authorPage = createMockAuthorPage();

        when(authorRepository.findAll(eq(pageRequest))).thenReturn(authorPage);

        authorService.findAll(pageRequest);

        assertThat(authorPage.getTotalElements()).isEqualTo(authorPage.getContent().size());
        assertThat(authorPage.getContent().get(0).getName()).isEqualTo("Chuck Palahniuk");
    }

    @Test
    @DisplayName("Should return author by id")
    public void shouldFindOneAuthor() {
        UUID id = UUID.randomUUID();
        Optional<Author> authorOptional = Optional.of(createMockAuthor(id));

        when(authorRepository.findById(eq(id))).thenReturn(authorOptional);

        authorService.findOne(id);

        assertThat(authorOptional.get().getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Should throw not found")
    public void shouldThrowNotFound() {
        UUID id = UUID.randomUUID();

        when(authorRepository.findById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found."));

        try {
            authorService.findOne(id);
        } catch (Exception e) {
            assertEquals(ResponseStatusException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("Should remove an author")
    public void shouldRemoveAuthor() {
        UUID authorId = UUID.randomUUID();
        Optional<Author> authorOptional = Optional.of(createMockAuthor(authorId));

        when(authorRepository.findById(eq(authorId))).thenReturn(authorOptional);

        authorService.delete(authorId);
        assertThat(authorOptional.get().getId()).isEqualTo(authorId);
    }

    private Author createMockAuthor(UUID id) {
        Author author = new Author();
        author.setId(id);
        author.setName("Chuck Palahniuk");

        return author;
    }

    private Page<Author> createMockAuthorPage() {
        Author author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("Chuck Palahniuk");

        return new PageImpl<>(List.of(author));
    }
}
