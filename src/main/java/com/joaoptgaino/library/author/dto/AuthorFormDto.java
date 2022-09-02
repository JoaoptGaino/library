package com.joaoptgaino.library.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class AuthorFormDto {
    @NotBlank
    private String name;
}
