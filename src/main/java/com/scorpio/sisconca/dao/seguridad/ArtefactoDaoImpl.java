package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Artefacto;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("artefactoDao")
public class ArtefactoDaoImpl extends GenericEntityDaoImpl<Artefacto, Serializable> implements ArtefactoDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public List listarPorNombreYEstado(Artefacto artefacto) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from Artefacto a where a.id is not null");
        if (!("").equalsIgnoreCase(artefacto.getNombre()))
        {
            consulta.append(" and a.nombre like '%").append(artefacto.getNombre()).append("%'");
        }
        if (null != artefacto.getIdEstado().getId())
        {
            consulta.append(" and a.idEstado.id=").append(artefacto.getIdEstado().getId());
        }
        return listarHQL(consulta.toString(), null, null);
    }

}
