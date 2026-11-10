/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.modelo;

public class Caja extends Usuario {

    private int idCaja;

    public Caja() {
        super();
    }

    public Caja(int idCaja, int idUsuario,
                String nombre, String rol, String estado) {

        super(idUsuario, nombre, rol, estado);
        this.idCaja = idCaja;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }
}
