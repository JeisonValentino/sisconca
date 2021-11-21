package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Accion;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

@Repository("accionDao")
public class AccionDaoImpl extends GenericEntityDaoImpl<Accion, Serializable> implements AccionDao
{
    private static final long serialVersionUID = 1L;

}
