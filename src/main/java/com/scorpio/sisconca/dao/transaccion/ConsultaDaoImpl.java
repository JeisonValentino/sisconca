/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Consulta;
import com.scorpio.sisconca.entidad.Prestamo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.apache.log4j.Logger;

/**
 *
 * @author YVAN
 */
@Repository("consultaDao")
public class ConsultaDaoImpl extends GenericEntityDaoImpl<Consulta, Serializable> implements ConsultaDao
{

    
    private final Logger log = Logger.getLogger(ConsultaDaoImpl.class.getName());
    private static final long serialVersionUID = 1L;
    
    @Override
    public List getListaPrestamosPorCobrar(Consulta filtro) {
        
        
        String nombreCliente;
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();

        paramQuery.put("id_sede", filtro.getFilterIdSede() == null ? 0 : filtro.getFilterIdSede());
        nombreCliente = filtro.getFilterNombreCliente() == null ? "" : filtro.getFilterNombreCliente();
        
        
        consulta.append("SELECT prestamo.`id`,  prestamo.`fecha`,  prestamo.`prestamo`,  prestamo.`cuota`,  prestamo.`monto_pagar`,  prestamo.`id_empleado`,  prestamo.`estado_id`,  ")
                .append("prestamo.`artefacto_id`,  prestamo.`tipo_prestamo_id`,  prestamo.`cliente_id`,  prestamo.`garante`,  prestamo.`frecuencia`,  prestamo.`numeroRenovacion`, ")
                .append("prestamo.`fechaRenovacion`,  prestamo.`indicadorRenovacion`,  prestamo.`interes`,    ")
                .append("(SELECT (CASE WHEN SUM(pago) IS NULL THEN 0 ELSE SUM(pago) END) FROM pago WHERE idPrestamo = prestamo.id) AS pago ")
                .append("FROM scorpio.prestamo,  scorpio.cliente ")
                .append("WHERE prestamo.cliente_id=cliente.id AND (SELECT (CASE WHEN SUM(pago) IS NULL THEN 0 ELSE SUM(pago) END) ")
                .append("FROM pago WHERE idPrestamo = prestamo.id) < scorpio.prestamo.monto_pagar AND scorpio.prestamo.estado_id<>7 AND CONCAT(cliente.nombre, ' ',cliente.apellidoPaterno,' ',cliente.apellidoMaterno) LIKE '%" + nombreCliente + "%' ")
                .append(" AND prestamo.sede_id   like (CASE WHEN :id_sede=0 THEN '%%' ELSE concat('%',:id_sede,'%') END)")
                .append(" ;");
        log.info(consulta.toString());
        return (List<Object>) listarSQL(consulta.toString(), paramQuery, null);
    }

    @Override
    public List getListaRecuperacionUtilidades(Consulta filtro) {
        //String nombreCliente;
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();
        paramQuery.put("id_sede", filtro.getFilterIdSede() == null ? 0 : filtro.getFilterIdSede());

        System.out.println("name:"+filtro.getFilterNombreCliente());
        System.out.println("star:"+filtro.getFilterStart());
        System.out.println("end:"+filtro.getFilterEnd());
        paramQuery.put("star", filtro.getFilterNombreCliente() == null ? "" : filtro.getFilterStart());
        paramQuery.put("end", filtro.getFilterNombreCliente() == null ? "" : filtro.getFilterEnd());
        paramQuery.put("nombre", filtro.getFilterNombreCliente() == null ? "" : "%" +filtro.getFilterNombreCliente()+ "%");
        
        //nombreCliente = filtro.getFilterNombreCliente() == null ? "" : filtro.getFilterNombreCliente();

        consulta.append("SELECT prestamo.id, prestamo.fecha, prestamo.prestamo, prestamo.cuota, prestamo.monto_pagar, prestamo.id_empleado, ")
                .append("prestamo.estado_id, prestamo.artefacto_id, prestamo.tipo_prestamo_id,prestamo.cliente_id,prestamo.frecuencia, ")
                .append("(SELECT (CASE WHEN SUM(pago) IS NULL THEN 0 ELSE SUM(pago) END) FROM pago WHERE pago.idPrestamo = prestamo.id) AS pago ")                
                .append("FROM scorpio.prestamo,  scorpio.cliente ")
                .append("WHERE prestamo.cliente_id=cliente.id AND prestamo.fecha BETWEEN DATE(:star) AND DATE(:end) AND CONCAT(cliente.nombre, ' ',cliente.apellidoPaterno,' ',cliente.apellidoMaterno) LIKE :nombre ")
                .append("AND NOT EXISTS (SELECT * FROM renovacion WHERE renovacion.idPrestamoPorRenovacion=prestamo.id) ")
                .append(" AND prestamo.sede_id   like (CASE WHEN :id_sede=0 THEN '%%' ELSE concat('%',:id_sede,'%') END)")
                .append(" ;")
                ; 
  
        log.info(consulta.toString());
        return (List<Object>) listarSQL(consulta.toString(), paramQuery, null);
    }

