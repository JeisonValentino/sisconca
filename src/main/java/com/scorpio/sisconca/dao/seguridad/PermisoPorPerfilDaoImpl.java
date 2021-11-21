package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.PermisoPorPerfil;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("permisoPorPerfilDao")
public class PermisoPorPerfilDaoImpl extends GenericEntityDaoImpl<PermisoPorPerfil, Serializable> implements PermisoPorPerfilDao
{
    private static final long serialVersionUID = 1L;

    @Override
    public List<PermisoPorPerfil> obtenerTodoPorPerfil(PermisoPorPerfil permisoPorPerfil)
    {
        String consulta = "from PermisoPorPerfil pp where pp.idPerfil.id=" + permisoPorPerfil.getIdPerfil().getId();
        return listarHQL(consulta, null, null);
    }

}
