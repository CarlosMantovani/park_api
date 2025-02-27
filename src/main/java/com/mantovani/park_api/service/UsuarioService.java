package com.mantovani.park_api.service;

import com.mantovani.park_api.entity.Usuario;
import com.mantovani.park_api.exception.EntityNotFoundException;
import com.mantovani.park_api.exception.PasswordInvalidExcepetion;
import com.mantovani.park_api.exception.UsernameUniqueViolationException;
import com.mantovani.park_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username '%s' ja cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly= true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Usuario id=%s não encontrado", id)));
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidExcepetion("Nova senha não confere com confirmação de senha");
        }

        Usuario user = buscarPorId(id);

        if (!user.getPassword().equals(senhaAtual)){
            throw new PasswordInvalidExcepetion("Sua senha não confere");
        }

        user.setPassword(novaSenha);
        return user;
    }

    @Transactional(readOnly= true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
}
