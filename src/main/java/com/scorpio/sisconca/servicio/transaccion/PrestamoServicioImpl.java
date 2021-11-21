package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.transaccion.PrestamoDao;
import com.scorpio.sisconca.entidad.Prestamo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Service("prestamoServicio")
public class PrestamoServicioImpl extends GenericEntityDaoImpl<Prestamo, Serializable> implements PrestamoServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    PrestamoDao dao;

    @Override
    public List listarPorNombreYEstado() throws Exception
    {
        return dao.listarPorNombreYEstado();
    }

    @Override
    public List listarFiltro(Prestamo prestamo) throws Exception
    {
        return dao.listarFiltro(prestamo);
    }

    @Override
    public int contarFiltro(Prestamo prestamo) throws Exception
    {
        return dao.contarFiltro(prestamo);
    }

    @Override
    public List getUnpaidPrestamos()
    {
        return dao.getUnpaidPrestamos();
    }

    @Override
    public List getListaPrestamosPorCobrar(Prestamo prestamo)
    {

        return dao.getListaPrestamosPorCobrar(prestamo);
    }

    @Override
    public Double getTotalAmount(Prestamo prestamo)
    {
        return dao.getTotalAmount(prestamo);
    }

    @Override
    public List findByClientName(String name)
    {
        return dao.findByClientName(name);
    }

    @Override
    public List<Prestamo> getUnpaidPrestamosFilteredByName(String name)
    {
        return dao.getUnpaidPrestamosFilteredByName(name);
    }

    @Override
    public List getUnpaidAndOverdue()
    {
        return dao.getUnpaidAndOverdue();
    }
}
