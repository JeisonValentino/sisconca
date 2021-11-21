package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.transaccion.ConceptoGastoDao;
import com.scorpio.sisconca.entidad.ConceptoGasto;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Arles
 */
@Service("conceptoGastoServicio")
public class ConceptoGastoServicioImpl
        extends GenericEntityDaoImpl<ConceptoGasto, Serializable>
        implements ConceptoGastoServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    ConceptoGastoDao dao;

    @Override
    public List listarPorNombreYEstado(ConceptoGasto conceptoGasto) throws Exception
    {
        return dao.listarPorNombreYEstado(conceptoGasto);
    }

    @Override
    public List listarFiltro(ConceptoGasto conceptoGasto) throws Exception
    {
        return dao.listarFiltro(conceptoGasto);
    }

    @Override
    public int contarFiltro(ConceptoGasto conceptoGasto) throws Exception
    {
        return dao.contarFiltro(conceptoGasto);
    }

}
