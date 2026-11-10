/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cuenta {

    private int idCuenta;
    private LocalDate fecha;
    private BigDecimal total;
    private String estado;
    private int idMesa;
    private Integer idCliente;
    private Integer idCaja;

    public Cuenta() {
    }

    public Cuenta(int idCuenta, LocalDate fecha,
                  BigDecimal total, String estado,
                  int idMesa, Integer idCliente, Integer idCaja) {

        this.idCuenta = idCuenta;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.idMesa = idMesa;
        this.idCliente = idCliente;
        this.idCaja = idCaja;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }
}

