package com.emprestimo.equipamentos.service;

import com.emprestimo.equipamentos.dto.ColaboradorRequestDTO;
import com.emprestimo.equipamentos.dto.ColaboradorResponseDTO;
import com.emprestimo.equipamentos.exception.RecursoNaoEncontradoException;
import com.emprestimo.equipamentos.exception.RegraNegocioException;
import com.emprestimo.equipamentos.mapper.ColaboradorMapper;
import com.emprestimo.equipamentos.model.Colaborador;
import com.emprestimo.equipamentos.repository.ColaboradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorService(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    public List<ColaboradorResponseDTO> listar(){
        List<Colaborador> colaboradores = colaboradorRepository.findAll();

        return colaboradores.stream()
                .map(colaborador -> ColaboradorMapper.toResponseDTO(colaborador))
                .toList();
    }

    public ColaboradorResponseDTO buscar(Long id){
        Colaborador colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Colaborador não encontrado."));

        return ColaboradorMapper.toResponseDTO(colaborador);
    }

    public ColaboradorResponseDTO cadastrar(ColaboradorRequestDTO dto){
        if(colaboradorRepository.existsByEmail(dto.email())){
            throw new RegraNegocioException("Já existe esse email cadastrado!");
        }

        Colaborador colaborador = ColaboradorMapper.toEntity(dto);

        return ColaboradorMapper.toResponseDTO(colaboradorRepository.save(colaborador));

    }


}
