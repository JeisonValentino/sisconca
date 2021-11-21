package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.DistritoDao;
import com.scorpio.sisconca.entidad.Distrito;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Service("distritoServicio")
public class DistritoServicioImpl
        extends GenericEntityDaoImpl<Distrito, Serializable>
        implements DistritoServicio, Serializable
{
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    DistritoDao dao;

}
