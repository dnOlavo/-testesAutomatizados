package iftm.edu.br.dnolavo.mockmvc.resources;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import iftm.edu.br.dnolavo.mockmvc.dto.ClientDTO;
import iftm.edu.br.dnolavo.mockmvc.entities.Client;
import iftm.edu.br.dnolavo.mockmvc.services.ClientService;

//necessário para utilizar o MockMVC
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import iftm.edu.br.dnolavo.mockmvc.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClientResourceTest {
    @Autowired
    private MockMvc mockMVC;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    @MockBean
    private ClientService service;

    /**
     * Caso de testes : Verificar se o endpoint get/clients/ retorna todos os
     * clientes existentes
     * Arrange:
     * - camada service simulada com mockito
     * - base de dado : 3 clientes
     * new Client(7l, "Jose Saramago", "10239254871", 5000.0,
     * Instant.parse("1996-12-23T07:00:00Z"), 0);
     * new Client(4l, "Carolina Maria de Jesus", "10419244771", 7500.0,
     * Instant.parse("1996-12-23T07:00:00Z"), 0);
     * new Client(8l, "Toni Morrison", "10219344681", 10000.0,
     * Instant.parse("1940-02-23T07:00:00Z"), 0);
     * - Uma PageRequest default
     * 
     * @throws Exception
     */
    @Test
    @DisplayName("Verificar se o endpoint get/clients/ retorna todos os clientes existentes")
    public void testarEndPointListarTodosClientesRetornaCorreto() throws Exception {
        // arrange
        int quantidadeClientes = 3;
        // configurando o Mock ClientService
        List<ClientDTO> listaClientes;
        listaClientes = new ArrayList<ClientDTO>();
        listaClientes.add(new ClientDTO(
                new Client(7L, "Jose Saramago", "10239254871", 5000.0, Instant.parse("1996-12-23T07:00:00Z"), 0)));
        listaClientes.add(new ClientDTO(new Client(4L, "Carolina Maria de Jesus", "10419244771", 7500.0,
                Instant.parse("1996-12-23T07:00:00Z"), 0)));
        listaClientes.add(new ClientDTO(
                new Client(8L, "Toni Morrison", "10219344681", 10000.0, Instant.parse("1940-02-23T07:00:00Z"), 0)));

        Page<ClientDTO> page = new PageImpl<>(listaClientes);

        Mockito.when(service.findAllPaged(Mockito.any())).thenReturn(page);
        // fim configuração mockito

        // act

        ResultActions resultados = mockMVC.perform(get("/clients/").accept(MediaType.APPLICATION_JSON));

        // assign
        resultados
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 7L).exists())
                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 4L).exists())
                .andExpect(jsonPath("$.content[?(@.id == '%s')]", 8L).exists())
                .andExpect(jsonPath("$.content[?(@.name == '%s')]", "Toni Morrison").exists())
                .andExpect(jsonPath("$.totalElements").exists())
                .andExpect(jsonPath("$.totalElements").value(quantidadeClientes));

    }

    @BeforeEach
    void setUp() throws Exception {
        // Inserir os dados iniciais
        clientRepository.deleteAll();
        clientRepository.saveAll(List.of(
                new Client(null, "Conceição Evaristo", "10619244881", 1500.0, Instant.parse("2020-07-13T20:50:00Z"), 2),
                new Client(null, "Lázaro Ramos", "10619244881", 2500.0, Instant.parse("1996-12-23T07:00:00Z"), 2)
        // outros clientes conforme a base fornecida
        ));
    }

    @Test
    public void findAll_ShouldReturnAllClients() throws Exception {
        ResultActions result = mockMVC.perform(get("/clients")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").isArray());
        result.andExpect(jsonPath("$.content", hasSize(12))); // Esperando 12 clientes
        result.andExpect(jsonPath("$.content[0].name").value("Conceição Evaristo"));
    }

    @Test
    public void findById_ShouldReturnClient_WhenIdExists() throws Exception {
        long existingId = 1L;
        ResultActions result = mockMVC.perform(get("/clients/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.name").value("Conceição Evaristo"));
    }

    @Test
    public void findById_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        long nonExistingId = 100L;
        ResultActions result = mockMVC.perform(get("/clients/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.error").value("Resource not found"));
    }

    @Test
    public void findByIncome_ShouldReturnClients_WhenIncomeMatches() throws Exception {
        double income = 1500.0;
        ResultActions result = mockMVC.perform(get("/clients/income/")
                .param("income", String.valueOf(income))
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").isArray());
        result.andExpect(jsonPath("$.content", hasSize(3))); // Esperando 3 clientes com income 1500
        result.andExpect(jsonPath("$.content[0].name").value("Conceição Evaristo"));
    }

    @Test
    public void findByIncomeGreaterThan_ShouldReturnClients_WhenIncomeIsGreater() throws Exception {
        double income = 4500.0;
        ResultActions result = mockMVC.perform(get("/clients/income-greater-than/")
                .param("income", String.valueOf(income))
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").isArray());
        result.andExpect(jsonPath("$.content", hasSize(3))); // Esperando 3 clientes com income > 4500
        result.andExpect(jsonPath("$.content[0].name").value("Toni Morrison"));
    }

    @Test
    public void findByCPFLike_ShouldReturnClients_WhenCPFMatches() throws Exception {
        String cpf = "106";
        ResultActions result = mockMVC.perform(get("/clients/cpf-like/")
                .param("cpf", cpf)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").isArray());
        result.andExpect(jsonPath("$.content", hasSize(4))); // Esperando 4 clientes cujo CPF contém "106"
        result.andExpect(jsonPath("$.content[0].name").value("Conceição Evaristo"));
    }
}
