package com.emprestimo.equipamentos.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoTest {
    @Test
    void deveCriarEmprestimoComPrazoDe14Dias() {
        LocalDate hoje = LocalDate.of(2026, 11, 14);

        Emprestimo emprestimo = new Emprestimo(1L, 1L, hoje);

        assertEquals(hoje, emprestimo.getDataEmprestimo());
        assertEquals(LocalDate.of(2026, 11, 28),
                emprestimo.getDataPrevistaDevolucao());
    }

    @Test
    void deveRegistrarDataDeDevolucao() {
        LocalDate hoje = LocalDate.of(2026, 11, 14);
        Emprestimo emprestimo = new Emprestimo(1L, 1L, hoje);
        LocalDate dataDevolucao = LocalDate.of(2026, 11, 20);

        emprestimo.devolver(dataDevolucao);

        assertEquals(dataDevolucao, emprestimo.getDataDevolucao());
    }

    @Test
    void naoDevePermitirDevolverDuasVezes() {
        Emprestimo emprestimo = new Emprestimo(
                1L, 1L, LocalDate.of(2026, 11, 14));

        emprestimo.devolver(LocalDate.of(2026, 11, 20));

        assertThrows(IllegalStateException.class, () ->
                emprestimo.devolver(LocalDate.of(2026, 11, 21)));
    }

    @Test
    void deveIdentificarEmprestimoAtrasado() {
        Emprestimo emprestimo = new Emprestimo(
                1L, 1L, LocalDate.of(2026, 10, 27));

        boolean atrasado = emprestimo.estaAtrasadoEm(
                LocalDate.of(2026, 11, 14));

        assertTrue(atrasado);
    }

    @Test
    void naoDeveIdentificarEmprestimoAindaNoPrazoComoAtrasado() {
        Emprestimo emprestimo = new Emprestimo(
                1L, 1L, LocalDate.of(2026, 11, 6));

        boolean atrasado = emprestimo.estaAtrasadoEm(
                LocalDate.of(2026, 11, 14));

        assertFalse(atrasado);
    }

    @Test
    void naoDeveIdentificarEmprestimoDevolvidoComoAtrasado() {
        Emprestimo emprestimo = new Emprestimo(
                1L, 1L, LocalDate.of(2026, 10, 27));

        emprestimo.devolver(LocalDate.of(2026, 11, 12));

        boolean atrasado = emprestimo.estaAtrasadoEm(
                LocalDate.of(2026, 11, 14));

        assertFalse(atrasado);
    }

    @Test
    void naoDeveConsiderarAtrasadoNoDiaDoVencimento() {
        Emprestimo emprestimo = new Emprestimo(
                1L, 1L, LocalDate.of(2026, 10, 31));

        boolean atrasado = emprestimo.estaAtrasadoEm(
                LocalDate.of(2026, 11, 14));

        assertFalse(atrasado);
    }
}
