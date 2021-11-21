package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Entidad;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

@Repository("entidadDao")
public class EntidadDaoImpl extends GenericEntityDaoImpl<Entidad, Serializable> implements EntidadDao
{
    private static final long serialVersionUID = 1L;

}
