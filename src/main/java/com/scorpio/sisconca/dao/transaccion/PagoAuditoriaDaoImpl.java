/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.PagoAuditoria;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author YVAN
 */
@Repository("pagoAuditoriaDao")
public class PagoAuditoriaDaoImpl extends GenericEntityDaoImpl<PagoAuditoria, Serializable> implements PagoAuditoriaDao {

    @Override
    public List listarPorFecha(Auditoria auditoria) throws Exception {
        StringBuilder consulta = new StringBuilder();
        Map<String, Object> param = new HashMap<>();
        consulta.append("from PagoAuditoria t where t.id is not null ");

        if (auditoria.getFilterEnd() != null && auditoria.getFilterStart() != null) {
            consulta.append(" and date(t.fechaOperacion)>=:filterStart and date(t.fechaOperacion)<=:filterEnd ");
            param.put("filterStart", auditoria.getFilterStart());
            param.put("filterEnd", auditoria.getFilterEnd());
            System.out.println("Fecha Start, End");
            System.out.println("" + auditoria.getFilterStart());
            System.out.println("" + auditoria.getFilterEnd());
            System.out.println("" + auditoria.getFilterStart());
            System.out.println("" + auditoria.getFilterEnd());
        } else if (auditoria.getFilterEnd() == null && auditoria.getFilterStart() != null) {
            consulta.append(" and date(t.fechaOperacion)>:filterStart ");
            param.put("filterStart", auditoria.getFilterStart());
            System.out.println("FechaStart");

        } else if (auditoria.getFilterEnd() != null && auditoria.getFilterStart() == null) {
            consulta.append(" and date(t.fechaOperacion)<=:filterEnd ");
            param.put("filterEnd", auditoria.getFilterEnd());
            System.out.println("FechaEnd");
        }

        if (auditoria.getSedeId().getId() != null) {
            param.put("sede_id", auditoria.getSedeId().getId());
            consulta.append(" and t.prestamo.idSede.id = :sede_id");
        }

        if (auditoria.getTipoOperacion() != null && auditoria.getTipoOperacion() != "") {
            param.put("tipo_operacion", auditoria.getTipoOperacion());
            consulta.append(" and t.tipoOperacion = :tipo_operacion");
        }

        List lit = listarLazyHQL(consulta.toString(), param, auditoria.getFirst(), auditoria.getPageSize(), null);
        System.out.println("La Lista: " + lit.size()
        );
        return lit;
    }

    @Override
    public Long getCountLazy(Auditoria auditoria) throws Exception {
        StringBuilder consulta = new StringBuilder();
        Map<String, Object> param = new HashMap<>();

        consulta.append("select count(*) from PagoAuditoria t where t.id is not null ");

        if (auditoria.getFilterEnd() != null && auditoria.getFilterStart() != null) {
            consulta.append(" and date(t.fechaOperacion)>=:filterStart and date(t.fechaOperacion)<=:filterEnd ");
            param.put("filterStart", auditoria.getFilterStart());
            param.put("filterEnd", auditoria.getFilterEnd());
            System.out.println("Fecha Start, End");
            System.out.println("" + auditoria.getFilterStart());
            System.out.println("" + auditoria.getFilterEnd());
            System.out.println("" + auditoria.getFilterStart());
            System.out.println("" + auditoria.getFilterEnd());
        } else if (auditoria.getFilterEnd() == null && auditoria.getFilterStart() != null) {
            consulta.append(" and date(t.fechaOperacion)>:filterStart ");
            param.put("filterStart", auditoria.getFilterStart());
            System.out.println("FechaStart");

        } else if (auditoria.getFilterEnd() != null && auditoria.getFilterStart() == null) {
            consulta.append(" and date(t.fechaOperacion)<=:filterEnd ");
            param.put("filterEnd", auditoria.getFilterEnd());
            System.out.println("FechaEnd");
        }

        if (auditoria.getSedeId().getId() != null) {
            param.put("sede_id", auditoria.getSedeId().getId());
            consulta.append(" and t.prestamo.idSede.id = :sede_id");
        }

        if (auditoria.getTipoOperacion() != null && auditoria.getTipoOperacion() != "") {
            param.put("tipo_operacion", auditoria.getTipoOperacion());
            consulta.append(" and t.tipoOperacion = :tipo_operacion");
        }

        Long count = getCountLazy(consulta.toString(), param);
        System.out.println("La Lista_: " + count);

        return count;
    }

}
