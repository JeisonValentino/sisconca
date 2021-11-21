package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Sede;
import java.io.Serializable;
import java.util.List;


public interface SedeDao extends GenericEntityDao<Sede, Serializable>
{

    public List listarPorNombreYEstado(Sede sede) throws Exception;
}
