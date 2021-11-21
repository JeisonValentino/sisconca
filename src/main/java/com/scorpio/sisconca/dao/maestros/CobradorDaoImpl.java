/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.maestros;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Cobrador;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository("cobradorDao")
public class CobradorDaoImpl extends GenericEntityDaoImpl<Cobrador, Serializable> implements CobradorDao
{

    @Override
    public Cobrador getByEmployeeId(int id)
    {
        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        query.append("from Cobrador c where c.empleado.id = :id");

        Cobrador c = new Cobrador();

        query = new StringBuilder();
        query.append("select c.id from cobrador c inner join empleado e on c.idEmpleado = e.id where e.id=:id");
        int i = obtenerPorSQL(query.toString(), params, null);
        c = obtenerPorCodigo(i);
        return c;
    }

}
