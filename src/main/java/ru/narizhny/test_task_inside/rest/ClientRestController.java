package ru.narizhny.test_task_inside.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.narizhny.test_task_inside.domain.dto.MessageRequestDto;
import ru.narizhny.test_task_inside.domain.dto.MessageResponseDto;
import ru.narizhny.test_task_inside.domain.dto.TokenRequestDto;
import ru.narizhny.test_task_inside.domain.dto.TokenResponseDto;
import ru.narizhny.test_task_inside.services.ClientService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientService clientService;

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponseDto getToken(@RequestBody TokenRequestDto tokenDto) {
        return clientService.getToken(tokenDto);
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<MessageResponseDto>> message(@RequestBody MessageRequestDto messageDto,
                                                            HttpServletRequest request) {
        return clientService.message(messageDto, request);
    }
}
