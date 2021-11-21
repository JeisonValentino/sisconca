package com.scorpio.sisconca.dao.mantenimiento;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.ZonaCobranza;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Arles
 */
@Repository("zonaCobranzaDao")
public class ZonaCobranzaDaoImpl extends GenericEntityDaoImpl<ZonaCobranza, Serializable> implements ZonaCobranzaDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public List listarFiltro(ZonaCobranza zonaCobranza) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from ZonaCobranza zc where zc.id is not null");
        if (!"".equals(zonaCobranza.getNombre()))
        {
            consulta.append(" and zc.nombre like '%").append(zonaCobranza.getNombre()).append("%'");
        }
        if (null != zonaCobranza.getIdEstado().getId())
        {
            consulta.append(" and zc.idEstado.id =").append(zonaCobranza.getIdEstado().getId());
        }
        consulta.append(" order by zc.abreviatura");
        return listarLazyHQL(consulta.toString(), null, zonaCobranza.getFirst(), zonaCobranza.getPageSize(), null);
    }

    @Override
    public int contarFiltro(ZonaCobranza zonaCobranza) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("select count(*) from ZonaCobranza zc where zc.id is not null");
        if (!"".equals(zonaCobranza.getNombre()))
        {
            consulta.append(" and zc.nombre like '%").append(zonaCobranza.getNombre()).append("%'");
        }
        if (null != zonaCobranza.getIdEstado().getId())
        {
            consulta.append(" and zc.idEstado.id =").append(zonaCobranza.getIdEstado().getId());
        }
        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }

}
