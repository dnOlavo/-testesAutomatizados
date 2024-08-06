package com.iftm.client.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iftm.client.entities.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Método para buscar cliente por nome exato (case insensitive)
    @Query("SELECT c FROM Client c WHERE LOWER(c.name) = LOWER(:name)")
    Client findClientByNameIgnoreCase(@Param("name") String name);

    // Método para buscar clientes contendo parte do nome (case insensitive)
    @Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Client> findClientsByNameContainingIgnoreCase(@Param("name") String name);
}
