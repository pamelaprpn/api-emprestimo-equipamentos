package com.emprestimo.equipamentos.service;

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


    public List<Equipamento> listar(TipoEquipamento tipo, Boolean disponivel){
        if(tipo == null && disponivel == null){
            return equipamentoRepository.findAll();
        }else if(tipo != null && disponivel == null){
            return equipamentoRepository.findByTipo(tipo);
        }else if(tipo == null && disponivel != null) {
            return equipamentoRepository.findByDisponivel(disponivel);
        }

        return equipamentoRepository.findByTipoAndDisponivel(tipo, disponivel);

    }

    public Equipamento buscar(Long id){
        return equipamentoRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));
    }

    public Equipamento cadastrar(Equipamento equipamento){
       if(equipamentoRepository.existsByPatrimonio(equipamento.getPatrimonio())){
           throw new IllegalStateException( "Já existe um equipamento cadastrado com esse patrimônio!");
       }

       equipamento.setDisponivel(true);

       return  equipamentoRepository.save(equipamento);
    }

    public Equipamento atualizar(Long id, Equipamento dados){
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não existe!"));

        equipamento.setNome(dados.getNome());
        equipamento.setTipo(dados.getTipo());

        return equipamentoRepository.save(equipamento);

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
