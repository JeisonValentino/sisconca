/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.servicio.maestros;

import com.scorpio.sisconca.entidad.Cobrador;
import com.scorpio.sisconca.servicio.GenericService;
import java.io.Serializable;

/**
 *
 * @author alejandro
 */
public interface CobradorService extends GenericService<Cobrador, Serializable>
{
    Cobrador getByEmployeeId(int id);
}
