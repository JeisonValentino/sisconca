package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.TipoPersonaDao;
import com.scorpio.sisconca.entidad.TipoPersona;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Service("tipoPersonaServicio")
public class TipoPersonaServicioImpl
        extends GenericEntityDaoImpl<TipoPersona, Serializable>
        implements TipoPersonaServicio, Serializable
{
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    TipoPersonaDao dao;

}
