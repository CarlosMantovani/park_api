package com.mantovani.park_api.service;

import com.mantovani.park_api.entity.Usuario;
import com.mantovani.park_api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void thenSalveUserSucessed() {
        Usuario usuario = new Usuario();
        usuario.setUsername("carlos@test.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        assertNotNull(usuarioSalvo);
        assertEquals("carlos@test.com", usuarioSalvo.getUsername());
        verify(usuarioRepository, times(1)).save(usuario);
    }
}
