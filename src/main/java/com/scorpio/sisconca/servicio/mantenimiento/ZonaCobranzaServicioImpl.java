package com.scorpio.sisconca.servicio.mantenimiento;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.mantenimiento.ZonaCobranzaDao;
import com.scorpio.sisconca.entidad.ZonaCobranza;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Arles
 */
@Service("zonaCobranzaServicio")
public class ZonaCobranzaServicioImpl
        extends GenericEntityDaoImpl<ZonaCobranza, Serializable>
        implements ZonaCobranzaServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    ZonaCobranzaDao dao;

    @Override
    public List listarFiltro(ZonaCobranza zonaCobranza) throws Exception
    {
        return dao.listarFiltro(zonaCobranza);
    }

    @Override
    public int contarFiltro(ZonaCobranza zonaCobranza) throws Exception
    {
        return dao.contarFiltro(zonaCobranza);
    }

}
