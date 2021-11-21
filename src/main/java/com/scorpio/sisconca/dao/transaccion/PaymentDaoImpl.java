/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Payment;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository("paymentDao")
public class PaymentDaoImpl extends GenericEntityDaoImpl<Payment, Serializable> implements PaymentDao
{

    @Override
    public List paginatedResult(Payment payment)
    {
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder query = new StringBuilder();
        query.append("from Payment p where p.id is not null");
        if (payment.getPrestamo().getIdCliente().getId() != null)
        {
            paramQuery.put("client_id", payment.getPrestamo().getIdCliente().getId());
            query.append(" and p.prestamo.idCliente.id = :client_id");
        }
        
        if (payment.getPrestamo().getIdSede().getId() != null)
        {
            paramQuery.put("sede_id", payment.getPrestamo().getIdSede().getId());
            query.append(" and p.prestamo.idSede.id = :sede_id");
        }
        
        if (payment.getStart() != null && payment.getEnd() != null)
        {
            paramQuery.put("start", payment.getStart());
            paramQuery.put("end", payment.getEnd());
            query.append(" and p.fechaPago between :start and :end");
        }
        query.append(" order by p.prestamo.idCliente.apellidoPaterno, p.prestamo.idCliente.apellidoMaterno, p.prestamo.id ASC");
        return listarLazyHQL(query.toString(), paramQuery, payment.getFirst(), payment.getPageSize(), null);
    }

    @Override
    public List<Payment> findByPrestamoId(int id)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        StringBuilder query = new StringBuilder();
        query.append("from Payment p where p.prestamo.id = :id");
        return listarHQL(query.toString(), params, null);
    }

    @Override
    public Double getTotalAmount(Payment payment)
    {
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder query = new StringBuilder();
        query.append("select sum(p.pago) from Payment p where p.id is not null");
        if (payment.getPrestamo().getIdCliente().getId() != null)
        {
            paramQuery.put("client_id", payment.getPrestamo().getIdCliente().getId());
            query.append(" and p.prestamo.idCliente.id = :client_id");
        }
        if (payment.getPrestamo().getIdSede().getId() != null)
        {
            paramQuery.put("sede_id", payment.getPrestamo().getIdSede().getId());
            query.append(" and p.prestamo.idSede.id = :sede_id");
        }
        if (payment.getStart() != null && payment.getEnd() != null)
        {
            paramQuery.put("start", payment.getStart());
            paramQuery.put("end", payment.getEnd());
            query.append(" and p.fechaPago between :start and :end");
        }
        System.out.println("Consulta de getTotalAmount:"+query.toString());
        //System.out.println("getTotalAmount:"+obtenerPorHQL(query.toString(), paramQuery, null).toString());
        try{
        	return Double.valueOf(0.00);
            //System.out.println("getTotalAmount:"+obtenerPorHQL(query.toString(), paramQuery, null).toString());            
            //return Double.valueOf(obtenerPorHQL(query.toString(), paramQuery, null).toString());
        }catch(Exception e){
            System.out.println("getTotalAmount - Query no ejecutado "+e.toString());
            return Double.valueOf(0.00);
        }
        
    }

}
