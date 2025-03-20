package com.mantovani.park_api.repository;

import com.mantovani.park_api.entity.ClienteVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteVagaRepository extends JpaRepository<ClienteVaga, Long> {

}
