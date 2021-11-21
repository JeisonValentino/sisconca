package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.ConceptoGasto;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Arles
 */
@Repository("conceptoGastoDao")
public class ConceptoGastoDaoImpl extends GenericEntityDaoImpl<ConceptoGasto, Serializable> implements ConceptoGastoDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public List listarPorNombreYEstado(ConceptoGasto conceptoGasto) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from ConceptoGasto cg where cg.id is not null");
        if (!"".equals(conceptoGasto.getNombre()))
        {
            consulta.append(" and cg.nombre like '%").append(conceptoGasto.getNombre()).append("%'");
        }
        if (null != conceptoGasto.getIdEstado().getId())
        {
            consulta.append(" and cg.idEstado.id =").append(conceptoGasto.getIdEstado().getId());
        }
        consulta.append(" order by cg.nombre");
        return listarLazyHQL(consulta.toString(), null, conceptoGasto.getFirst(), conceptoGasto.getPageSize(), null);
    }

    @Override
    public List listarFiltro(ConceptoGasto conceptoGasto) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from ConceptoGasto cg where cg.id is not null");
        if (!"".equals(conceptoGasto.getNombre()))
        {
            consulta.append(" and cg.nombre like '%").append(conceptoGasto.getNombre()).append("%'");
        }
        if (null != conceptoGasto.getIdEstado().getId())
        {
            consulta.append(" and cg.idEstado.id =").append(conceptoGasto.getIdEstado().getId());
        }
        consulta.append(" order by cg.nombre");
        return listarLazyHQL(consulta.toString(), null, conceptoGasto.getFirst(), conceptoGasto.getPageSize(), null);
    }

    @Override
    public int contarFiltro(ConceptoGasto conceptoGasto) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("select count(*) from ConceptoGasto cg where cg.id is not null");
        if (!"".equals(conceptoGasto.getNombre()))
        {
            consulta.append(" and cg.nombre like '%").append(conceptoGasto.getNombre()).append("%'");
        }
        if (null != conceptoGasto.getIdEstado().getId())
        {
            consulta.append(" and cg.idEstado.id =").append(conceptoGasto.getIdEstado().getId());
        }
        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }

}
