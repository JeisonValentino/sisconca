package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.TipoViviendaDao;
import com.scorpio.sisconca.entidad.TipoVivienda;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Service("tipoViviendaServicio")
public class TipoViviendaServicioImpl
        extends GenericEntityDaoImpl<TipoVivienda, Serializable>
        implements TipoViviendaServicio, Serializable
{
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    TipoViviendaDao dao;

}
