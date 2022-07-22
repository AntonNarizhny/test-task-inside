package ru.narizhny.test_task_inside.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.narizhny.test_task_inside.domain.dto.MessageRequestDto;
import ru.narizhny.test_task_inside.domain.dto.MessageResponseDto;
import ru.narizhny.test_task_inside.domain.dto.TokenRequestDto;
import ru.narizhny.test_task_inside.domain.dto.TokenResponseDto;
import ru.narizhny.test_task_inside.domain.entity.Client;
import ru.narizhny.test_task_inside.domain.entity.Message;
import ru.narizhny.test_task_inside.domain.projections.MessageFromDb;
import ru.narizhny.test_task_inside.jwt.JwtTokenProvider;
import ru.narizhny.test_task_inside.repositories.ClientRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponseDto getToken(TokenRequestDto tokenDto) {
        Optional<Client> client = clientRepository.findByName(tokenDto.getName());

        if (client.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if(!client.get().getPassword().equals(tokenDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        String token = jwtTokenProvider.createToken(client.get());
        client.get().setToken(token);
        clientRepository.saveAndFlush(client.get());

        return new TokenResponseDto(token);
    }

    public ResponseEntity<List<MessageResponseDto>> message(MessageRequestDto messageDto, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            return handleMessage(messageDto);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<List<MessageResponseDto>> handleMessage(MessageRequestDto messageDto) {
        String message = messageDto.getMessage();
        if (message == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Client> client = clientRepository.findByName(messageDto.getName());
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (message.startsWith("history ")) {
            try {
                Long messagesCount = Long.parseLong(message.substring(8));
                List<MessageFromDb> messages = clientRepository.findAllByHistory(messageDto.getName(), messagesCount);

                List<MessageResponseDto> response = messages.stream()
                        .map(msg -> new MessageResponseDto(msg.getMessage()))
                        .collect(Collectors.toList());

                return response.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT): ResponseEntity.ok(response);
            } catch (NumberFormatException exception) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            Message newMessage = new Message();
            newMessage.setMessage(message);

            client.get().getMessages().add(newMessage);
            clientRepository.saveAndFlush(client.get());

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
