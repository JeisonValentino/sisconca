package com.scorpio.sisconca.dao.maestros;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Empleado;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
public interface EmpleadoDao extends GenericEntityDao<Empleado, Serializable>
{

    public List listarSinUsuario() throws Exception;
    
    public List listarSinUsuario(Empleado empleado) throws Exception;

    public List listarFiltro(Empleado empleado) throws Exception;

    public int contarFiltro(Empleado empleado) throws Exception;
}
