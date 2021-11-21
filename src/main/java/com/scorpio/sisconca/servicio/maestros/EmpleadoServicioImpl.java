package com.scorpio.sisconca.servicio.maestros;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.maestros.EmpleadoDao;
import com.scorpio.sisconca.entidad.Empleado;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Service("empleadoServicio")
public class EmpleadoServicioImpl
        extends GenericEntityDaoImpl<Empleado, Serializable>
        implements EmpleadoServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    EmpleadoDao dao;

    @Override
    public List listarSinUsuario() throws Exception
    {
        return dao.listarSinUsuario();
    }

    @Override
    public List listarFiltro(Empleado empleado) throws Exception
    {
        return dao.listarFiltro(empleado);
    }

    @Override
    public int contarFiltro(Empleado empleado) throws Exception
    {
        return dao.contarFiltro(empleado);
    }

    @Override
    public List listarSinUsuario(Empleado empleado) throws Exception {
        return dao.listarSinUsuario(empleado);
        
    }

}
