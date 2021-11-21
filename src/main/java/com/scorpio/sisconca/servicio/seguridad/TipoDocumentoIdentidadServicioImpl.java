package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.seguridad.TipoDocumentoIdentidadDao;
import com.scorpio.sisconca.entidad.TipoDocumentoIdentidad;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Service("tipoDocumentoIdentidadServicio")
public class TipoDocumentoIdentidadServicioImpl
        extends GenericEntityDaoImpl<TipoDocumentoIdentidad, Serializable>
        implements TipoDocumentoIdentidadServicio, Serializable
{
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    TipoDocumentoIdentidadDao dao;

}
