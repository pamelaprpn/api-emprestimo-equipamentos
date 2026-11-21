package com.emprestimo.equipamentos.controller;

import com.emprestimo.equipamentos.dto.EmprestimoRequestDTO;
import com.emprestimo.equipamentos.dto.EmprestimoResponseDTO;
import com.emprestimo.equipamentos.dto.EquipamentoResponseDTO;
import com.emprestimo.equipamentos.mapper.EmprestimoMapper;
import com.emprestimo.equipamentos.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public List<EmprestimoResponseDTO> listarEmprestimo(@RequestParam(required = false) Boolean atrasados){
        return emprestimoService.listar(atrasados);
    }

    @GetMapping("/{id}")
    public EmprestimoResponseDTO listarEmprestimoPorId(@PathVariable Long id){
        return emprestimoService.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmprestimoResponseDTO emprestar(
            @RequestParam Long equipamentoId,
            @RequestParam Long colaboradorId) {
        return emprestimoService.emprestar(equipamentoId, colaboradorId);
    }

    @PutMapping("/{id}/devolucao")
    public EmprestimoResponseDTO devolver(@PathVariable Long id) {
        return emprestimoService.devolver(id);
    }
}
