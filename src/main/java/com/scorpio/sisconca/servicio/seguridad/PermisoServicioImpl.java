package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.PermisoDao;
import com.scorpio.sisconca.entidad.Permiso;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("permisoServicio")
public class PermisoServicioImpl extends GenericEntityDaoImpl<Permiso, Serializable> implements PermisoServicio, Serializable
{
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    PermisoDao dao;

}
