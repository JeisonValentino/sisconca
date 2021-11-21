package com.scorpio.sisconca.dao.maestros;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Cliente;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
public interface ClienteDao extends GenericEntityDao<Cliente, Serializable>
{

    public List listarPorNombreYEstado() throws Exception;

    public List listarFiltro(Cliente cliente) throws Exception; 

    public int contarFiltro(Cliente cliente) throws Exception;
    
    public int obtenerSiguiente() throws Exception;
}
