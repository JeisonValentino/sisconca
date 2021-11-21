package com.scorpio.sisconca.dao.maestros;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Repository("empleadoDao")
public class EmpleadoDaoImpl extends GenericEntityDaoImpl<Empleado, Serializable> implements EmpleadoDao {

    private static final long serialVersionUID = 1L;

    @Override
    public List listarSinUsuario() throws Exception {
        String consulta = "from Empleado e where e.idEstado.id ="
                + EnumEstado.ACTIVO.getId() + " and (select count(id) from Usuario u where u.idEmpleado.id = e.id) = 0"
                + " order by e.idSede.nombre, e.nombre, e.apellidoPaterno, e.apellidoMaterno";
        System.out.println("consulta listarSinUsuario: " + consulta.toString());
        return listarHQL(consulta, null, null);
    }

    @Override
    public List listarFiltro(Empleado empleado) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder consulta = new StringBuilder();
        consulta.append("from Empleado u where u.id is not null");

        if (!"".equals(empleado.getNombre())) {
            consulta.append(" and u.nombre like '%").append(empleado.getNombre()).append("%'");
        }
        if (!"".equals(empleado.getNumeroDocumento())) {
            consulta.append(" and u.numeroDocumento like '%").append(empleado.getNumeroDocumento()).append("%'");
        }
        

        if (null != empleado.getIdEstado().getId()) {
            consulta.append(" and u.idEstado.id =:idEstado");
            parametros.put("idEstado", empleado.getIdEstado().getId());
        }
        if (null != empleado.getIdSede().getId()) {
            consulta.append(" and u.idSede.id =:idSede");
            parametros.put("idSede", empleado.getIdSede().getId());
        }
        consulta.append(" order by u.idSede.nombre,u.nombre, u.apellidoPaterno, u.apellidoMaterno");
        System.out.println("consulta listarFiltro: " + consulta.toString());
        return listarLazyHQL(consulta.toString(), parametros, empleado.getFirst(), empleado.getPageSize(), null);
    }

    @Override
    public int contarFiltro(Empleado empleado) throws Exception {
        StringBuilder consulta = new StringBuilder();
        consulta.append("select count(*) from Empleado u where u.id is not null");
        if (!"".equals(empleado.getNombre())) {
            consulta.append(" and u.nombre like '%").append(empleado.getNombre()).append("%'");
        }
        if (!"".equals(empleado.getNumeroDocumento())) {
            consulta.append(" and u.numeroDocumento like '%").append(empleado.getNumeroDocumento()).append("%'");
        }
        if (null != empleado.getIdEstado().getId()) {
            consulta.append(" and u.idEstado.id =").append(empleado.getIdEstado().getId());
        }
        System.out.println("consulta contarFiltro: " + consulta.toString());
        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }

    @Override
    public List listarSinUsuario(Empleado empleado) throws Exception {
        String consulta = "";
        if (null != empleado.getIdSede().getId()) {
            consulta = consulta 
                    + "from Empleado e where e.idEstado.id ="
                    + EnumEstado.ACTIVO.getId()
                    + " and e.idSede.id="
                    + empleado.getIdSede().getId()
                    + " and (select count(id) from Usuario u where u.idEmpleado.id = e.id) = 0"
                    + " order by e.nombre, e.apellidoPaterno, e.apellidoMaterno";
        } else {
            consulta = consulta 
                    + "from Empleado e where e.idEstado.id ="
                    + EnumEstado.ACTIVO.getId() 
                    + " and (select count(id) from Usuario u where u.idEmpleado.id = e.id) = 0"
                    + " order by e.idSede.nombre, e.nombre, e.apellidoPaterno, e.apellidoMaterno";
        }
        System.out.println("consulta listarSinUsuario: " + consulta.toString());

        return listarHQL(consulta, null, null);
    }

}
