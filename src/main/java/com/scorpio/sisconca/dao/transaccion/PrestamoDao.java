package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Prestamo;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
public interface PrestamoDao extends GenericEntityDao<Prestamo, Serializable>
{

    public List listarPorNombreYEstado() throws Exception;

    public List listarFiltro(Prestamo prestamo) throws Exception;

    public int contarFiltro(Prestamo prestamo) throws Exception;

    List getUnpaidPrestamos();

    List getListaPrestamosPorCobrar(Prestamo prestamo);

    Double getTotalAmount(Prestamo prestamo);

    List findByClientName(String name);

    List getUnpaidPrestamosFilteredByName(String name);

    List getUnpaidAndOverdue();

}
