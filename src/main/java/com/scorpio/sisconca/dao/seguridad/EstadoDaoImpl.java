package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Estado;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("estadoDao")
public class EstadoDaoImpl extends GenericEntityDaoImpl<Estado, Serializable> implements EstadoDao
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public List listarEstadoSolicitud() throws Exception
    {
        String consulta = "from Estado e where e.id in ("
                + EnumEstado.APROBADO.getId() + ", "+  EnumEstado.NO_APROBADO.getId()+ ") "
                + " order by e.descripcion";
        return listarHQL(consulta, null, null);
    }
}
