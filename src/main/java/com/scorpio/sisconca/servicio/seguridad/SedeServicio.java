package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.entidad.Sede;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

public interface SedeServicio extends GenericService<Sede, Serializable>
{

    public List listarPorNombreYEstado(Sede sede) throws Exception;
}
