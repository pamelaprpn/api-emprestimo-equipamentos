package com.emprestimo.equipamentos.controller;

import com.emprestimo.equipamentos.dto.ColaboradorRequestDTO;
import com.emprestimo.equipamentos.dto.ColaboradorResponseDTO;
import com.emprestimo.equipamentos.dto.EmprestimoResponseDTO;
import com.emprestimo.equipamentos.service.ColaboradorService;
import com.emprestimo.equipamentos.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;
    private final EmprestimoService emprestimoService;

    public ColaboradorController(ColaboradorService colaboradorService, EmprestimoService emprestimoService) {
        this.colaboradorService = colaboradorService;
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public List<ColaboradorResponseDTO> listarColaboradores(){
        return colaboradorService.listar();
    }

    @GetMapping("/{id}")
    public ColaboradorResponseDTO listarColaboradorPorId(
            @PathVariable Long id
    ){
        return colaboradorService.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ColaboradorResponseDTO cadastrarColaborador(
            @Valid
            @RequestBody ColaboradorRequestDTO dto){

        return colaboradorService.cadastrar(dto);
    }

    @GetMapping("/{id}/emprestimos")
    public List<EmprestimoResponseDTO> listarEmprestimosDoColaborador(
            @PathVariable Long id) {
        return emprestimoService.listarDoColaborador(id);
    }

}
