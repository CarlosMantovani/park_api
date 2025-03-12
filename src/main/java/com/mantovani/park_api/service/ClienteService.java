package com.mantovani.park_api.service;

import com.mantovani.park_api.entity.Cliente;
import com.mantovani.park_api.exception.CpfUniqueViolationExpetion;
import com.mantovani.park_api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente){
        try {
            return clienteRepository.save(cliente);
        }catch (DataIntegrityViolationException ex){
            throw new CpfUniqueViolationExpetion(
                    String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", cliente.getCpf()));
        }
    }
}
