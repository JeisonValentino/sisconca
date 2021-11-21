package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.PermisoPorPerfilDao;
import com.scorpio.sisconca.entidad.PermisoPorPerfil;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("permisoPorPerfilServicio")
public class PermisoPorPerfilServicioImpl extends GenericEntityDaoImpl<PermisoPorPerfil, Serializable> implements PermisoPorPerfilServicio, Serializable
{
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    PermisoPorPerfilDao dao;

    @Override
    public List<PermisoPorPerfil> obtenerTodoPorPerfil(PermisoPorPerfil permisoPorPerfil)
    {
        return dao.obtenerTodoPorPerfil(permisoPorPerfil);
    }

}
