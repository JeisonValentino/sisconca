package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.ParameterMode;
import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Repository("usuarioDao")
public class UsuarioDaoImpl extends GenericEntityDaoImpl<Usuario, Serializable> implements UsuarioDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public Usuario ingresarSistema(Usuario usuario) throws Exception
    {
        String consulta = "from Usuario u where u.nombre='" + usuario.getNombre() + "'"
                + " and u.contrasenia='" + usuario.getContrasenia() + "' and u.idEstado.id = 1";
        return obtenerPorHQL(consulta, null, null);

    }

    @Override
    public Usuario obtenerPorIdEmpleado(Usuario usuario) throws Exception
    {
        String consulta = "from Usuario u where u.idEmpleado.id=" + usuario.getIdEmpleado().getId();
        consulta += " order by u.nombre, u.idEmpleado.nombre, u.idEmpleado.apellidoPaterno, u.idEmpleado.apellidoMaterno ";
        return obtenerPorHQL(consulta, null, null);
    }

    @Override
    public Usuario obtenerPorUsuarioYContrasenia(Usuario usuario) throws Exception
    {
        String consulta = "from Usuario u where u.nombre='" + usuario.getNombre() + "'"
                + " and u.contrasenia='" + usuario.getContrasenia() + "'";
        consulta += " order by u.nombre, u.idEmpleado.nombre, u.idEmpleado.apellidoPaterno, u.idEmpleado.apellidoMaterno ";
        return obtenerPorHQL(consulta, null, null);
    }

    @Override
    public List listarPorNombreYNombreEmpleadoyEstado(Usuario usuario) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from Usuario u where u.id is not null");
        if (null != usuario.getNombre()
                && !"".equalsIgnoreCase(usuario.getNombre()))
        {
            consulta.append(" and u.nombre like '%").append(usuario.getNombre()).append("%'");
        }
        if (null != usuario.getIdEmpleado()
                && null != usuario.getIdEmpleado().getNombre()
                && !"".equals(usuario.getIdEmpleado().getNombre()))
        {
            consulta.append(" and u.idEmpleado.nombre like '%").append(usuario.getIdEmpleado().getNombre()).append("%'");
        }
        
        if (null != usuario.getIdEstado().getId())
        {
            consulta.append(" and u.idEstado.id =").append(usuario.getIdEstado().getId());
        }
        
        if (null != usuario.getIdEmpleado().getIdSede().getId())
        {
            consulta.append(" and u.idEmpleado.idSede.id =").append(usuario.getIdEmpleado().getIdSede().getId());
        }

        consulta.append(" order by u.nombre, u.idEmpleado.nombre, u.idEmpleado.apellidoPaterno, u.idEmpleado.apellidoMaterno ");

        return listarLazyHQL(consulta.toString(), null, usuario.getFirst(), usuario.getPageSize(), null);
    }

    @Override
    public int contarPorNombreYNombreEmpleadoyEstado(Usuario usuario) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("select count(*) from Usuario u where u.id is not null");
        if (!"".equals(usuario.getNombre()))
        {
            consulta.append(" and u.nombre  like '%").append(usuario.getNombre()).append("%'");
        }
        if (!"".equals(usuario.getIdEmpleado().getNombre()))
        {
            consulta.append(" and u.idEmpleado.nombre like '%").append(usuario.getIdEmpleado().getNombre()).append("%'");
        }
        if (null != usuario.getIdEstado().getId())
        {
            consulta.append(" and u.idEstado.id =").append(usuario.getIdEstado().getId());
        }

        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }
}
