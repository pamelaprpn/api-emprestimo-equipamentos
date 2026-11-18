package com.emprestimo.equipamentos.mapper;

import com.emprestimo.equipamentos.dto.EquipamentoRequestDTO;
import com.emprestimo.equipamentos.dto.EquipamentoResponseDTO;
import com.emprestimo.equipamentos.model.Equipamento;

public class EquipamentoMapper {

    public static EquipamentoResponseDTO toResponseDTO(Equipamento equipamento){
        return new EquipamentoResponseDTO(
                equipamento.getId(),
                equipamento.getPatrimonio(),
                equipamento.getNome(),
                equipamento.getTipo(),
                equipamento.isDisponivel()
        );
    }

    public static Equipamento toEntity(EquipamentoRequestDTO equipamentoRequestDTO){
        Equipamento equipamento = new Equipamento();

        equipamento.setNome(equipamentoRequestDTO.nome());
        equipamento.setPatrimonio(equipamentoRequestDTO.patrimonio());
        equipamento.setTipo(equipamentoRequestDTO.tipo());

        return equipamento;
    }
}
