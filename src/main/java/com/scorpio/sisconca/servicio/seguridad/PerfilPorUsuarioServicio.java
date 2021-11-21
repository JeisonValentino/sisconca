package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

public interface PerfilPorUsuarioServicio extends GenericService<PerfilPorUsuario, Serializable>
{

    public List<PerfilPorUsuario> obtenerTodosPorUsuario(PerfilPorUsuario perfilPorUsuario) throws Exception;

    public List<PerfilPorUsuario> obtenerTodosPorPerfil(PerfilPorUsuario perfilPorUsuario) throws Exception;
    
    List<PerfilPorUsuario> getByUserId(int id) throws Exception;
}
