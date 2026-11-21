package com.emprestimo.equipamentos.service;

import com.emprestimo.equipamentos.dto.EmprestimoResponseDTO;
import com.emprestimo.equipamentos.exception.RecursoNaoEncontradoException;
import com.emprestimo.equipamentos.exception.RegraNegocioException;
import com.emprestimo.equipamentos.mapper.EmprestimoMapper;
import com.emprestimo.equipamentos.model.Emprestimo;
import com.emprestimo.equipamentos.model.Equipamento;
import com.emprestimo.equipamentos.repository.ColaboradorRepository;
import com.emprestimo.equipamentos.repository.EmprestimoRepository;
import com.emprestimo.equipamentos.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final ColaboradorRepository colaboradorRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, EquipamentoRepository equipamentoRepository, ColaboradorRepository colaboradorRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.colaboradorRepository = colaboradorRepository;

    }


    public List<EmprestimoResponseDTO> listar(Boolean atrasados){
        LocalDate hoje = LocalDate.now();

        List<Emprestimo> emprestimos;

        if (Boolean.TRUE.equals(atrasados)) {
            emprestimos = emprestimoRepository
                    .findByDataDevolucaoIsNullAndDataPrevistaDevolucaoBefore(hoje);
        } else {
            emprestimos = emprestimoRepository.findAll();
        }

        return emprestimos.stream()
                .map(EmprestimoMapper::toResponseDTO)
                .toList();

    }

    public EmprestimoResponseDTO buscar(Long id){
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Emprestimo não encontrado: id=" + id));

        return EmprestimoMapper.toResponseDTO(emprestimo);

    }

    @Transactional
    public EmprestimoResponseDTO emprestar(Long equipamentoId, Long colaboradorId) {

        LocalDate hoje = LocalDate.now();

        Equipamento equipamento = equipamentoRepository.findById(equipamentoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Equipamento não encontrado: id=" + equipamentoId
                ));

        if (!colaboradorRepository.existsById(colaboradorId)) {
            throw new RecursoNaoEncontradoException(
                    "Colaborador não encontrado: id=" + colaboradorId
            );
        }

        boolean possuiAtrasado = emprestimoRepository
                .existsByColaboradorIdAndDataDevolucaoIsNullAndDataPrevistaDevolucaoBefore(
                        colaboradorId, hoje
                );

        if (possuiAtrasado) {
            throw new RegraNegocioException(
                    "Colaborador possui empréstimo atrasado e não pode pegar outro equipamento."
            );
        }

        equipamento.emprestar();

        Emprestimo emprestimo = new Emprestimo(
                equipamentoId,
                colaboradorId,
                hoje
        );

        equipamentoRepository.save(equipamento);
        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);

        return EmprestimoMapper.toResponseDTO(emprestimoSalvo);
    }


    public List<EmprestimoResponseDTO> listarDoColaborador(Long colaboradorId){
        if (!colaboradorRepository.existsById(colaboradorId)) {
            throw new RecursoNaoEncontradoException(
                    "Colaborador não encontrado: id=" + colaboradorId
            );
        }

        return emprestimoRepository.findByColaboradorId(colaboradorId)
                .stream()
                .map(EmprestimoMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public EmprestimoResponseDTO devolver(Long id) {

        LocalDate hoje = LocalDate.now();

        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                    "Empréstimo não encontrado: id=" + id
                ));

        Equipamento equipamento = equipamentoRepository
                .findById(emprestimo.getEquipamentoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Equipamento não encontrado: id=" + emprestimo.getEquipamentoId()
                ));

        emprestimo.devolver(hoje);

        equipamento.devolver();

        equipamentoRepository.save(equipamento);
        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);

        return EmprestimoMapper.toResponseDTO(emprestimoSalvo);
    }

}
