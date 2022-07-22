package ru.narizhny.test_task_inside.domain.dto;

import lombok.Value;

@Value
public class TokenRequestDto {
    String name;
    String password;
}
