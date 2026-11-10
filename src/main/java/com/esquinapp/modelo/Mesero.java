/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.modelo;

public class Mesero extends Usuario {

    private int idMesero;

    public Mesero() {
        super();
    }

    public Mesero(int idMesero, int idUsuario,
                  String nombre, String rol, String estado) {

        super(idUsuario, nombre, rol, estado);
        this.idMesero = idMesero;
    }

    public int getIdMesero() {
        return idMesero;
    }

    public void setIdMesero(int idMesero) {
        this.idMesero = idMesero;
    }
}

