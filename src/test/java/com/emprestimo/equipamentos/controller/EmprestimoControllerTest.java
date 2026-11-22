package com.emprestimo.equipamentos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@SpringBootTest
@AutoConfigureMockMvc
class EmprestimoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCadastrarEquipamentoComStatus201() throws Exception {

        String json = """
            {
                "patrimonio": "TESTE-001",
                "nome": "Notebook para teste",
                "tipo": "NOTEBOOK"
            }
            """;

        mockMvc.perform(post("/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Notebook para teste"))
                .andExpect(jsonPath("$.disponivel").value(true));
    }

    @Test
    void deveRetornar404QuandoEquipamentoNaoExistir() throws Exception {

        mockMvc.perform(get("/equipamentos/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveEmprestarEDevolverEquipamento() throws Exception {

        String equipamentoJson = """
            {
                "patrimonio": "TESTE-002",
                "nome": "Monitor para teste",
                "tipo": "MONITOR"
            }
            """;

        String respostaEquipamento = mockMvc.perform(post("/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(equipamentoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long equipamentoId = com.fasterxml.jackson.databind.json.JsonMapper
                .builder()
                .build()
                .readTree(respostaEquipamento)
                .get("id")
                .asLong();

        String colaboradorJson = """
            {
                "nome": "Colaborador Teste",
                "email": "teste.mockmvc@empresa.com",
                "cargo": "Analista"
            }
            """;

        String respostaColaborador = mockMvc.perform(post("/colaboradores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(colaboradorJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long colaboradorId = com.fasterxml.jackson.databind.json.JsonMapper
                .builder()
                .build()
                .readTree(respostaColaborador)
                .get("id")
                .asLong();


        String respostaEmprestimo = mockMvc.perform(post("/emprestimos")
                        .param("equipamentoId", equipamentoId.toString())
                        .param("colaboradorId", colaboradorId.toString()))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long emprestimoId = com.fasterxml.jackson.databind.json.JsonMapper
                .builder()
                .build()
                .readTree(respostaEmprestimo)
                .get("id")
                .asLong();


        mockMvc.perform(put("/emprestimos/{id}/devolucao", emprestimoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataDevolucao").exists());
    }

    @Test
    void naoDeveEmprestarEquipamentoJaEmprestado() throws Exception {

        String equipamentoJson = """
            {
                "patrimonio": "TESTE-003",
                "nome": "Notebook indisponível",
                "tipo": "NOTEBOOK"
            }
            """;

        String respostaEquipamento = mockMvc.perform(post("/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(equipamentoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long equipamentoId = com.fasterxml.jackson.databind.json.JsonMapper
                .builder()
                .build()
                .readTree(respostaEquipamento)
                .get("id")
                .asLong();

        String colaboradorJson = """
            {
                "nome": "Teste Conflito",
                "email": "conflito.mockmvc@empresa.com",
                "cargo": "Analista"
            }
            """;

        String respostaColaborador = mockMvc.perform(post("/colaboradores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(colaboradorJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long colaboradorId = com.fasterxml.jackson.databind.json.JsonMapper
                .builder()
                .build()
                .readTree(respostaColaborador)
                .get("id")
                .asLong();


        mockMvc.perform(post("/emprestimos")
                        .param("equipamentoId", equipamentoId.toString())
                        .param("colaboradorId", colaboradorId.toString()))
                .andExpect(status().isCreated());


        mockMvc.perform(post("/emprestimos")
                        .param("equipamentoId", equipamentoId.toString())
                        .param("colaboradorId", colaboradorId.toString()))
                .andExpect(status().isConflict());
    }

}