package com.emprestimo.equipamentos.dto;

import com.emprestimo.equipamentos.model.TipoEquipamento;

public record EquipamentoResponseDTO(
        Long id,
        String patrimonio,
        String nome,
        TipoEquipamento tipo,
        boolean disponivel
) {
}
