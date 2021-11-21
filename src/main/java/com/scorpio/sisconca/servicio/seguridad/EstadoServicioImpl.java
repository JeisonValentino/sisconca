package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.EstadoDao;
import com.scorpio.sisconca.entidad.Estado;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("estadoServicio")
public class EstadoServicioImpl extends GenericEntityDaoImpl<Estado, Serializable> implements EstadoServicio, Serializable
{
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    EstadoDao dao;

    @Override
    public List listarEstadoSolicitud() throws Exception
    {
        return dao.listarEstadoSolicitud();
    }
    
}
