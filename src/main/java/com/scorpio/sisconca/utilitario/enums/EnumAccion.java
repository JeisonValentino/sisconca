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
public enum EnumAccion
{

    REGISTRAR(1, "Registrar", ""),
    MODIFICAR(2, "Modificar", ""),
    LISTAR(3, "Listar", ""),
    INHABILITAR(4, "Inhabilitar", ""),
    ELIMINAR(5, "Eliminar", ""),
    AUDITAR(6, "Auditar", ""),
    HABILITAR(7, "Habilitar", ""),
    CIERRE(8, "Cierre", "");

    private final int id;
    private final String nombre;
    private final String descripcion;

    private EnumAccion(int id, String nombre, String descripcion)
    {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId()
    {
        return id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

}
