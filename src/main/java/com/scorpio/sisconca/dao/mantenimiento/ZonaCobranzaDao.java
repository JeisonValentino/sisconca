package com.scorpio.sisconca.dao.mantenimiento;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.ZonaCobranza;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arles
 */
public interface ZonaCobranzaDao extends GenericEntityDao<ZonaCobranza, Serializable>
{  
    public List listarFiltro(ZonaCobranza zonaCobranza) throws Exception;

    public int contarFiltro(ZonaCobranza zonaCobranza) throws Exception;
}
