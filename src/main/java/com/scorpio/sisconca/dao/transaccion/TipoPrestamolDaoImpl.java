package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.TipoPrestamo;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("tipoPrestamoDao")
public class TipoPrestamolDaoImpl extends GenericEntityDaoImpl<TipoPrestamo, Serializable> implements TipoPrestamoDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public List listarPorNombreYEstado(TipoPrestamo tipoPrestamo) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from TipoPrestamo p where p.id is not null");
        if (!("").equalsIgnoreCase(tipoPrestamo.getNombre()))
        {
            consulta.append(" and p.nombre like '%").append(tipoPrestamo.getNombre()).append("%'");
        }
        if (null != tipoPrestamo.getIdEstado().getId())
        {
            consulta.append(" and p.idEstado.id=").append(tipoPrestamo.getIdEstado().getId());
        }
        return listarHQL(consulta.toString(), null, null);
    }

    @Override
    public int contarFiltro(TipoPrestamo tipoPrestamo) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("select count(*) from TipoPrestamo p where p.id is not null");
        if (!"".equals(tipoPrestamo.getNombre()))
        {
            consulta.append(" and p.nombre like '%").append(tipoPrestamo.getNombre()).append("%'");
        }
        if (null != tipoPrestamo.getIdEstado().getId())
        {
            consulta.append(" and p.idEstado.id =").append(tipoPrestamo.getIdEstado().getId());
        }
        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }

}
