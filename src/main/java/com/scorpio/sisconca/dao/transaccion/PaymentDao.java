/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDao;
import com.scorpio.sisconca.entidad.Payment;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author alejandro
 */
public interface PaymentDao extends GenericEntityDao<Payment, Serializable>
{
    List paginatedResult(Payment payment);
    List<Payment> findByPrestamoId(int id);

    public Double getTotalAmount(Payment payment);
}
