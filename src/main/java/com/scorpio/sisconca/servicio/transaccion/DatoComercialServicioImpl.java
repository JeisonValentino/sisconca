package com.scorpio.sisconca.servicio.transaccion;

//import com.cjavaperu.scorpio.servicio.seguridad.*;
import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.transaccion.DatoComercialDao;
import com.scorpio.sisconca.entidad.DatoComercial;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Arles
 */
@Service("datoComercialServicio")
public class DatoComercialServicioImpl extends GenericEntityDaoImpl<DatoComercial, Serializable> implements DatoComercialServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    DatoComercialDao dao;


    @Override
    public DatoComercial obtenerPorIdDatoComercial(DatoComercial datoComercial) throws Exception
    {
        return dao.obtenerPorIdDatoComercial(datoComercial);
    }


    @Override
    public List listarPorDescripcionyEstado(DatoComercial datoComercial) throws Exception
    {
        return dao.listarPorDescripcionyEstado(datoComercial);
    }

    @Override
    public int contarPorDescripcionyEstado(DatoComercial datoComercial) throws Exception
    {
        return dao.contarPorDescripcionyEstado(datoComercial);
    }

    @Override
    public DatoComercial obtenerPorIdClienteDatoComercial(int idCliente) throws Exception {
        return dao.obtenerPorIdClienteDatoComercial(idCliente);
    }
}
