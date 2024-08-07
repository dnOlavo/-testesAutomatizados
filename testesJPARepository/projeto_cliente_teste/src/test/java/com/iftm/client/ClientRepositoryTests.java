package com.iftm.client;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.iftm.client.entities.Client;
import com.iftm.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ClientRepositoryTests {
    @Autowired
    private ClientRepository clientRepository;


    // Teste para buscar cliente por nome existente
@Test
public void shouldFindClientByName() {
    Client client = clientRepository.findClientByNameIgnoreCase("Conceição Evaristo");
    assertThat(client).isNotNull();
}

// Teste para buscar cliente por nome não existente
@Test
public void shouldNotFindClientByNonexistentName() {
    Client client = clientRepository.findClientByNameIgnoreCase("Nonexistent Name");
    assertThat(client).isNull();
}

// Teste para buscar clientes contendo parte do nome existente
@Test
public void shouldFindClientsByNameContaining() {
    List<Client> clients = clientRepository.findClientsByNameContainingIgnoreCase("Gil");
    assertThat(clients).isNotEmpty();
    assertThat(clients.size()).isEqualTo(1);
}

// Teste para buscar clientes por parte do nome não existente
@Test
public void shouldNotFindClientsByNameContainingNonexistent() {
    List<Client> clients = clientRepository.findClientsByNameContainingIgnoreCase("xyz");
    assertThat(clients).isEmpty();
}

// Teste para buscar clientes com nome vazio
@Test
public void shouldFindAllClientsWhenNameIsEmpty() {
    List<Client> clients = clientRepository.findClientsByNameContainingIgnoreCase("");
    assertThat(clients).hasSize(12); // Número de clientes no banco de dados
}

// Teste para buscar clientes com salários superiores a um valor
@Test
public void shouldFindClientsWithIncomeGreaterThan() {
    List<Client> clients = clientRepository.findClientsByIncomeGreaterThan(3000.0);
    assertThat(clients).isNotEmpty();
}

// Teste para buscar clientes com salários inferiores a um valor
@Test
public void shouldFindClientsWithIncomeLessThan() {
    List<Client> clients = clientRepository.findClientsByIncomeLessThan(2000.0);
    assertThat(clients).isNotEmpty();
}

// Teste para buscar clientes com salários em uma faixa de valores
@Test
public void shouldFindClientsWithIncomeBetween() {
    List<Client> clients = clientRepository.findClientsByIncomeBetween(2000.0, 5000.0);
    assertThat(clients).isNotEmpty();
}

// Teste para buscar clientes com data de nascimento entre duas datas
@Test
public void shouldFindClientsByBirthDateBetween() {
    Instant startDate = Instant.parse("1970-01-01T00:00:00Z");
    Instant endDate = Instant.now();
    List<Client> clients = clientRepository.findClientByBirthDateBetween(startDate, endDate);
    assertThat(clients).isNotEmpty();
}

// Teste para verificar a atualização dos dados do cliente
@Test
public void shouldUpdateClient() {
    Client client = clientRepository.findClientByNameIgnoreCase("Conceição Evaristo");
    client.setName("Conceição Almeida");
    client.setIncome(8000.0);
    client.setBirthDate(Instant.parse("1980-01-01T00:00:00Z"));
    clientRepository.save(client);

    Client updatedClient = clientRepository.findClientByNameIgnoreCase("Conceição Almeida");
    assertThat(updatedClient).isNotNull();
    assertThat(updatedClient.getIncome()).isEqualTo(8000.0);
    assertThat(updatedClient.getBirthDate()).isEqualTo(Instant.parse("1980-01-01T00:00:00Z"));
}

}
