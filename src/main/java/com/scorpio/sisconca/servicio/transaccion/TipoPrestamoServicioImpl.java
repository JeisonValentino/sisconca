package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.transaccion.TipoPrestamoDao;
import com.scorpio.sisconca.entidad.TipoPrestamo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tipoPrestamoServicio")
public class TipoPrestamoServicioImpl extends GenericEntityDaoImpl<TipoPrestamo, Serializable> implements TipoPrestamoServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    TipoPrestamoDao dao;

    @Override
    public List listarPorNombreYEstado(TipoPrestamo tipoPrestamo) throws Exception
    {
        return dao.listarPorNombreYEstado(tipoPrestamo);
    }

    @Override
    public int contarFiltro(TipoPrestamo tipoPrestamo) throws Exception
    {
        return dao.contarFiltro(tipoPrestamo);
    }

}
