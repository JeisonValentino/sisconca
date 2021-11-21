/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.transaccion.PrestamoAuditoriaDao;
import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.PrestamoAuditoria;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author YVAN
 */
@Service("prestamoAuditoriaServicio")
public class PrestamoAuditoriaServicioImpl 
        extends GenericEntityDaoImpl<PrestamoAuditoria, Serializable> implements PrestamoAuditoriaServicio, Serializable{
    
    private static final long serialVersionUID = 3106162540279643668L;
    
    @Autowired
    PrestamoAuditoriaDao dao;

    @Override
    public List listarPorFecha(Auditoria auditoria) throws Exception {
        return dao.listarPorFecha(auditoria);
    }

    @Override
    public Long getCountLazy(Auditoria auditoria) throws Exception {
        return dao.getCountLazy(auditoria);
    }
    
}
