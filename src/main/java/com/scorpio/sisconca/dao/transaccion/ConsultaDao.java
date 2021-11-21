/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Consulta;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author YVAN
 */
public interface ConsultaDao extends GenericEntityDao<Consulta, Serializable>
{
    List getListaPrestamosPorCobrar(Consulta filtro);
    
    List getListaRecuperacionUtilidades(Consulta filtro);
    List getListaResumenPrestamos(Consulta filtro);
    List getListaPrestamosVencidos(Consulta filtro);
    
}
