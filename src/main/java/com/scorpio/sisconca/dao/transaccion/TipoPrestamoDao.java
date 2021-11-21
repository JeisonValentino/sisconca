package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.TipoPrestamo;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateo.cjavaperu@gmail.com>
 */
public interface TipoPrestamoDao extends GenericEntityDao<TipoPrestamo, Serializable>
{

    public List listarPorNombreYEstado(TipoPrestamo tipoPrestamo) throws Exception;
    
    public int contarFiltro(TipoPrestamo tipoPrestamo)throws Exception;
}
