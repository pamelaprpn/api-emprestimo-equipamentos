package com.emprestimo.equipamentos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmprestimoRequestDTO(
        @NotNull Long equipamentoId,
        @NotNull Long colaboradorId
) {


}
