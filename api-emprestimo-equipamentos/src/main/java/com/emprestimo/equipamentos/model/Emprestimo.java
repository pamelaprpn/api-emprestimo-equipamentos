package com.emprestimo.equipamentos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long equipamentoId;
    private Long colaboradorId;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao = null;

    public Emprestimo(){

    }

    public Emprestimo(Long equipamentoId, Long colaboradorId, LocalDate hoje){
            this.equipamentoId = equipamentoId;
            this.colaboradorId = colaboradorId;
            dataEmprestimo = hoje;
            dataPrevistaDevolucao = hoje.plusDays(14);

    }

    public Long getId() {
        return id;
    }

    public Long getEquipamentoId() {
        return equipamentoId;
    }

    public Long getColaboradorId() {
        return colaboradorId;
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
        if(dataDevolucao != null){
            throw new IllegalStateException("Equipamento já foi devolvido!");
        }
        dataDevolucao = data;

    }

    public boolean estaAtrasadoEm(LocalDate data){
        if(dataPrevistaDevolucao.isBefore(data) && dataDevolucao == null){
            return true;
        }

        return false;

    }

    public boolean isAtrasado(){
       return estaAtrasadoEm(LocalDate.now());
    }

}
