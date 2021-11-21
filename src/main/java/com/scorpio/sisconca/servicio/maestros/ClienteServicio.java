package com.scorpio.sisconca.servicio.maestros;

import com.scorpio.sisconca.entidad.Cliente;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
public interface ClienteServicio
        extends GenericService<Cliente, Serializable>
{

    public List listarPorNombreYEstado() throws Exception;  

    public List listarFiltro(Cliente cliente) throws Exception;

    public int contarFiltro(Cliente cliente) throws Exception;
    
    public int obtenerSiguiente() throws Exception;
}
