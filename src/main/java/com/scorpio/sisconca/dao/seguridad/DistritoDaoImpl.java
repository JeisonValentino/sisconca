package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Distrito;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Repository("DistritoDao")
public class DistritoDaoImpl extends GenericEntityDaoImpl<Distrito, Serializable> implements DistritoDao
{
    private static final long serialVersionUID = 1L;

}
