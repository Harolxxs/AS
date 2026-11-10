/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.modelo;

public class Comanda {

    private int idComanda;
    private int idPedido;
    private Integer idCocina;
    private String estadoPreparacion;

    public Comanda() {
    }

    public Comanda(int idComanda, int idPedido,
                   Integer idCocina, String estadoPreparacion) {

        this.idComanda = idComanda;
        this.idPedido = idPedido;
        this.idCocina = idCocina;
        this.estadoPreparacion = estadoPreparacion;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdCocina() {
        return idCocina;
    }

    public void setIdCocina(Integer idCocina) {
        this.idCocina = idCocina;
    }

    public String getEstadoPreparacion() {
        return estadoPreparacion;
    }

    public void setEstadoPreparacion(String estadoPreparacion) {
        this.estadoPreparacion = estadoPreparacion;
    }
}

