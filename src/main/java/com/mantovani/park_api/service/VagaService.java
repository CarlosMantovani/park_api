package com.mantovani.park_api.service;

import com.mantovani.park_api.entity.Vaga;
import com.mantovani.park_api.exception.CodigoUniqueViolationException;
import com.mantovani.park_api.exception.EntityNotFoundException;
import com.mantovani.park_api.repository.VagasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VagaService {
     private final VagasRepository vagasRepository;

     @Transactional
     public Vaga salvar(Vaga vaga){
         try {
             return vagasRepository.save(vaga);
         } catch (DataIntegrityViolationException ex){
             throw new CodigoUniqueViolationException(String.format("Vaga com codigo '%s' ja cadastrada", vaga.getCodigo()));
         }
     }
    @Transactional(readOnly = true)
     public Vaga buscarPorCodigo(String codigo){
         return vagasRepository.findByCodigo(codigo).orElseThrow(
                 ()-> new EntityNotFoundException(String.format("Vaga com codigo '%s' nao foi encotrada", codigo))
         );
     }
}
