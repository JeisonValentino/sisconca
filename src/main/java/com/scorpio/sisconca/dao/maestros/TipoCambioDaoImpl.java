/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.maestros;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.TipoCambio;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author YVAN
 */
@Repository("tipoCambioDao")
public class TipoCambioDaoImpl extends GenericEntityDaoImpl<TipoCambio, Serializable> implements TipoCambioDao{
    
    private static final long serialVersionUID = 1L;

    @Override
    public List listarPorFecha(TipoCambio tipoCambio) throws Exception {
        StringBuilder consulta = new StringBuilder();
        Map<String, Object> param = new HashMap<>();
        
        if(tipoCambio.getFilterEnd() != null && tipoCambio.getFilterStart() != null) {
            consulta.append("from TipoCambio t where t.fecha>=:filterStart and t.fecha<=:filterEnd order by t.fecha desc");
            param.put("filterStart", tipoCambio.getFilterStart());
            param.put("filterEnd", tipoCambio.getFilterEnd());
            System.out.println("Fecha Start, End");
        } else if(tipoCambio.getFilterEnd() == null && tipoCambio.getFilterStart() != null) {
            consulta.append("from TipoCambio t where t.fecha>:filterStart order by t.fecha desc");
            param.put("filterStart", tipoCambio.getFilterStart());
            System.out.println("FechaStart");
        } else if(tipoCambio.getFilterEnd() != null && tipoCambio.getFilterStart() == null) {
            consulta.append("from TipoCambio t where t.fecha<=:filterEnd order by t.fecha desc");
            param.put("filterEnd", tipoCambio.getFilterEnd());
            System.out.println("FEchaEnd");
        } else {
            consulta.append("from TipoCambio t");
        
        }
        
        System.out.println("DAODAODAODAODAODAO1");
        System.out.println(tipoCambio.getFirst());
        System.out.println(tipoCambio.getPageSize());
        System.out.println(tipoCambio.getFilterStart());
        System.out.println(tipoCambio.getFilterEnd());
        System.out.println("DAODAODAODAODAODAO1");
        
        List lit = listarLazyHQL(consulta.toString(), param, tipoCambio.getFirst(), tipoCambio.getPageSize(),null);
        System.out.println("La Lista: "+ lit.size()
        );
        return lit;
        
    }

    @Override
    public Long getCountLazy(TipoCambio tipoCambio) throws Exception {
        StringBuilder consulta = new StringBuilder();
        Map<String, Object> param = new HashMap<>();
        
        if(tipoCambio.getFilterEnd() != null && tipoCambio.getFilterStart() != null) {
            consulta.append("select count(*) from TipoCambio t where t.fecha>=:filterStart and t.fecha<=:filterEnd order by t.fecha desc");
            param.put("filterStart", tipoCambio.getFilterStart());
            param.put("filterEnd", tipoCambio.getFilterEnd());
        } else if(tipoCambio.getFilterEnd() == null && tipoCambio.getFilterStart() != null) {
            consulta.append("select count(*) from TipoCambio t where t.fecha>:filterStart order by t.fecha desc");
            param.put("filterStart", tipoCambio.getFilterStart());
        } else if(tipoCambio.getFilterEnd() != null && tipoCambio.getFilterStart() == null) {
            consulta.append("select count(*) from TipoCambio t where t.fecha<=:filterEnd order by t.fecha desc");
            param.put("filterEnd", tipoCambio.getFilterEnd());
        } else {
            consulta.append("select count(*) from TipoCambio t");
        }
        
        Long count = getCountLazy(consulta.toString(), param);
        System.out.println("La Lista: "+ count);
        
        return count;
    }
    
    
}
