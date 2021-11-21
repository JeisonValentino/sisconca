package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.entidad.PermisoPorPerfil;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

public interface PermisoPorPerfilServicio extends GenericService<PermisoPorPerfil, Serializable>
{
    public List<PermisoPorPerfil> obtenerTodoPorPerfil(PermisoPorPerfil permisoPorPerfil);
}
