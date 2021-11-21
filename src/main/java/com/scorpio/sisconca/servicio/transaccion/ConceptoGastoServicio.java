package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.entidad.ConceptoGasto;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arles
 */
public interface ConceptoGastoServicio
        extends GenericService<ConceptoGasto, Serializable>
{

    public List listarPorNombreYEstado(ConceptoGasto conceptoGasto) throws Exception;

    public List listarFiltro(ConceptoGasto conceptoGasto) throws Exception;

    public int contarFiltro(ConceptoGasto conceptoGasto) throws Exception;
}
