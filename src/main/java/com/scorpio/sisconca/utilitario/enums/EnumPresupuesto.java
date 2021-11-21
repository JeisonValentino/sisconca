/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.utilitario.enums;

/**
 *
 * @author Stewen Mateo Villanueva<smateo@gmail.com>
 */
public enum EnumPresupuesto
{
    MENSUAL(1, "Mensual"),
    ANUAL(2, "Anual");

    private final int id;
    private final String nombre;

    private EnumPresupuesto(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId()
    {
        return id;
    }

    public String getNombre()
    {
        return nombre;
    }

}
