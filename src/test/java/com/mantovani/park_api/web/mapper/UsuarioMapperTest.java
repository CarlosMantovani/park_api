package com.mantovani.park_api.web.mapper;

import com.mantovani.park_api.entity.Usuario;
import com.mantovani.park_api.web.dto.UsuarioCreateDto;
import com.mantovani.park_api.web.dto.UsuarioResponseDto;
import com.mantovani.park_api.web.dto.mapper.UsuarioMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    @Test
    void deveConverterUsuarioCreateDtoParaUsuario() {

        UsuarioCreateDto dto = new UsuarioCreateDto("carlos@teste.com", "senha123");

        // Convertendo para entidade
        Usuario usuario = UsuarioMapper.toUsuario(dto);

        // Validando se os dados foram mapeados corretamente
        assertNotNull(usuario);
        assertEquals("carlos@teste.com", usuario.getUsername());
        assertEquals("senha123", usuario.getPassword());
    }

    @Test
    void deveConverterUsuarioParaUsuarioResponseDto() {
        // Criando um usuário com role simulada
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("carlos@teste.com");
        usuario.setRole(Usuario.Role.ROLE_ADMIN); // Supondo que sua enum tenha ROLE_ADMIN

        // Convertendo para DTO de resposta
        UsuarioResponseDto dto = UsuarioMapper.toDto(usuario);

        // Validando se os dados foram mapeados corretamente
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("carlos@teste.com", dto.getUsername());
        assertEquals("ADMIN", dto.getRole()); // Removendo "ROLE_"
    }
}
