package com.emprestimo.equipamentos.service;

import com.emprestimo.equipamentos.exception.RegraNegocioException;
import com.emprestimo.equipamentos.model.Colaborador;
import com.emprestimo.equipamentos.model.Emprestimo;
import com.emprestimo.equipamentos.model.Equipamento;
import com.emprestimo.equipamentos.repository.ColaboradorRepository;
import com.emprestimo.equipamentos.repository.EmprestimoRepository;
import com.emprestimo.equipamentos.repository.EquipamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest {

    private static final Long EQUIPAMENTO_DISPONIVEL_ID = 2L;
    private static final Long COLABORADOR_ID = 1L;
    private static final String MENSAGEM_REGRA_R10 =
            "Colaborador possui empréstimo atrasado e não pode pegar outro equipamento.";

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Test
    void naoDeveEmprestarEquipamentoDisponivelAColaboradorComEmprestimoAtrasado() {
        LocalDate hoje = LocalDate.now();
        Colaborador colaborador = new Colaborador();
        colaborador.setNome("Colaborador de teste");
        colaborador.setEmail("r10.teste@empresa.com");
        colaborador.setCargo("Analista");

        Equipamento equipamentoAntigo = new Equipamento();
        equipamentoAntigo.setDisponivel(false);
        Emprestimo emprestimoAtrasado = new Emprestimo(
                equipamentoAntigo,
                colaborador,
                hoje.minusDays(15)
        );

        Equipamento equipamentoDisponivel = new Equipamento();
        equipamentoDisponivel.setDisponivel(true);

        assertFalse(equipamentoAntigo.isDisponivel());
        assertNull(emprestimoAtrasado.getDataDevolucao());
        assertEquals(hoje.minusDays(1), emprestimoAtrasado.getDataPrevistaDevolucao());
        assertTrue(emprestimoAtrasado.estaAtrasadoEm(hoje));
        assertTrue(equipamentoDisponivel.isDisponivel());

        when(equipamentoRepository.findById(EQUIPAMENTO_DISPONIVEL_ID))
                .thenReturn(Optional.of(equipamentoDisponivel));
        when(colaboradorRepository.findById(COLABORADOR_ID))
                .thenReturn(Optional.of(colaborador));
        when(emprestimoRepository
                .existsByColaborador_IdAndDataDevolucaoIsNullAndDataPrevistaDevolucaoBefore(
                        eq(COLABORADOR_ID),
                        any(LocalDate.class)
                ))
                .thenReturn(true);

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> emprestimoService.emprestar(
                        EQUIPAMENTO_DISPONIVEL_ID,
                        COLABORADOR_ID
                )
        );

        assertEquals(MENSAGEM_REGRA_R10, exception.getMessage());
        verify(equipamentoRepository, never()).save(any(Equipamento.class));
        verify(emprestimoRepository, never()).save(any(Emprestimo.class));
    }
}
