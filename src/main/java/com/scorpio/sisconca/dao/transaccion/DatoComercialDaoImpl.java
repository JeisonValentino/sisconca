package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.DatoComercial;
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
@Repository("datoComercialDao")
public class DatoComercialDaoImpl extends GenericEntityDaoImpl<DatoComercial, Serializable> implements DatoComercialDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public DatoComercial obtenerPorIdDatoComercial(DatoComercial datoComercial) throws Exception
    {
        String consulta = "from DatoComercial g where g.id=" + datoComercial.getId();
        return obtenerPorHQL(consulta, null, null);
    }

    @Override
    public List listarPorDescripcionyEstado(DatoComercial datoComercial) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from DatoComercial g where g.id is not null");
        
        if (null != datoComercial.getIdEstado().getId())
        {
            consulta.append(" and g.idEstado.id =").append(datoComercial.getIdEstado().getId());
        }
        return listarLazyHQL(consulta.toString(), null, datoComercial.getFirst(), datoComercial.getPageSize(), null);
    }

    @Override
    public int contarPorDescripcionyEstado(DatoComercial datoComercial) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("select count(*) from DatoComercial g where g.id is not null");
        if (null != datoComercial.getIdEstado().getId())
        {
            consulta.append(" and g.idEstado.id =").append(datoComercial.getIdEstado().getId());
        }

        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }

    @Override
    public DatoComercial obtenerPorIdClienteDatoComercial(int idCliente) throws Exception {
        String consulta = "from DatoComercial g where g.idCliente=" + idCliente;
        return obtenerPorHQL(consulta, null, null);
    }
}
