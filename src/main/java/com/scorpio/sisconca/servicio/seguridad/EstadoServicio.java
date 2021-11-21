package com.scorpio.sisconca.servicio.seguridad;

import com.scorpio.sisconca.entidad.Estado;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

public interface EstadoServicio extends GenericService<Estado, Serializable>
{
    
    public List listarEstadoSolicitud() throws Exception;
    
}
