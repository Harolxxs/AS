/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Pedido {

    private int idPedido;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private String observaciones;
    private int idMesa;
    private Integer idCliente;
    private int idMesero;
    private int idCuenta;

    public Pedido() {
    }

    public Pedido(int idPedido, LocalDate fecha, LocalTime hora,
                  String estado, String observaciones, int idMesa,
                  Integer idCliente, int idMesero, int idCuenta) {

        this.idPedido = idPedido;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.observaciones = observaciones;
        this.idMesa = idMesa;
        this.idCliente = idCliente;
        this.idMesero = idMesero;
        this.idCuenta = idCuenta;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdMesero() {
        return idMesero;
    }

    public void setIdMesero(int idMesero) {
        this.idMesero = idMesero;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }
}

