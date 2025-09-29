package br.com.postech.soat.commons.infrastructure.security.http;

import br.com.postech.soat.commons.infrastructure.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return authenticationService.authenticateByCpf(request.cpf())
            .map(token -> ResponseEntity.ok(new AuthenticationResponse(token)))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CPF não cadastrado"));
    }
}
