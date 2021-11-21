package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.entidad.TipoPrestamo;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

public interface TipoPrestamoServicio extends GenericService<TipoPrestamo, Serializable>
{

    public List listarPorNombreYEstado(TipoPrestamo tipoPrestamo) throws Exception;
    
    public int contarFiltro(TipoPrestamo tipoPrestamo) throws Exception;
}
