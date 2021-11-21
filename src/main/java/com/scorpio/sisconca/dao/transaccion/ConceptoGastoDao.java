package com.scorpio.sisconca.dao.transaccion;

//import com.cjavaperu.scorpio.dao.maestros.*;
import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.ConceptoGasto;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arles
 */
public interface ConceptoGastoDao extends GenericEntityDao<ConceptoGasto, Serializable>
{

    public List listarPorNombreYEstado(ConceptoGasto conceptoGasto) throws Exception;

    public List listarFiltro(ConceptoGasto conceptoGasto) throws Exception;

    public int contarFiltro(ConceptoGasto conceptoGasto) throws Exception;
}
