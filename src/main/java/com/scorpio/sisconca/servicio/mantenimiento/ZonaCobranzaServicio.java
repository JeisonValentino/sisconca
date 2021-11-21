package com.scorpio.sisconca.servicio.mantenimiento;

import com.scorpio.sisconca.entidad.ZonaCobranza;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arles
 */
public interface ZonaCobranzaServicio
        extends GenericService<ZonaCobranza, Serializable>
{   
    public List listarFiltro(ZonaCobranza zonaCobranza) throws Exception;

    public int contarFiltro(ZonaCobranza zonaCobranza) throws Exception;
}
