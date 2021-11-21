package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Sede;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("sedeDao")
public class SedeDaoImpl extends GenericEntityDaoImpl<Sede, Serializable> implements SedeDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public List listarPorNombreYEstado(Sede sede) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from Sede s where s.id is not null");
        if (!("").equalsIgnoreCase(sede.getNombre()))
        {
            consulta.append(" and s.nombre like '%").append(sede.getNombre()).append("%'");
        }
        return listarHQL(consulta.toString(), null, null);
    }
    
    

}
