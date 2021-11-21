package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.entidad.DatoComercial;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arles
 */
public interface DatoComercialServicio extends GenericService<DatoComercial, Serializable>
{
    public DatoComercial obtenerPorIdDatoComercial(DatoComercial datoComercial) throws Exception;
    
    public DatoComercial obtenerPorIdClienteDatoComercial(int idCliente) throws Exception;

    public List listarPorDescripcionyEstado(DatoComercial datoComercial) throws Exception;

    public int contarPorDescripcionyEstado(DatoComercial datoComercial) throws Exception;
    
}
