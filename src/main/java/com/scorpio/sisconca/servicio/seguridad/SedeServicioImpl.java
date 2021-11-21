package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.SedeDao;
import com.scorpio.sisconca.entidad.Sede;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sedeServicio")
public class SedeServicioImpl extends GenericEntityDaoImpl<Sede, Serializable> implements SedeServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    SedeDao dao;

    @Override
    public List listarPorNombreYEstado(Sede sede) throws Exception
    {
        return dao.listarPorNombreYEstado(sede);
    }

}
