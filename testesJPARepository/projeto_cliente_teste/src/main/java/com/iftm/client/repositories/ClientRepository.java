package com.iftm.client.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iftm.client.entities.Client;

import java.util.List;
import java.time.Instant;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Método para buscar cliente por nome exato (case insensitive)
    @Query("SELECT c FROM Client c WHERE LOWER(c.name) = LOWER(:name)")
    Client findClientByNameIgnoreCase(@Param("name") String name);

    // Método para buscar clientes contendo parte do nome (case insensitive)
    @Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Client> findClientsByNameContainingIgnoreCase(@Param("name") String name);

    // Método para buscar clientes com salários superiores a um valor
    @Query("SELECT c FROM Client c WHERE c.income > :income")
    List<Client> findClientsByIncomeGreaterThan(@Param("income") Double income);

    // Método para buscar clientes com salários inferiores a um valor
    @Query("SELECT c FROM Client c WHERE c.income < :income")
    List<Client> findClientsByIncomeLessThan(@Param("income") Double salary);

    // Método para buscar clientes com salários dentro de uma faixa de valores
    @Query("SELECT c FROM Client c WHERE c.income BETWEEN :minIncome AND :maxIncome")
    List<Client> findClientsByIncomeBetween(@Param("minIncome") Double minIncome, @Param("maxIncome") Double maxIncome);

    // Método para buscar clientes por faixa de data de nascimento
    List<Client> findClientByBirthDateBetween(Instant startDate, Instant endDate);
}
