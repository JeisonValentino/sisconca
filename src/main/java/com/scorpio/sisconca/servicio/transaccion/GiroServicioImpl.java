package com.scorpio.sisconca.servicio.transaccion;

//import com.cjavaperu.scorpio.servicio.seguridad.*;
import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.transaccion.GiroDao;
import com.scorpio.sisconca.entidad.Giro;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Arles
 */
@Service("giroServicio")
public class GiroServicioImpl extends GenericEntityDaoImpl<Giro, Serializable> implements GiroServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    GiroDao dao;


    @Override
    public Giro obtenerPorIdGiro(Giro giro) throws Exception
    {
        return dao.obtenerPorIdGiro(giro);
    }


    @Override
    public List listarPorDescripcionyEstado(Giro giro) throws Exception
    {
        return dao.listarPorDescripcionyEstado(giro);
    }

    @Override
    public int contarPorDescripcionyEstado(Giro giro) throws Exception
    {
        return dao.contarPorDescripcionyEstado(giro);
    }

}
