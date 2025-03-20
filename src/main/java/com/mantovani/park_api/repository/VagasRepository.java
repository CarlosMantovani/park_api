package com.mantovani.park_api.repository;

import com.mantovani.park_api.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VagasRepository extends JpaRepository<Vaga, Long> {
    Optional<Vaga> findByCodigo(String codigo);

    Optional<Vaga> findFirstByStatus(Vaga.StatusVaga statusVaga);
}