    @Override
    public List getListaResumenPrestamos(Consulta filtro) {
        
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();
        paramQuery.put("id_sede", filtro.getFilterIdSede() == null ? 0 : filtro.getFilterIdSede());

        
        System.out.println("star:"+filtro.getFilterStart());
        System.out.println("end:"+filtro.getFilterEnd());
        paramQuery.put("star", filtro.getFilterStart() == null ? "" : filtro.getFilterStart());
        paramQuery.put("end", filtro.getFilterEnd() == null ? "" : filtro.getFilterEnd());
        
        
        
        consulta.append("SELECT prestamo.`fecha`,COUNT(*) cantidad,ROUND(SUM(prestamo.`prestamo`),2) AS prestamo, ROUND(SUM(prestamo.`monto_pagar`),2) AS monto_pagar,ROUND(SUM(prestamo.`monto_pagar`),2)-ROUND(SUM(prestamo.`prestamo`),2) AS interes   ")
                .append("FROM prestamo ")
                .append("WHERE prestamo.fecha BETWEEN DATE(:star) AND DATE(:end) ")
                .append(" AND prestamo.sede_id   like (CASE WHEN :id_sede=0 THEN '%%' ELSE concat('%',:id_sede,'%') END)")
                .append("GROUP BY prestamo.`fecha`; "); 
  
        log.info(consulta.toString());
        return (List<Object>) listarSQL(consulta.toString(), paramQuery, null);
    }

    @Override
    public List getListaPrestamosVencidos(Consulta filtro) {
        //String nombreCliente;
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();
        paramQuery.put("id_sede", filtro.getFilterIdSede() == null ? 0 : filtro.getFilterIdSede());

        System.out.println("name:"+filtro.getFilterNombreCliente());
        System.out.println("zona:"+filtro.getFilterZona());        
        System.out.println("end:"+filtro.getFilterEnd());
        
        paramQuery.put("end", filtro.getFilterEnd() == null ? "" : filtro.getFilterEnd());
        paramQuery.put("zona", filtro.getFilterNombreCliente() == null ? "" : "%" +filtro.getFilterZona()+ "%");
        paramQuery.put("nombre", filtro.getFilterNombreCliente() == null ? "" : "%" +filtro.getFilterNombreCliente()+ "%");
        
        
        consulta.append("SELECT prestamo.id, DATE_ADD(prestamo.fecha, INTERVAL tipo_prestamo.tiempo DAY) fecha,  ")
                .append("CONCAT(cliente.nombre, ' ',cliente.apellidoPaterno,' ',cliente.apellidoMaterno) Nombrecliente,  ")                
                .append("tipo_prestamo.nombre, prestamo.cuota, prestamo.monto_pagar, ")
                .append("(SELECT (CASE WHEN SUM(pago) IS NULL THEN 0 ELSE SUM(pago) END) FROM pago WHERE pago.idPrestamo = prestamo.id) AS pago, ")
                .append("(prestamo.monto_pagar-(SELECT (CASE WHEN SUM(pago) IS NULL THEN 0 ELSE SUM(pago) END) FROM pago WHERE pago.idPrestamo = prestamo.id)) AS saldo, zona_cobranza.nombre AS zona ")
                .append("FROM scorpio.prestamo,  scorpio.cliente, scorpio.zona_cobranza, tipo_prestamo  ")
                .append("WHERE prestamo.cliente_id=cliente.id AND cliente.zona_cobranza_id=zona_cobranza.id AND prestamo.tipo_prestamo_id=tipo_prestamo.id  ")
                .append("AND DATE_ADD(prestamo.fecha, INTERVAL tipo_prestamo.tiempo DAY)<= :end ")
                .append("AND prestamo.estado_id=1 ")
                .append("AND CONCAT(cliente.nombre, ' ',cliente.apellidoPaterno,' ',cliente.apellidoMaterno) LIKE :nombre AND zona_cobranza.nombre LIKE :zona ")
                .append(" AND prestamo.sede_id   like (CASE WHEN :id_sede=0 THEN '%%' ELSE concat('%',:id_sede,'%') END)")
                .append(" ;")
                ;
  
        log.info(consulta.toString());
        return (List<Object>) listarSQL(consulta.toString(), paramQuery, null);
    }
    
}
