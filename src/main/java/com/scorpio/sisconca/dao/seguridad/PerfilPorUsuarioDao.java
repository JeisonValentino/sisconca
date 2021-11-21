package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Perfil;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Stewen Jordi Mateo <smateo.cjavaperu@gmail.com>
 */
public interface PerfilPorUsuarioDao extends GenericEntityDao<PerfilPorUsuario, Serializable>
{

    public List<PerfilPorUsuario> obtenerTodosPorUsuario(PerfilPorUsuario perfilPorUsuario) throws Exception;

    public List<PerfilPorUsuario> obtenerTodosPorPerfil(PerfilPorUsuario perfilPorUsuario) throws Exception;
    List<PerfilPorUsuario> getByUserId(int id) throws Exception;
}
