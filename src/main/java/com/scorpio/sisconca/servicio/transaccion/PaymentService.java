/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.entidad.Payment;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author alejandro
 */
public interface PaymentService extends GenericService<Payment, Serializable>
{

    List<Payment> findAll() throws Exception;

    List<Payment> getPaginated(Payment payment);

    List<Payment> findByPrestamoId(int id);

    int count(Payment payment) throws Exception;

    void save(Payment payment) throws Exception;

    Double getTotalAmount(Payment payment);
}
