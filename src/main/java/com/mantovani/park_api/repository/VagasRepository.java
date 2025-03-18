package com.mantovani.park_api.repository;

import com.mantovani.park_api.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagasRepository extends JpaRepository<Vaga, Long> {
    Optional<Vaga> findByCodigo(String codigo);
}
