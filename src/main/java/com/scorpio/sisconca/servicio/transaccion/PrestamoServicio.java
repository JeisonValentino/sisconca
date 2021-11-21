package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.entidad.Prestamo;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
public interface PrestamoServicio extends GenericService<Prestamo, Serializable>
{

    public List listarPorNombreYEstado() throws Exception;

    public List listarFiltro(Prestamo prestamo) throws Exception;

    public int contarFiltro(Prestamo prestamo) throws Exception;

    List getUnpaidPrestamos();
    
    List getListaPrestamosPorCobrar(Prestamo prestamo);
    
    Double getTotalAmount(Prestamo prestamo);
    
    List findByClientName(String name);
    
    List<Prestamo> getUnpaidPrestamosFilteredByName(String name);
    
    List getUnpaidAndOverdue();
}
