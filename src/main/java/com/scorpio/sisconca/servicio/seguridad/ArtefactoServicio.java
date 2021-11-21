package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.entidad.Artefacto;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

public interface ArtefactoServicio extends GenericService<Artefacto, Serializable>
{

    public List listarPorNombreYEstado(Artefacto artefacto) throws Exception;
}
