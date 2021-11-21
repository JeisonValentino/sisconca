package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.PermisoPorPerfil;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateo.cjavaperu@gmail.com>
 */
public interface PermisoPorPerfilDao extends GenericEntityDao<PermisoPorPerfil, Serializable>
{
    public List<PermisoPorPerfil> obtenerTodoPorPerfil(PermisoPorPerfil permisoPorPerfil);
}
