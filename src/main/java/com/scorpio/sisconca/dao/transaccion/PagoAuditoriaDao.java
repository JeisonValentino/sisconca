/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.PagoAuditoria;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author YVAN
 */
public interface PagoAuditoriaDao extends GenericEntityDao<PagoAuditoria, Serializable> {
    
    public List listarPorFecha(Auditoria auditoria) throws Exception;
    
    public Long getCountLazy(Auditoria auditoria) throws Exception;
    
    
    
}
