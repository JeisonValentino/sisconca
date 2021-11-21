package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Estado;
import com.scorpio.sisconca.entidad.Prestamo;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.apache.log4j.Logger;

/**
 *
 * @author Stewen Jordi Mateo <smateovj@gmail.com>
 */
@Repository("prestamoDao")
public class PrestamoDaoImpl extends GenericEntityDaoImpl<Prestamo, Serializable> implements PrestamoDao
{

    private final Logger log = Logger.getLogger(PrestamoDaoImpl.class.getName());
    private static final long serialVersionUID = 1L;

    @Override
    // TODO: no estás filtrando por nombre, se debería cambiar el nombre del método.
    public List listarPorNombreYEstado() throws Exception
    {
        String consulta = "from Prestamo e ";
        return listarHQL(consulta, null, null);
    }

    @Override
    public List listarFiltro(Prestamo prestamo) throws Exception
    {
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();
        consulta.append("from Prestamo u where u.id is not null");

        if (prestamo.getFilterStart() != null && prestamo.getFilterEnd() != null)
        {
            paramQuery.put("start", prestamo.getFilterStart());
            paramQuery.put("end", prestamo.getFilterEnd());
            consulta.append(" and u.fecha between :start and :end");
        }
        if (prestamo.getIdCliente().getId() != null)
        {
            paramQuery.put("client_id", prestamo.getIdCliente().getId());
            consulta.append(" and u.idCliente.id = :client_id");
        }
        
        if(prestamo.getFilterEstado()!=null){
            if(prestamo.getFilterEstado()!=0){        
                    paramQuery.put("estado_id", prestamo.getFilterEstado());
                    consulta.append(" and u.idEstado.id = :estado_id ");
            }
            
        }
        if (prestamo.getIdSede().getId() != null)
        {
            paramQuery.put("sede_id", prestamo.getIdSede().getId());
            consulta.append(" and u.idSede.id = :sede_id");
        }
        
        
        if (prestamo.getIdEmpleado().getId() != null)
        {
            paramQuery.put("empleado_id", prestamo.getIdEmpleado().getId());
            consulta.append(" and u.idEmpleado.id = :empleado_id");
        }
        System.out.println("**********************"+paramQuery.toString());
        System.out.println("**********************"+consulta.toString());
        if (!paramQuery.isEmpty())
        {
            return listarLazyHQL(consulta.toString(), paramQuery, prestamo.getFirst(), prestamo.getPageSize(), null);
        }
        return listarLazyHQL(consulta.toString(), null, prestamo.getFirst(), prestamo.getPageSize(), null);
    }

    @Override
    public List findByClientName(String name)
    {
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();
        consulta.append("from Prestamo u where u.id is not null");

        paramQuery.put("name", "%" + name + "%");
        consulta.append(" and (u.idCliente.nombre like :name")
                .append(" or u.idCliente.apellidoPaterno like :name")
                .append(" or u.idCliente.apellidoMaterno like :name)");
        return (List<Prestamo>) listarHQL(consulta.toString(), paramQuery, null);
    }

    @Override
    public int contarFiltro(Prestamo prestamo) throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("select count(*) from Prestamo u where u.id is not null");
        
        
        Map<String, Object> paramQuery = new HashMap<>();
        

        if (prestamo.getFilterStart() != null && prestamo.getFilterEnd() != null)
        {
            paramQuery.put("start", prestamo.getFilterStart());
            paramQuery.put("end", prestamo.getFilterEnd());
            consulta.append(" and u.fecha between :start and :end");
        }
        if (prestamo.getIdCliente().getId() != null)
        {
            paramQuery.put("client_id", prestamo.getIdCliente().getId());
            consulta.append(" and u.idCliente.id = :client_id");
        }
        if (prestamo.getIdSede().getId() != null)
        {
            paramQuery.put("sede_id", prestamo.getIdSede().getId());
            consulta.append(" and u.idSede.id = :sede_id");
        }
        
        if(prestamo.getFilterEstado()!=null){
            if(prestamo.getFilterEstado()!=0){        
                    paramQuery.put("estado_id", prestamo.getFilterEstado());
                    consulta.append(" and u.idEstado.id = :estado_id ");
            }
            
        }
        
        if (prestamo.getIdEmpleado().getId() != null)
        {
            paramQuery.put("empleado_id", prestamo.getIdEmpleado().getId());
            consulta.append(" and u.idEmpleado.id = :empleado_id");
        }
        
