package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("perfilPorUsuarioDao")
public class PerfilPorUsuarioDaoImpl extends GenericEntityDaoImpl<PerfilPorUsuario, Serializable> implements PerfilPorUsuarioDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public List<PerfilPorUsuario> obtenerTodosPorUsuario(PerfilPorUsuario perfilPorUsuario) throws Exception
    {
        String consulta = " from PerfilPorUsuario pu where pu.idUsuario.id=" + perfilPorUsuario.getIdUsuario().getId();
        return listarHQL(consulta, null, null);
    }

    @Override
    public List<PerfilPorUsuario> obtenerTodosPorPerfil(PerfilPorUsuario perfilPorUsuario) throws Exception
    {
        String consulta = "from PerfilPorUsuario pu where pu.idPerfil.id=" + perfilPorUsuario.getIdPerfil().getId();
        return listarHQL(consulta, null, null);
    }

    @Override
    public List<PerfilPorUsuario> getByUserId(int id) throws Exception
    {
        String consulta = "from PerfilPorUsuario pu where pu.idUsuario.id=" + id;
        return listarHQL(consulta, null, null);
    }

}
