/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.servicio.maestros;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.maestros.TipoCambioDao;
import com.scorpio.sisconca.entidad.TipoCambio;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author YVAN
 */
@Service("tipoCambioServicio")
public class TipoCambioServicioImpl extends GenericEntityDaoImpl<TipoCambio, Serializable>implements TipoCambioServicio, Serializable{
    
    private static final long serialVersionUID = 3106162540279643668L;

    @Autowired
    TipoCambioDao dao;

    @Override
    public List listarPorFecha(TipoCambio tipoCambio) throws Exception {
        return dao.listarPorFecha(tipoCambio);
    }

    @Override
    public Long getCountLazy(TipoCambio tipoCambio) throws Exception {
        return dao.getCountLazy(tipoCambio);
    }
    
}
