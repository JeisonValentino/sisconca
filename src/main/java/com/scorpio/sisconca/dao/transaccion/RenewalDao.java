/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Renewal;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author alejandro
 */
public interface RenewalDao extends GenericEntityDao<Renewal, Serializable>
{

    List<Renewal> getPaginatedResult(Renewal renewal);

    double getTotalAmount(Renewal renewal);

    int save(Renewal renewal);
}
