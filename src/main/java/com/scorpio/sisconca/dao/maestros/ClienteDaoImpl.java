package com.scorpio.sisconca.dao.maestros;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Cliente;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Repository("clienteDao")
public class ClienteDaoImpl extends GenericEntityDaoImpl<Cliente, Serializable> implements ClienteDao
{

    private static final long serialVersionUID = 1L;

    @Override
    public List listarPorNombreYEstado() throws Exception
    {
        String consulta = "from Cliente e where e.idEstado.id ="
                + EnumEstado.ACTIVO.getId() + " and (select count(id) from Usuario u where u.idCliente.id = e.id) = 0"
                + " order by e.nombre, e.apellidoPaterno, e.apellidoMaterno";
        return listarHQL(consulta, null, null);
    }

    @Override
    public List listarFiltro(Cliente cliente) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("from Cliente u where u.id is not null");
        if (!"".equals(cliente.getNombre()))
        {
            consulta.append(" and u.nombre like '%").append(cliente.getNombre()).append("%'");
        }
        if (null != cliente.getIdEstado().getId())
        {
            consulta.append(" and u.idEstado.id =").append(cliente.getIdEstado().getId());
        }
        consulta.append(" order by u.nombre, u.apellidoPaterno, u.apellidoMaterno");
        return listarLazyHQL(consulta.toString(), null, cliente.getFirst(), cliente.getPageSize(), null); 
    }

    @Override
    public int contarFiltro(Cliente cliente) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("select count(*) from Cliente u where u.id is not null");
        if (!"".equals(cliente.getNombre()))
        {
            consulta.append(" and u.nombre like '%").append(cliente.getNombre()).append("%'");
        }
        if (null != cliente.getIdEstado().getId())
        {
            consulta.append(" and u.idEstado.id =").append(cliente.getIdEstado().getId());
        }
        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }
    
    @Override
    public int obtenerSiguiente() throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("SELECT max(id)+1 FROM Cliente");
        
        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }

}
