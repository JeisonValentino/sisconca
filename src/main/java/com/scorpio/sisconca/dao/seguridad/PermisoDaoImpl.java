package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Permiso;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

@Repository("permisoDao")
public class PermisoDaoImpl extends GenericEntityDaoImpl<Permiso, Serializable> implements PermisoDao
{
    private static final long serialVersionUID = 1L;

}