        if (!paramQuery.isEmpty())
        {
            return Integer.parseInt(obtenerPorHQL(consulta.toString(), paramQuery, null).toString());
        }
        
        
        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }

    @Override
    public List<Prestamo> getUnpaidPrestamos()
    {
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();
        consulta.append("SELECT prestamo.`id`,  prestamo.`fecha`,  prestamo.`prestamo`,  prestamo.`cuota`,  prestamo.`monto_pagar`,  prestamo.`id_empleado`,  prestamo.`estado_id`,  ")
                .append("prestamo.`artefacto_id`,  prestamo.`tipo_prestamo_id`,  prestamo.`cliente_id`,  prestamo.`garante`,  prestamo.`frecuencia`,  prestamo.`numeroRenovacion`, ")
                .append("prestamo.`fechaRenovacion`,  prestamo.`indicadorRenovacion`,  prestamo.`interes`,    ")
                .append(" (select (case when SUM(pago) is null then 0 else sum(pago) end) ")
                .append("from pago where idPrestamo = prestamo.id) as pago ")
                .append("FROM scorpio.prestamo ")
                .append("inner join tipo_prestamo on prestamo.tipo_prestamo_id = tipo_prestamo.id ")
                .append("where prestamo.estado_id in ( ")
                .append(EnumEstado.ACTIVO.getId())
                .append(") ")
                ;
                //.append("where (select (case when SUM(pago) is null then 0 else sum(pago) end) ")
                //.append("from pago where idPrestamo = p.id) < p.monto_pagar ")
                //.append( "and DATEDIFF(DATE_ADD(`fecha`, INTERVAL (select tiempo from tipo_prestamo where id=tp.id) DAY), NOW())<=0 and p.estado_id not in( ")
                //.append(EnumEstado.RENOVADO.getId())
                //.append(", ")
                //.append(EnumEstado.CANCELADO.getId())
                //.append(")")
                //.append(";");
        return (List<Prestamo>) listarSQL(consulta.toString(), paramQuery, null);
    }

    @Override
    public List getUnpaidPrestamosFilteredByName(String name)
    {
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();
        consulta.append("SELECT prestamo.`id`,  prestamo.`fecha`,  prestamo.`prestamo`,  prestamo.`cuota`,  prestamo.`monto_pagar`,  prestamo.`id_empleado`,  prestamo.`estado_id`,  ")
                .append("prestamo.`artefacto_id`,  prestamo.`tipo_prestamo_id`,  prestamo.`cliente_id`,  prestamo.`garante`,  prestamo.`frecuencia`,  prestamo.`numeroRenovacion`, ")
                .append("prestamo.`fechaRenovacion`,  prestamo.`indicadorRenovacion`,  prestamo.`interes`,    ")
                .append(" (select (case when SUM(pago) is null then 0 else sum(pago) end) ")
                .append("from pago where idPrestamo = prestamo.id) as pago ")
                .append("FROM scorpio.prestamo ")
                .append("where (select (case when SUM(pago) is null then 0 else sum(pago) end) ")
                .append("from pago where idPrestamo = prestamo.id) < scorpio.prestamo.monto_pagar ")
                .append("and (scorpio.cliente.nombre like '%")
                .append(name)
                .append("%' or scorpio.cliente.apellidoPaterno like '%")
                .append(name)
                .append("%' or scorpio.cliente.apellidoMaterno like '%")
                .append(name)
                .append("%');");
        return (List<Prestamo>) listarSQL(consulta.toString(), paramQuery, null);
    }

    @Override
    public List getListaPrestamosPorCobrar(Prestamo prestamo)
    {
        String nombreCliente;
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();

        nombreCliente = prestamo.getFilterNombreCliente() == null ? "" : prestamo.getFilterNombreCliente();

        consulta.append("SELECT prestamo.`id`,  prestamo.`fecha`,  prestamo.`prestamo`,  prestamo.`cuota`,  prestamo.`monto_pagar`,  prestamo.`id_empleado`,  prestamo.`estado_id`,  ")
                .append("prestamo.`artefacto_id`,  prestamo.`tipo_prestamo_id`,  prestamo.`cliente_id`,  prestamo.`garante`,  prestamo.`frecuencia`,  prestamo.`numeroRenovacion`, ")
                .append("prestamo.`fechaRenovacion`,  prestamo.`indicadorRenovacion`,  prestamo.`interes`,    ")
                .append(" (SELECT (CASE WHEN SUM(pago) IS NULL THEN 0 ELSE SUM(pago) END) FROM pago WHERE idPrestamo = prestamo.id) AS pago ")
                .append("FROM scorpio.prestamo,  scorpio.cliente ")
                .append("WHERE prestamo.cliente_id=cliente.id AND (SELECT (CASE WHEN SUM(pago) IS NULL THEN 0 ELSE SUM(pago) END) ")
                .append("FROM pago WHERE idPrestamo = prestamo.id) < scorpio.prestamo.monto_pagar AND scorpio.cliente.nombre LIKE '%")
                .append(nombreCliente)
                .append("%'; ");
        return (List<Prestamo>) listarSQL(consulta.toString(), paramQuery, null);
    }

    @Override
    public Double getTotalAmount(Prestamo prestamo)
    {
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();
        consulta.append("select sum(u.prestamo) from Prestamo u where u.id is not null");

        if (prestamo.getFilterStart() != null && prestamo.getFilterEnd() != null)
        {
            paramQuery.put("start", prestamo.getFilterStart());
            paramQuery.put("end", prestamo.getFilterEnd());
            consulta.append(" and u.fecha between :start and :end");
        }
        if (prestamo.getIdCliente().getId() != null)
        {
            paramQuery.put("client_id", prestamo.getIdCliente().getId());
            consulta.append(" and u.idCliente.id = :client_id ");
        }
        if (prestamo.getIdSede().getId() != null)
        {
            paramQuery.put("sede_id", prestamo.getIdSede().getId());
            consulta.append(" and u.idSede.id = :sede_id");
        }
        if(prestamo.getFilterEstado()!=null){
            if(prestamo.getFilterEstado()!=0){        
                    paramQuery.put("estado_id", prestamo.getFilterEstado());
                    consulta.append(" and u.idEstado.id = :estado_id ");
            }
            
        }
        if (!paramQuery.isEmpty())
        {
            Object o = obtenerPorHQL(consulta.toString(), paramQuery, null);

            if (o == null)
            {
                return 0.0;
            }
            else
            {
                return (double) o;
            }
        }
        Object o = obtenerPorHQL(consulta.toString(), null, null);
        if (o == null)
        {
            return 0.0;
        }
        else
        {
            return (double) o;
        }
    }

    @Override
    public List getUnpaidAndOverdue()
    {
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder consulta = new StringBuilder();
        consulta.append("SELECT prestamo.`id`,  prestamo.`fecha`,  prestamo.`prestamo`,  prestamo.`cuota`,  prestamo.`monto_pagar`,  prestamo.`id_empleado`,  prestamo.`estado_id`,  ")
                .append("prestamo.`artefacto_id`,  prestamo.`tipo_prestamo_id`,  prestamo.`cliente_id`,  prestamo.`garante`,  prestamo.`frecuencia`,  prestamo.`numeroRenovacion`, ")
                .append("prestamo.`fechaRenovacion`,  prestamo.`indicadorRenovacion`,  prestamo.`interes`,    ")
                .append(" (select (case when SUM(pago) is null then 0 else sum(pago) end) ")
                .append("from pago where idPrestamo = prestamo.id) as pago ")
                .append("FROM scorpio.prestamo ")
                .append("inner join tipo_prestamo on prestamo.tipo_prestamo_id = tipo_prestamo.id ")
                .append("where prestamo.estado_id in ( ")
                .append(EnumEstado.ACTIVO.getId())
                .append(") ");
        
                //.append("where (select (case when SUM(pago) is null then 0 else sum(pago) end) ")
                //.append("from pago where idPrestamo = p.id) < p.monto_pagar ")
                //.append( "and DATEDIFF(DATE_ADD(`fecha`, INTERVAL (select tiempo from tipo_prestamo where id=tp.id) DAY), NOW())<=0 and p.estado_id not in( ")
                //.append(EnumEstado.RENOVADO.getId())
                //.append(", ")
                //.append(EnumEstado.CANCELADO.getId())
                //.append(")")
                //.append(";");
        return (List<Prestamo>) listarSQL(consulta.toString(), paramQuery, null);
    }
}
