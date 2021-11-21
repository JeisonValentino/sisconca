/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.utilitario.enums;

/**
 *
 * @author YVAN
 */
public enum EnumTipoAuditoria {

    ACCESO("A", "Acceso", ""),
    DATOS("D", "Datos", "");

    private final String id;
    private final String nombre;
    private final String descripcion;

    private EnumTipoAuditoria(String id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
