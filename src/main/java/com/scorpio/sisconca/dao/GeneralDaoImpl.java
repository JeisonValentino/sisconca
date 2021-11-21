package com.scorpio.sisconca.dao;

import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stewen Mateo <smateovj@gmail.com>
 */
@Repository("generalDao")
public class GeneralDaoImpl extends GenericEntityDaoImpl<Object, Serializable> implements GeneralDao
{
    private static final long serialVersionUID = -3774208873832984995L;
    

}
