package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.dao.transaccion.RenewalDao;
import com.scorpio.sisconca.entidad.Renewal;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alejandro Matos<amatos@gmail.com>
*/
@Service("renewalService")
public class RenewalServiceImpl  extends GenericEntityDaoImpl<Renewal, Serializable> implements RenewalService
{
    @Autowired
    RenewalDao dao;
    
    @Override
    public List<Renewal> getPaginatedResult(Renewal renewal)
    {
        return dao.getPaginatedResult(renewal);
    }

    @Override
    public double getTotalAmount(Renewal renewal)
    {
        return dao.getTotalAmount(renewal);
    }

    @Override
    public int save(Renewal renewal)
    {
        return dao.save(renewal);
    }
}
