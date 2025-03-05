package com.mantovani.park_api.web.controller;

import com.mantovani.park_api.jwt.JwtToken;
import com.mantovani.park_api.jwt.JwtUserDetailsService;
import com.mantovani.park_api.web.dto.UsuarioLoginDto;
import com.mantovani.park_api.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AutenticacaoController {

    private final JwtUserDetailsService detailsService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request) {
        log.info("Início do processo de autenticação para login. Username: {}", dto.getUsername());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            log.info("Autenticando credenciais...");

            authenticationManager.authenticate(authenticationToken);

            log.info("Usuário autenticado.");
            JwtToken token = detailsService.getTokerAuthenticated(dto.getUsername());

            log.info("Autenticação concluída com sucesso.");
            return ResponseEntity.ok(token);

        } catch (AuthenticationException e) {
            log.error("Falha na autenticação para o username '{}'. Credenciais inválidas.", dto.getUsername(), e);
        }

        log.info("Retornando erro de credenciais inválidas.");
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }
}
