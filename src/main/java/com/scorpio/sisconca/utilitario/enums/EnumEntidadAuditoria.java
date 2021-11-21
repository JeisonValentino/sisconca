package com.scorpio.sisconca.utilitario.enums;

/**
 *
 * @author Stewen Mateo Villanueva<smateo@gmail.com>
 */
public enum EnumEntidadAuditoria
{
    SISTEMAS(14, "Sistema", "", 5),
    EMPLEADO(7, "Empleado", "", 1),
    CLIENTE(5, "Cliente", "", 1),
    PRESTAMO(15, "Prestamo", "", 6),
    PAGO(16, "Sitio", "Pago", 6),
    RENOVACION(17, "Renovacion", "", 6);

    private final int id;
    private final String nombre;
    private final String descripcion;
    private final int idCategoriaEntidad;

    private EnumEntidadAuditoria(int id, String nombre, String descripcion, int idCategoriaEntidad)
    {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoriaEntidad = idCategoriaEntidad;
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

    public int getIdCategoriaEntidad()
    {
        return idCategoriaEntidad;
    }

}
