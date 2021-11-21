package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.PerfilPorUsuarioDao;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("perfilPorUsuarioServicio")
public class PerfilPorUsuarioServicioImpl extends GenericEntityDaoImpl<PerfilPorUsuario, Serializable>
        implements PerfilPorUsuarioServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    PerfilPorUsuarioDao dao;

    @Override
    public List<PerfilPorUsuario> obtenerTodosPorUsuario(PerfilPorUsuario perfilPorUsuario) throws Exception
    {
        return dao.obtenerTodosPorUsuario(perfilPorUsuario);
    }

    @Override
    public List<PerfilPorUsuario> obtenerTodosPorPerfil(PerfilPorUsuario perfilPorUsuario) throws Exception
    {
        return dao.obtenerTodosPorPerfil(perfilPorUsuario);
    }

    @Override
    public List<PerfilPorUsuario> getByUserId(int id) throws Exception
    {
        return dao.getByUserId(id);
    }

}
