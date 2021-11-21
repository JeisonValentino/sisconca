package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.CategoriaEntidadDao;
import com.scorpio.sisconca.entidad.CategoriaEntidad;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Service("categoriaEntidadServicio")
public class CategoriaEntidadServicioImpl
        extends GenericEntityDaoImpl<CategoriaEntidad, Serializable>
        implements CategoriaEntidadServicio, Serializable
{
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    CategoriaEntidadDao dao;

}
