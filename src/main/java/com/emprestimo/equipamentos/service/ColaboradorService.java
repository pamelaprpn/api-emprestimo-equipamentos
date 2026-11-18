package com.emprestimo.equipamentos.service;

import com.emprestimo.equipamentos.repository.ColaboradorRepository;
import org.springframework.stereotype.Service;

@Service
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorService(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }


}
