package com.lead.pizzaria.repositories;

import com.lead.pizzaria.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRespository extends JpaRepository<Client,Long> {

    @Query("SELECT c FROM Client c WHERE lower(c.name) = :name")
    Client findByName(String name);
}
