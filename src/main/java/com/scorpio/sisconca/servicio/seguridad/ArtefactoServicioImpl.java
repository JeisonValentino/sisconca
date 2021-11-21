package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.ArtefactoDao;
import com.scorpio.sisconca.entidad.Artefacto;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("artefactoServicio")
public class ArtefactoServicioImpl extends GenericEntityDaoImpl<Artefacto, Serializable> implements ArtefactoServicio, Serializable
{

    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    ArtefactoDao dao;

    @Override
    public List listarPorNombreYEstado(Artefacto artefacto) throws Exception
    {
        return dao.listarPorNombreYEstado(artefacto);
    }

}
