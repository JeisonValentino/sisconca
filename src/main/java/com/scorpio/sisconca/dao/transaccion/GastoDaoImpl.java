/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Gasto;
import com.scorpio.sisconca.entidad.Giro;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Max Dicson
 */
@Repository("gastoDao")
public class GastoDaoImpl extends GenericEntityDaoImpl<Gasto, Serializable> implements GastoDao {
    
}
