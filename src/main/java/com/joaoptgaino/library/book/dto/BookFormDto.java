package com.joaoptgaino.library.book.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BookFormDto {

    private String name;

    private String genre;

    private String section;

    private UUID authorId;
}
