package com.emprestimo.equipamentos.controller;

import com.emprestimo.equipamentos.dto.*;
import com.emprestimo.equipamentos.model.TipoEquipamento;
import com.emprestimo.equipamentos.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    public List<EquipamentoResponseDTO> listarEquipamentos(
            @RequestParam(required = false) TipoEquipamento tipo,
            @RequestParam(required = false) Boolean disponivel){

        return equipamentoService.listar(tipo, disponivel);
    }

    @GetMapping("/{id}")
    public EquipamentoResponseDTO listarEquipamentosPorId(
            @PathVariable Long id){
        return equipamentoService.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipamentoResponseDTO cadastrarEquipamento(@Valid @RequestBody EquipamentoRequestDTO dto){
        return equipamentoService.cadastrar(dto);
    }

    @PutMapping("/{id}")
    public EquipamentoResponseDTO atualizaEquipamento(@Valid
            @RequestBody EquipamentoAtualizacaoDTO dto,
            @PathVariable Long id){
        return equipamentoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarEquipamento(@PathVariable Long id){
        equipamentoService.remover(id);
    }


}
