package com.emprestimo.equipamentos.dto;

import java.time.LocalDate;

public record EmprestimoResponseDTO(
        Long id,
        Long equipamentoId,
        Long colaboradorId,
        LocalDate dataEmprestimo,
        LocalDate dataPrevistaDevolucao,
        LocalDate dataDevolucao
) {
}
