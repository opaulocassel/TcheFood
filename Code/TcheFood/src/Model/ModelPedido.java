/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author adriano
 */
public class ModelPedido {
    
    private int id;
    private ModelUsuario usuarioId;
    private ModelFormaPagamento formaPagamentoId;
    private Date dataHora;
    private int statusPagamento;
    private int statusPedido;
    private double total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelUsuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(ModelUsuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public ModelFormaPagamento getFormaPagamentoId() {
        return formaPagamentoId;
    }

    public void setFormaPagamentoId(ModelFormaPagamento formaPagamentoId) {
        this.formaPagamentoId = formaPagamentoId;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }


    public void setDataHora(java.sql.Date dataHora) {
        this.dataHora = dataHora;
    }

    public int getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(int statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public int getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(int statusPedido) {
        this.statusPedido = statusPedido;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
