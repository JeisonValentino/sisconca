package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.EntidadDao;
import com.scorpio.sisconca.entidad.Entidad;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("entidadServicio")
public class EntidadServicioImpl extends GenericEntityDaoImpl<Entidad, Serializable> implements EntidadServicio, Serializable
{
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    EntidadDao dao;

}
