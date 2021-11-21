/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.servicio.maestros;

import com.scorpio.sisconca.entidad.TipoCambio;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author YVAN
 */
public interface TipoCambioServicio extends GenericService<TipoCambio, Serializable>{
    
    public List listarPorFecha(TipoCambio tipoCambio) throws Exception;
    
    public Long getCountLazy(TipoCambio tipoCambio) throws Exception;
}
