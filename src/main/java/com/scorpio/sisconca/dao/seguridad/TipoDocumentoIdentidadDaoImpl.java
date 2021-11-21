package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.TipoDocumentoIdentidad;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Repository("tipoDocumentoIdentidadDao")
public class TipoDocumentoIdentidadDaoImpl extends GenericEntityDaoImpl<TipoDocumentoIdentidad, Serializable> implements TipoDocumentoIdentidadDao
{
    private static final long serialVersionUID = 1L;

}
