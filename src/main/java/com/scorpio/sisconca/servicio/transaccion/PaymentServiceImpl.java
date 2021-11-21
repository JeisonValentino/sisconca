package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.transaccion.PaymentDao;
import com.scorpio.sisconca.entidad.Payment;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alejandro Matos<amatos@gmail.com>
*/
@Service("paymentService")
public class PaymentServiceImpl extends GenericEntityDaoImpl<Payment, Serializable>implements PaymentService
{
    @Autowired
    PaymentDao paymentDao;

    @Override
    public List<Payment> findAll() throws Exception
    {
        return paymentDao.obtenerTodo();
    }

    @Override
    public List<Payment> getPaginated(Payment payment)
    {
        List<Payment> list = paymentDao.paginatedResult(payment);
        return list;
    }

    @Override
    public List<Payment> findByPrestamoId(int id)
    {
        return paymentDao.findByPrestamoId(id);
    }

    @Override
    public int count(Payment payment) throws Exception
    {
        return paymentDao.contarTodo();
    }

    @Override
    public void save(Payment payment) throws Exception
    {
        paymentDao.guardar(payment);
    }

    @Override
    public Double getTotalAmount(Payment payment)
    {
        return paymentDao.getTotalAmount(payment);
    }
}
