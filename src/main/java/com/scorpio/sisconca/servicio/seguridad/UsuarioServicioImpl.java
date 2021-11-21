package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.UsuarioDao;
import com.scorpio.sisconca.entidad.Usuario;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Service("usuarioServicio")
public class UsuarioServicioImpl extends GenericEntityDaoImpl<Usuario, Serializable> implements UsuarioServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    UsuarioDao dao;

    @Override
    public Usuario ingresarSistema(Usuario usuario) throws Exception
    {
        return dao.ingresarSistema(usuario);
    }

    @Override
    public Usuario obtenerPorIdEmpleado(Usuario usuario) throws Exception
    {
        return dao.obtenerPorIdEmpleado(usuario);
    }

    @Override
    public Usuario obtenerPorUsuarioYContrasenia(Usuario usuario) throws Exception
    {
        return dao.obtenerPorUsuarioYContrasenia(usuario);
    }

    @Override
    public List listarPorNombreYNombreEmpleadoyEstado(Usuario usuario) throws Exception
    {
        return dao.listarPorNombreYNombreEmpleadoyEstado(usuario);
    }

    @Override
    public int contarPorNombreYNombreEmpleadoyEstado(Usuario usuario) throws Exception
    {
        return dao.contarPorNombreYNombreEmpleadoyEstado(usuario);
    }

}
