/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.utilitario.enums;

/**
 *
 * @author alejandro
 */
public enum EnumPerfil
{
    ADMINISTRADOR(1),
    COBRADOR(2),
    SOPORTE(3);
    
    private final int id;

    EnumPerfil(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return id;
    }
}
