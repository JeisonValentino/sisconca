/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.servicio.maestros;

import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.EmpleadoAuditoria;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author YVAN
 */
public interface EmpleadoAuditoriaServicio extends GenericService<EmpleadoAuditoria, Serializable>{
    
    public List listarPorFecha(Auditoria auditoria) throws Exception;
    
    public Long getCountLazy(Auditoria auditoria) throws Exception;
    
}
