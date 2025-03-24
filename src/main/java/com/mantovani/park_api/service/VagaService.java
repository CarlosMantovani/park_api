package com.mantovani.park_api.service;

import com.mantovani.park_api.entity.Vaga;
import com.mantovani.park_api.exception.CodigoUniqueViolationException;
import com.mantovani.park_api.exception.EntityNotFoundException;
import com.mantovani.park_api.exception.VagaDisponivelException;
import com.mantovani.park_api.repository.VagasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mantovani.park_api.entity.Vaga.StatusVaga.LIVRE;

@RequiredArgsConstructor
@Service
public class VagaService {
     private final VagasRepository vagasRepository;

    @Transactional
    public Vaga salvar(Vaga vaga) {
        try {
            return vagasRepository.save(vaga);
        } catch (DataIntegrityViolationException ex) {
            throw new CodigoUniqueViolationException("Vaga", vaga.getCodigo());
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo(String codigo) {
        return vagasRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException("Vaga", codigo)
        );
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorVagaLivre() {
        return vagasRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new VagaDisponivelException()
        );
    }
}
