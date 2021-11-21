/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.maestros;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.TipoCambio;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author YVAN
 */
public interface TipoCambioDao extends GenericEntityDao<TipoCambio, Serializable>{
    
    public List listarPorFecha(TipoCambio tipoCambio) throws Exception;
    
    public Long getCountLazy(TipoCambio tipoCambio) throws Exception;
    
}
