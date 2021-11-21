package com.scorpio.sisconca.servicio.transaccion;

import com.scorpio.sisconca.entidad.Renewal;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;
import java.util.List;

/**
 * @author Alejandro Matos<amatos@gmail.com>
 */
public interface RenewalService extends GenericService<Renewal, Serializable>
{

    List<Renewal> getPaginatedResult(Renewal renewal);

    double getTotalAmount(Renewal renewal);

    int save(Renewal renewal);

}
