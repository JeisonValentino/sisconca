package com.scorpio.sisconca.dao.transaccion;

//import com.cjavaperu.scorpio.dao.seguridad.*;
import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.DatoComercial;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arles
 */
public interface DatoComercialDao extends GenericEntityDao<DatoComercial, Serializable>
{

    public DatoComercial obtenerPorIdDatoComercial(DatoComercial datoComercial) throws Exception;
    
    public DatoComercial obtenerPorIdClienteDatoComercial(int idCliente) throws Exception;

    public List listarPorDescripcionyEstado(DatoComercial datoComercial) throws Exception;

    public int contarPorDescripcionyEstado(DatoComercial datoComercial) throws Exception;
    
}
