package com.scorpio.sisconca.utilitario.enums;

/**
 *
 * @author Stewen Mateo Villanueva<smateo@gmail.com>
 */
public enum EnumEntidad
{
    PERIODO(1, "Periodo", "", 1),
    USUARIO(2, "Usuario", "", 1),
    PERFIL(3, "Perfil", "", 1),
    EMPLEADO(4, "Empleado", "", 1),
    SITIO(5, "Sitio", "", 1),
    CLIENTE(6, "Cliente", "", 1),
    MES(7, "Mes", "", 1),
    EMPRESA(8, "Empresa", "", 1),
    ACTIVIDAD(9, "Actividad", "", 1),
    DEPARTAMENTO(10, "Departamento", "", 1),
    SEGMENTO(11, "Segmento", "", 1),
    SUB_SEGMENTO(12, "Segmento", "", 1);

    private final int id;
    private final String nombre;
    private final String descripcion;
    private final int idCategoriaEntidad;

    private EnumEntidad(int id, String nombre, String descripcion, int idCategoriaEntidad)
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
