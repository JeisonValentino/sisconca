package com.scorpio.sisconca.utilitario.enums;

/**
 *
 * @author
 */
public enum EnumTipoDocumentoIdentidad
{
    DNI(1, "Documento Nacional de Identidad", 8),
    EXTRANJERO(2, "Documento Nacional de Identidad para Extranjeros", 12),
    PASAPORTE(3, "Documento con validez internacional", 12);

    private final int id;
    private final String descripcion;
    private final int longitud;

    private EnumTipoDocumentoIdentidad(int id, String descripcion, int longitud)
    {
        this.id = id;
        this.descripcion = descripcion;
        this.longitud = longitud;
    }

    public int getId()
    {
        return id;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public int getLongitud()
    {
        return longitud;
    }
}
