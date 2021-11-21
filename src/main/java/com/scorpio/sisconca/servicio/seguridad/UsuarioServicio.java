package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
public interface UsuarioServicio extends GenericService<Usuario, Serializable>
{

    public Usuario ingresarSistema(Usuario usuario) throws Exception;

    public Usuario obtenerPorUsuarioYContrasenia(Usuario usuario) throws Exception;

    public Usuario obtenerPorIdEmpleado(Usuario usuario) throws Exception;

    public List listarPorNombreYNombreEmpleadoyEstado(Usuario usuario) throws Exception;

    public int contarPorNombreYNombreEmpleadoyEstado(Usuario usuario) throws Exception;
}
