package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.TipoPersona;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Repository("tipoPersonaDao")
public class TipoPersonaDaoImpl extends GenericEntityDaoImpl<TipoPersona, Serializable> implements TipoPersonaDao
{
    private static final long serialVersionUID = 1L;

}
