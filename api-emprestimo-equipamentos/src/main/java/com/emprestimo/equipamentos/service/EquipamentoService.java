package com.emprestimo.equipamentos.service;

import com.emprestimo.equipamentos.dto.EquipamentoAtualizacaoDTO;
import com.emprestimo.equipamentos.dto.EquipamentoRequestDTO;
import com.emprestimo.equipamentos.dto.EquipamentoResponseDTO;
import com.emprestimo.equipamentos.mapper.EquipamentoMapper;
import com.emprestimo.equipamentos.model.Emprestimo;
import com.emprestimo.equipamentos.model.Equipamento;
import com.emprestimo.equipamentos.model.TipoEquipamento;
import com.emprestimo.equipamentos.repository.EmprestimoRepository;
import com.emprestimo.equipamentos.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final EmprestimoRepository emprestimoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, EmprestimoRepository emprestimoRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.emprestimoRepository = emprestimoRepository;
    }


    public List<EquipamentoResponseDTO> listar(TipoEquipamento tipo, Boolean disponivel){
        List<Equipamento> equipamentos;

        if(tipo == null && disponivel == null){
            equipamentos = equipamentoRepository.findAll();
        }else if(tipo != null && disponivel == null){
            equipamentos = equipamentoRepository.findByTipo(tipo);
        }else if(tipo == null && disponivel != null) {
            equipamentos =  equipamentoRepository.findByDisponivel(disponivel);
        }else{
            equipamentos = equipamentoRepository.findByTipoAndDisponivel(tipo, disponivel);
        }

        return equipamentos.stream()
                .map(equipamento -> EquipamentoMapper.toResponseDTO(equipamento))
                .toList();
    }

    public EquipamentoResponseDTO buscar(Long id){
       Equipamento equipamento = equipamentoRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));

        return EquipamentoMapper.toResponseDTO(equipamento);
    }

    public EquipamentoResponseDTO cadastrar(EquipamentoRequestDTO dto){
        if(equipamentoRepository.existsByPatrimonio(dto.patrimonio())){
            throw new IllegalStateException( "Já existe um equipamento cadastrado com esse patrimônio!");
        }

        Equipamento equipamento = EquipamentoMapper.toEntity(dto);

        equipamento.setDisponivel(true);

        Equipamento equipamentoSalvo = equipamentoRepository.save(equipamento);

        return EquipamentoMapper.toResponseDTO(equipamentoSalvo);
    }

    public EquipamentoResponseDTO atualizar(Long id, EquipamentoAtualizacaoDTO dtoDados){
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não existe!"));

        equipamento.setNome(dtoDados.nome());
        equipamento.setTipo(dtoDados.tipo());

        Equipamento equipamentoAtualizado = equipamentoRepository.save(equipamento);

        return EquipamentoMapper.toResponseDTO(equipamentoAtualizado);
    }

    public void remover(Long id){
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado!"));

        if (emprestimoRepository.existsByEquipamentoId(id)) {
            throw new IllegalStateException(
                    "Não é possível remover um equipamento com histórico de empréstimos!"
            );
        }

        equipamentoRepository.delete(equipamento);
    }



}
