package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Estado;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateo.cjavaperu@gmail.com>
 */
public interface EstadoDao extends GenericEntityDao<Estado, Serializable>
{
    public List listarEstadoSolicitud() throws Exception;
}
