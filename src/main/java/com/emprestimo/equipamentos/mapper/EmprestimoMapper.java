package com.emprestimo.equipamentos.mapper;

import com.emprestimo.equipamentos.dto.EmprestimoRequestDTO;
import com.emprestimo.equipamentos.dto.EmprestimoResponseDTO;
import com.emprestimo.equipamentos.model.Emprestimo;

import java.time.LocalDate;

public class EmprestimoMapper {

    public static EmprestimoResponseDTO toResponseDTO(Emprestimo emprestimo){
        return new EmprestimoResponseDTO(
                emprestimo.getId(),
                emprestimo.getEquipamento().getId(),
                emprestimo.getColaborador().getId(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataPrevistaDevolucao(),
                emprestimo.getDataDevolucao()
        );
    }

}
