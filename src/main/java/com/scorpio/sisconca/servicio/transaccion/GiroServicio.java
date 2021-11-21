package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.entidad.Giro;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arles
 */
public interface GiroServicio extends GenericService<Giro, Serializable>
{
    public Giro obtenerPorIdGiro(Giro giro) throws Exception;

    public List listarPorDescripcionyEstado(Giro giro) throws Exception;

    public int contarPorDescripcionyEstado(Giro giro) throws Exception;
}
