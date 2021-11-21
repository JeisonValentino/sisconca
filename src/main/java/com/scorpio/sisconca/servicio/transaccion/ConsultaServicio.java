/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.entidad.Consulta;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author YVAN
 */
public interface ConsultaServicio extends GenericService<Consulta, Serializable>{
    
    public List getListaPrestamosPorCobrar(Consulta filtro);
    public List getListaRecuperacionUtilidades(Consulta filtro);
    public List getListaResumenPrestamos(Consulta filtro);
    public List getListaPrestamosVencidos(Consulta filtro);
    
}
