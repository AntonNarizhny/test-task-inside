package ru.narizhny.test_task_inside.domain.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class TokenRequestDto {
    @NotBlank
    @NotNull
    String name;
    @NotBlank
    @NotNull
    String password;
}
