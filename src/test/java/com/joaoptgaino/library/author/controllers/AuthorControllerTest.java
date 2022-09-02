package com.joaoptgaino.library.author.controllers;


import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaoptgaino.library.author.models.Author;
import com.joaoptgaino.library.author.services.AuthorServiceImpl;
import com.joaoptgaino.library.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AuthorServiceImpl authorService;

    private ObjectMapper objectMapper = new ObjectMapper().disable(MapperFeature.USE_ANNOTATIONS);

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void getAuthors() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Author author = getAuthor();

        PageImpl<Author> authorPage = new PageImpl<>(Arrays.asList(author));

        when(authorService.findAll(pageRequest)).thenReturn(authorPage);

        mockMvc.perform(get("/v1/authors")).andExpect(status().is2xxSuccessful());

    }

    private Page<Author> getAuthorPageDto() {
        return new PageImpl<Author>(TestUtils.newList());
    }

    private Author getAuthor() {
        Author author = new Author();

        author.setName("Chuck Palahniuk");

        return author;
    }
}
