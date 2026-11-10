/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.esquinapp.modelo;

public class Cocina extends Usuario {

    private int idCocina;

    public Cocina() {
        super();
    }

    public Cocina(int idCocina, int idUsuario,
                  String nombre, String rol, String estado) {

        super(idUsuario, nombre, rol, estado);
        this.idCocina = idCocina;
    }

    public int getIdCocina() {
        return idCocina;
    }

    public void setIdCocina(int idCocina) {
        this.idCocina = idCocina;
    }
}

