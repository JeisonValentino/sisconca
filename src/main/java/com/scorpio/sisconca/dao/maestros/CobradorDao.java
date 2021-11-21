/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.maestros;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Cobrador;
import java.io.Serializable;

/**
 *
 * @author alejandro
 */
public interface CobradorDao extends GenericEntityDao<Cobrador, Serializable>
{
    Cobrador getByEmployeeId(int id);
}
