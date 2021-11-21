package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.entidad.Perfil;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

public interface PerfilServicio extends GenericService<Perfil, Serializable>
{

    public List listarPorNombreYEstado(Perfil perfil) throws Exception;
}
