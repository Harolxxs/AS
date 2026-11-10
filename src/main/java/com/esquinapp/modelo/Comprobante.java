/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Comprobante {

    private int idComprobante;
    private int idPago;
    private LocalDate fecha;
    private BigDecimal total;

    public Comprobante() {
    }

    public Comprobante(int idComprobante, int idPago,
                       LocalDate fecha, BigDecimal total) {

        this.idComprobante = idComprobante;
        this.idPago = idPago;
        this.fecha = fecha;
        this.total = total;
    }

    public int getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(int idComprobante) {
        this.idComprobante = idComprobante;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
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
}

