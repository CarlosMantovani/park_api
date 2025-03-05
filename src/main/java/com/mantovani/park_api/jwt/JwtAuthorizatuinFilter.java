package com.mantovani.park_api.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
public class JwtAuthorizatuinFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Request URI: {}", request.getRequestURI());

        String requestPath = request.getRequestURI();
        String requestMethod = request.getMethod();

        // Permitir rotas públicas (login e criar usuários)
        if (isPublicEndpoint(requestPath, requestMethod)) {
            log.info("Endpoint público acessado: {} {}", requestMethod, requestPath);
            filterChain.doFilter(request, response); // Ignora o filtro para endpoints públicos
            return;
        }

        // Validação do token
        final String authHeader = request.getHeader(JwtUltils.JWT_AUTHORIZATION);
        log.info("Authorization Header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith(JwtUltils.JWT_BEARER)) {
            log.warn("Token ausente ou com formato inválido no header Authorization.");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Responde com 403 se não tiver token
            return;
        }

        // Extrai o token removendo o prefixo "Bearer "
        String token = authHeader.substring(JwtUltils.JWT_BEARER.length()).trim();

        if (!JwtUltils.isTokenValid(token)) {
            log.warn("Token JWT inválido ou expirado.");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Responde com 403 para token inválido
            return;
        }

        // Valida e autentica o usuário
        String username = JwtUltils.getUsernameFromToken(token);
        toAuthentication(request, username);

        filterChain.doFilter(request, response); // Continue o processamento normal
    }

    private boolean isPublicEndpoint(String path, String method) {
        // Login é público
        if (path.equals("/api/v1/login")) return true;

        // POST em "/api/v1/usuarios" (criação de usuários) é público
        if (path.equals("/api/v1/usuarios") && method.equalsIgnoreCase("POST")) return true;

        return false; // Todas as outras rotas exigem autenticação
    }

    private void toAuthentication(HttpServletRequest request, String username) {
        // Carrega as informações do usuário com base no username do token
        UserDetails userDetails = detailsService.loadUserByUsername(username);

        // Cria o objeto de autenticação com as roles/authorities do usuário
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Configura o contexto de segurança
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Usuário autenticado com sucesso: {}", username);
    }
}
