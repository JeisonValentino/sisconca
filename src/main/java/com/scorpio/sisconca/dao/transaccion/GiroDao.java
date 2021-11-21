package com.scorpio.sisconca.dao.transaccion;

//import com.cjavaperu.scorpio.dao.seguridad.*;
import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Giro;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arles
 */
public interface GiroDao extends GenericEntityDao<Giro, Serializable>
{

    public Giro obtenerPorIdGiro(Giro giro) throws Exception;

    public List listarPorDescripcionyEstado(Giro giro) throws Exception;

    public int contarPorDescripcionyEstado(Giro giro) throws Exception;
}
