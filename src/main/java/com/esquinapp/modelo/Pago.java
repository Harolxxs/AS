/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pago {

    private int idPago;
    private int idCuenta;
    private LocalDate fecha;
    private BigDecimal monto;
    private String metodoPago;
    private String estado;

    public Pago() {
    }

    public Pago(int idPago, int idCuenta, LocalDate fecha,
                BigDecimal monto, String metodoPago, String estado) {

        this.idPago = idPago;
        this.idCuenta = idCuenta;
        this.fecha = fecha;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
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

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

