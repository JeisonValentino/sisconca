package com.scorpio.sisconca.utilitario.enums;

/**
 *
 * @author Stewen Mateo Villanueva<smateo@gmail.com>
 */
public enum EnumEstado
{
    ACTIVO(1, "Activo", ""),
    INACTIVO(2, "Inactivo", ""),    
    EN_ESPERA(3, "En espera", "Usado para mencionar que aún no se registrará un Periodo"),
    CERRADO(4, "Cerrado", "Usado para mencionar que ya se registro un Periodo"),
    INACTIVO_POR_DEPENDENCIA(5, "Inactivado por dependencia",
            "Otra Entidad fue inactivada y esta fue necesaria ser desactivada para ello."),
    CANCELADO(6, "Cancelado", "Se ha pagado el préstamo"),
    RENOVADO(7, "Renovado", "Se ha creado un préstamo con este como origen"),
    APROBADO(8, "Aprobado", ""),
    NO_APROBADO(9, "No Aprobado", "");

    private final int id;
    private final String nombre;
    private final String descripcion;

    private EnumEstado(int id, String nombre, String descripcion)
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
