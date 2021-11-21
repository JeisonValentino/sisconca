/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.utilitario;

/**
 *
 * @author Stewen Mateo Villanueva<smateo@gmail.com>
 */
public class Constantes
{

    public static class Entidad
    {

        public static final int EMPLEADO = 7;
        public static final int USUARIO = 11;
        public static final int PERFIL = 12;
        public static final int ARTEFACTO = 13;
        public static final int SEDE = 14;
        public static final int GIRO = 15;
    }

    public static class Permiso
    {

        public static final int EMPLEADO_REGISTRAR = 31;
        public static final int EMPLEADO_MODIFICAR = 32;
        public static final int EMPLEADO_LISTAR = 33;
        public static final int EMPLEADO_INHABILITAR = 34;
        public static final int EMPLEADO_ELIMINAR = 35;

        public static final int USUARIO_REGISTRAR = 51;
        public static final int USUARIO_MODIFICAR = 52;
        public static final int USUARIO_LISTAR = 53;
        public static final int USUARIO_INHABILITAR = 54;
        public static final int USUARIO_ELIMINAR = 55;

        public static final int PERFIL_REGISTRAR = 56;
        public static final int PERFIL_MODIFICAR = 57;
        public static final int PERFIL_LISTAR = 58;
        public static final int PERFIL_INHABILITAR = 59;
        public static final int PERFIL_ELIMINAR = 60;
        
        public static final int ARTEFACTO_REGISTRAR = 61;
        public static final int ARTEFACTO_MODIFICAR = 62;
        public static final int ARTEFACTO_LISTAR = 63;
        public static final int ARTEFACTO_INHABILITAR = 64;
        public static final int ARTEFACTO_ELIMINAR = 65;
        
        public static final int SEDE_REGISTRAR = 66;
        public static final int SEDE_MODIFICAR = 67;
        public static final int SEDE_LISTAR = 68;
        public static final int SEDE_INHABILITAR = 69;
        public static final int SEDE_ELIMINAR = 70;

        public static final int REPORTE_LISTAR = 71;
        
        public static final int GIRO_REGISTRAR = 72;
        public static final int GIRO_MODIFICAR = 73;
        public static final int GIRO_LISTAR = 74;
        public static final int GIRO_INHABILITAR = 75;
        public static final int GIRO_ELIMINAR = 76;

    }

    public static class Accion
    {

        public static final int REGISTRAR = 1;
        public static final int MODIFICAR = 2;
        public static final int LISTAR = 3;
        public static final int INHABILITAR = 4;
        public static final int ELIMINAR = 5;
        public static final int AUDITAR = 6;
        public static final int HABILITAR = 7;
        public static final int CIERRE = 8;
    }

    public static class ClasificacionRegistro
    {

        public static final int PRESUPUESTO = 1;
        public static final int PROYECCION = 2;
        public static final int FOOD = 3;
        public static final int NO_FOOD = 4;
        public static final int PREVISION = 5;
        public static final int MOVIMIENTO = 6;
        public static final int PORCENTAJE = 7;
        public static final int MES_DEFINITIVO_CONTABLE = 8;
        public static final int TOTAL = 9;
    }
}
