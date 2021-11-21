package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Perfil;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateo.cjavaperu@gmail.com>
 */
public interface PerfilDao extends GenericEntityDao<Perfil, Serializable>
{

    public List listarPorNombreYEstado(Perfil perfil) throws Exception;
}
