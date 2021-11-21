package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.TipoVivienda;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Repository("tipoViviendaDao")
public class TipoViviendaDaoImpl extends GenericEntityDaoImpl<TipoVivienda, Serializable> implements TipoViviendaDao
{
    private static final long serialVersionUID = 1L;

}
