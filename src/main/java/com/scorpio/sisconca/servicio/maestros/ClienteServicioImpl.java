package com.scorpio.sisconca.servicio.maestros;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.maestros.ClienteDao;
import com.scorpio.sisconca.entidad.Cliente;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Service("clienteServicio")
public class ClienteServicioImpl
        extends GenericEntityDaoImpl<Cliente, Serializable>
        implements ClienteServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    ClienteDao dao;

    @Override
    public List listarPorNombreYEstado() throws Exception
    {
        return dao.listarPorNombreYEstado();
    }

    @Override
    public List listarFiltro(Cliente cliente) throws Exception
    {
        return dao.listarFiltro(cliente);
    }

    @Override
    public int contarFiltro(Cliente cliente) throws Exception
    {
        return dao.contarFiltro(cliente);
    }
    
    @Override
    public int obtenerSiguiente() throws Exception
    {
        return dao.obtenerSiguiente();
    }

}
