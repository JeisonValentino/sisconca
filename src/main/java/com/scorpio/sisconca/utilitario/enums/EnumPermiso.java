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
public enum EnumPermiso
{

    // <editor-fold defaultstate="collapsed" desc="MES">
    MES_REGISTRAR(1, 1, 1),
    MES_MODIFICAR(2, 1, 2),
    MES_LISTA(3, 1, 3),
    MES_INHABILITAR(4, 1, 4),
    MES_ELIMINAR(5, 1, 5),
    MES_CIERRE(66, 1, 8),
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="PERIODO">
    PERIODO_REGISTRAR(6, 2, 1),
    PERIODO_MODIFICAR(7, 2, 2),
    PERIODO_LISTA(8, 2, 3),
    PERIODO_INHABILITAR(9, 2, 4),
    PERIODO_ELIMINAR(10, 2, 5),
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="INFODAF">
    INFODAF_REGISTRAR(11, 3, 1),
    INFODAF_MODIFICAR(12, 3, 2),
    INFODAF_LISTA(13, 3, 3),
    INFODAF_INHABILITAR(14, 3, 4),
    INFODAF_ELIMINAR(15, 3, 5),
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="PRESUPUESTO">
    PRESUPUESTO_REGISTRAR(16, 4, 1),
    PRESUPUESTO_MODIFICAR(17, 4, 2),
    PRESUPUESTO_LISTA(18, 4, 3),
    PRESUPUESTO_INHABILITAR(19, 4, 4),
    PRESUPUESTO_ELIMINAR(20, 4, 5),
    // </editor-fold>
    // CLIENTE
    CLIENTE_REGISTRAR(21, 5, 1),
    CLIENTE_MODIFICAR(22, 5, 2),
    CLIENTE_LISTA(23, 5, 3),
    CLIENTE_INHABILITAR(24, 5, 4),
    CLIENTE_ELIMINAR(25, 5, 5),
    // SITIO
    SITIO_REGISTRAR(26, 6, 1),
    SITIO_MODIFICAR(27, 6, 2),
    SITIO_LISTA(28, 6, 3),
    SITIO_INHABILITAR(29, 6, 4),
    SITIO_ELIMINAR(30, 6, 5),
    // EMPLEADO
    EMPLEADO_REGISTRAR(31, 7, 1),
    EMPLEADO_MODIFICAR(32, 7, 2),
    EMPLEADO_LISTA(33, 7, 3),
    EMPLEADO_INHABILITAR(34, 7, 4),
    EMPLEADO_ELIMINAR(35, 7, 5),
    // ACTIVIDAD
    ACTIVIDAD_REGISTRAR(36, 8, 1),
    ACTIVIDAD_MODIFICAR(37, 8, 2),
    ACTIVIDAD_LISTA(38, 8, 3),
    ACTIVIDAD_INHABILITAR(39, 8, 4),
    ACTIVIDAD_ELIMINAR(40, 8, 5),
    // SEGMENTO
    SEGMENTO_REGISTRAR(41, 9, 1),
    SEGMENTO_MODIFICAR(42, 9, 2),
    SEGMENTO_LISTA(43, 9, 3),
    SEGMENTO_INHABILITAR(44, 9, 4),
    SEGMENTO_ELIMINAR(45, 9, 5),
    // EMPRESA
    EMPRESA_REGISTRAR(46, 10, 1),
    EMPRESA_MODIFICAR(47, 10, 2),
    EMPRESA_LISTA(48, 10, 3),
    EMPRESA_INHABILITAR(49, 10, 4),
    EMPRESA_ELIMINAR(50, 10, 5),
    // USUARIO
    USUARIO_REGISTRAR(51, 11, 1),
    USUARIO_MODIFICAR(52, 11, 2),
    USUARIO_LISTA(53, 11, 3),
    USUARIO_INHABILITAR(54, 11, 4),
    USUARIO_ELIMINAR(55, 11, 5),
    // PERFIL
    PERFIL_REGISTRAR(56, 12, 1),
    PERFIL_MODIFICAR(57, 12, 2),
    PERFIL_LISTA(58, 12, 3),
    PERFIL_INHABILITAR(59, 12, 4),
    PERFIL_ELIMINAR(60, 12, 5),
    // REPORTE
    REPORTE_LISTA(61, 13, 3),
    MES_AUDITAR(63, 1, 6),
    PRESUPUESTO_AUDITAR(64, 2, 6),
    PERIODO_AUDITAR(65, 4, 6);

    private final int id;

    private final int idEntidad;
    private final int idAccion;

    private EnumPermiso(int id, int idEntidad, int idAccion)
    {
        this.id = id;
        this.idEntidad = idEntidad;
        this.idAccion = idAccion;
    }

    public int getId()
    {
        return id;
    }

    public int getIdEntidad()
    {
        return idEntidad;
    }

    public int getIdAccion()
    {
        return idAccion;
    }

}
