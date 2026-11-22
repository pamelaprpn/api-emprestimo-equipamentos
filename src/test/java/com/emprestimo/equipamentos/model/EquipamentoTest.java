package com.emprestimo.equipamentos.model;

import com.emprestimo.equipamentos.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquipamentoTest {
    @Test
    void deveFicarIndisponivelAoEmprestarEquipamentoDisponivel() {
        Equipamento equipamento = new Equipamento();

        equipamento.emprestar();

        assertFalse(equipamento.isDisponivel());
    }

    @Test
    void naoDeveEmprestarEquipamentoIndisponivel() {
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(false);

        assertThrows(
                RegraNegocioException.class,
                () -> equipamento.emprestar()
        );
    }

    @Test
    void deveFicarDisponivelAoDevolverEquipamento() {
        Equipamento equipamento = new Equipamento();
        equipamento.setDisponivel(false);

        equipamento.devolver();

        assertTrue(equipamento.isDisponivel());
    }
}
