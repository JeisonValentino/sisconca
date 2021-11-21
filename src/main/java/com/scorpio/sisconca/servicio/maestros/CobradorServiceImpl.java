/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.servicio.maestros;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.maestros.CobradorDao;
import com.scorpio.sisconca.entidad.Cobrador;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cobradorService")
public class CobradorServiceImpl extends GenericEntityDaoImpl<Cobrador, Serializable>implements CobradorService
{
    @Autowired
    CobradorDao dao;
    
    @Override
    public Cobrador getByEmployeeId(int id)
    {
        return dao.getByEmployeeId(id);
    }

}
