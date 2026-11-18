package com.emprestimo.equipamentos.mapper;

import com.emprestimo.equipamentos.dto.ColaboradorRequestDTO;
import com.emprestimo.equipamentos.dto.ColaboradorResponseDTO;
import com.emprestimo.equipamentos.model.Colaborador;

public class ColaboradorMapper {

    public static ColaboradorResponseDTO toResponseDTO(Colaborador colaborador){
        return new ColaboradorResponseDTO(
                colaborador.getId(),
                colaborador.getNome(),
                colaborador.getEmail(),
                colaborador.getCargo()
        );
    }

    public static Colaborador toEntity(ColaboradorRequestDTO colaboradorResponseDTO){
        Colaborador colaborador = new Colaborador();

        colaborador.setNome(colaboradorResponseDTO.nome());
        colaborador.setEmail(colaboradorResponseDTO.email());
        colaborador.setCargo(colaboradorResponseDTO.cargo());

        return colaborador;
    }
}
