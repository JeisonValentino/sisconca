/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.transaccion.ConsultaDao;
import com.scorpio.sisconca.entidad.ConceptoGasto;
import com.scorpio.sisconca.entidad.Consulta;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author YVAN
 */
@Service("consultaServicio")
public class ConsultaServicioImpl extends GenericEntityDaoImpl<Consulta, Serializable>
        implements ConsultaServicio, Serializable
{
    @Autowired
    ConsultaDao dao;
    @Override
    public List getListaPrestamosPorCobrar(Consulta filtro) {
        return dao.getListaPrestamosPorCobrar(filtro);
        
    }

    @Override
    public List getListaRecuperacionUtilidades(Consulta filtro) {
        return dao.getListaRecuperacionUtilidades(filtro);
    }

    @Override
    public List getListaResumenPrestamos(Consulta filtro) {
        return dao.getListaResumenPrestamos(filtro);
        
    }

    @Override
    public List getListaPrestamosVencidos(Consulta filtro) {
        return dao.getListaPrestamosVencidos(filtro);
    }
    
}
