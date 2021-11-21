package com.scorpio.sisconca.servicio.maestros;

import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
public interface EmpleadoServicio
        extends GenericService<Empleado, Serializable>
{

    public List listarSinUsuario() throws Exception;
    
    public List listarSinUsuario(Empleado empleado) throws Exception;

    public List listarFiltro(Empleado empleado) throws Exception;

    public int contarFiltro(Empleado empleado) throws Exception;
}
