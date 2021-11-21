package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Perfil;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("perfilDao")
public class PerfilDaoImpl extends GenericEntityDaoImpl<Perfil, Serializable> implements PerfilDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public List listarPorNombreYEstado(Perfil perfil) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from Perfil p where p.id is not null");
        if (!("").equalsIgnoreCase(perfil.getNombre()))
        {
            consulta.append(" and p.nombre like '%").append(perfil.getNombre()).append("%'");
        }
        if (null != perfil.getIdEstado().getId())
        {
            consulta.append(" and p.idEstado.id=").append(perfil.getIdEstado().getId());
        }
        return listarHQL(consulta.toString(), null, null);
    }

}
