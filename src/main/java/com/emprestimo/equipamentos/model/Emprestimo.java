package com.emprestimo.equipamentos.model;

import com.emprestimo.equipamentos.exception.RegraNegocioException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

import java.time.LocalDate;

@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;

    public Emprestimo(){

    }

    public Emprestimo(Equipamento equipamento, Colaborador colaborador, LocalDate hoje){
            this.equipamento = equipamento;
            this.colaborador = colaborador;
            dataEmprestimo = hoje;
            dataPrevistaDevolucao = hoje.plusDays(14);

    }

    public Long getId() {
        return id;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void devolver(LocalDate data){
        if (dataDevolucao != null) {
            throw new RegraNegocioException(
                    "Equipamento já foi devolvido!"
            );
        }

        dataDevolucao = data;
    }

    public boolean estaAtrasadoEm(LocalDate data){
        return dataPrevistaDevolucao.isBefore(data) && dataDevolucao == null;
    }

    public boolean isAtrasado(){
       return estaAtrasadoEm(LocalDate.now());
    }

}
