/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.seguridad;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Accion;
import com.scorpio.sisconca.entidad.Auditoria;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author YVAN
 */
public interface AuditoriaDao extends GenericEntityDao<Auditoria, Serializable> {
    
    public List listarPorFecha(Auditoria auditoria) throws Exception;
    
    public Long getCountLazy(Auditoria auditoria) throws Exception;

}
