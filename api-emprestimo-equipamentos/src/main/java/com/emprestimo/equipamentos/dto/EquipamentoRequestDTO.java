package com.emprestimo.equipamentos.dto;

import com.emprestimo.equipamentos.model.TipoEquipamento;
import jakarta.validation.constraints.*;


public record EquipamentoRequestDTO(
        @NotBlank String patrimonio,
        @NotBlank String nome,
        @NotNull TipoEquipamento tipo
) {


}
