package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.PerfilDao;
import com.scorpio.sisconca.entidad.Perfil;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("perfilServicio")
public class PerfilServicioImpl extends GenericEntityDaoImpl<Perfil, Serializable> implements PerfilServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    PerfilDao dao;

    @Override
    public List listarPorNombreYEstado(Perfil perfil) throws Exception
    {
        return dao.listarPorNombreYEstado(perfil);
    }

}
