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
public enum EnumClasificacionRegistro
{
    PRESUPUESTO(new Short("" + 1), "Presupuesto"),
    PROYECCION(new Short("" + 2), "Proyección"),
    FOOD(new Short("" + 3), "Food"),
    NO_FOOD(new Short("" + 4), "No food"),
    PREVISION(new Short("" + 5), "Previsión"),
    MOVIMIENTO(new Short("" + 6), "Movimiento"),
    PORCENTAJE(new Short("" + 7), "Porcentaje"),
    MES_DEFINITIVO_CONTABLE(new Short("" + 8), "Mes definitivo contable"),
    TOTAL(new Short("" + 9), "Total"),;
    

    private final short id;
    private final String nombre;

    private EnumClasificacionRegistro(short id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public short getId()
    {
        return id;
    }

    public String getNombre()
    {
        return nombre;
    }

}
