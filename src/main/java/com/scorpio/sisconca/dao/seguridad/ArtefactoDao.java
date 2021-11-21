package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Artefacto;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateo.cjavaperu@gmail.com>
 */
public interface ArtefactoDao extends GenericEntityDao<Artefacto, Serializable>
{
    public List listarPorNombreYEstado(Artefacto artefacto) throws Exception;
}
