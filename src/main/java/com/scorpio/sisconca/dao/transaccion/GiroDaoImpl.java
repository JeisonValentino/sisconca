package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Giro;
import java.io.Serializable;
import java.util.List;
import javax.persistence.ParameterMode;
import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Arles
 */
@Repository("giroDao")
public class GiroDaoImpl extends GenericEntityDaoImpl<Giro, Serializable> implements GiroDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public Giro obtenerPorIdGiro(Giro giro) throws Exception
    {
        String consulta = "from Giro g where g.id=" + giro.getId();
        consulta += " order by g.descripcion ";
        return obtenerPorHQL(consulta, null, null);
    }

    @Override
    public List listarPorDescripcionyEstado(Giro giro) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from Giro g where g.id is not null");
        if (null != giro.getDescripcion()
                && !"".equalsIgnoreCase(giro.getDescripcion()))
        {
            consulta.append(" and g.descripcion like '%").append(giro.getDescripcion()).append("%'");
        }
        if (null != giro.getIdEstado().getId())
        {
            consulta.append(" and g.idEstado.id =").append(giro.getIdEstado().getId());
        }

        consulta.append(" order by g.descripcion");

        return listarLazyHQL(consulta.toString(), null, giro.getFirst(), giro.getPageSize(), null);
    }

    @Override
    public int contarPorDescripcionyEstado(Giro giro) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("select count(*) from Giro g where g.id is not null");
        if (!"".equals(giro.getDescripcion()))
        {
            consulta.append(" and g.descripcion  like '%").append(giro.getDescripcion()).append("%'");
        }
        if (null != giro.getIdEstado().getId())
        {
            consulta.append(" and g.idEstado.id =").append(giro.getIdEstado().getId());
        }

        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }
}
