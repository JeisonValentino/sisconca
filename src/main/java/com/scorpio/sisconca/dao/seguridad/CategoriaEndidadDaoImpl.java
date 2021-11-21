package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.CategoriaEntidad;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Repository("categoriaEntidadDao")
public class CategoriaEndidadDaoImpl extends GenericEntityDaoImpl<CategoriaEntidad, Serializable> implements CategoriaEntidadDao
{
    private static final long serialVersionUID = 1L;

}
