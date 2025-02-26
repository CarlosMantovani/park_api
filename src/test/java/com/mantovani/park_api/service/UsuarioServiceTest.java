package com.mantovani.park_api.service;

import com.mantovani.park_api.entity.Usuario;
import com.mantovani.park_api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveSalvarUsuarioComSucesso() {
        Usuario usuario = new Usuario();
        usuario.setUsername("carlos@test.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        assertNotNull(usuarioSalvo);
        assertEquals("carlos@test.com", usuarioSalvo.getUsername());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("carlos@test.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario usuarioEncontrado = usuarioService.buscarPorId(1L);

        assertNotNull(usuarioEncontrado);
        assertEquals(1L, usuarioEncontrado.getId());
        assertEquals("carlos@test.com", usuarioEncontrado.getUsername());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> usuarioService.buscarPorId(1L));
        assertEquals("Usuario não encontrado ", thrown.getMessage());
    }
}
