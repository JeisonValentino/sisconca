package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.AccionDao;
import com.scorpio.sisconca.entidad.Accion;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accionServicio")
public class AccionServicioImpl extends GenericEntityDaoImpl<Accion, Serializable> implements AccionServicio, Serializable
{
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    AccionDao dao;

}
