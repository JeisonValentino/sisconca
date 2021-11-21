package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Renewal;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository("renewalDao")
public class RenewalDaoImpl extends GenericEntityDaoImpl<Renewal, Serializable> implements RenewalDao
{

    @Override
    public List<Renewal> getPaginatedResult(Renewal renewal)
    {
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder query = new StringBuilder();
        query.append("from Renewal r where r.id is not null");

        System.out.println("client_id1"+renewal.getPrestamoPorRenovacion().getIdCliente().getId());
        if (renewal.getPrestamoPorRenovacion().getIdCliente().getId() != null)
        {
            
            paramQuery.put("client_id", renewal.getPrestamoPorRenovacion().getIdCliente().getId());
            query.append(" and r.prestamoPorRenovacion.idCliente.id = :client_id");
        }
        System.out.println("fechas1:"+renewal.getStart()+""+renewal.getEnd());
        if (renewal.getStart() != null && renewal.getEnd() != null)
        {
            paramQuery.put("start", renewal.getStart());
            paramQuery.put("end", renewal.getEnd());
            query.append(" and r.prestamoPorRenovacion.fecha between :start and :end ");
        }
        System.out.println("Sede1"+renewal.getPrestamoPorRenovacion().getIdSede().getId());
        if (renewal.getPrestamoPorRenovacion().getIdSede().getId() != null)
        {
            paramQuery.put("sede_id", renewal.getPrestamoPorRenovacion().getIdSede().getId());
            query.append(" and r.prestamoPorRenovacion.idSede.id = :sede_id");
        }
        
        if (paramQuery.isEmpty())
        {
            System.out.println("QUERY:"+query.toString());
            return listarLazyHQL(query.toString(), null, renewal.getFirst(), renewal.getPageSize(), null);
            
        }
        return listarLazyHQL(query.toString(), paramQuery, renewal.getFirst(), renewal.getPageSize(), null);
        
    }

    @Override
    public double getTotalAmount(Renewal renewal)
    {
        Map<String, Object> paramQuery = new HashMap<>();
        StringBuilder query = new StringBuilder();
        query.append("select sum(r.prestamoPorRenovacion.prestamo) from Renewal r where r.id is not null");
        if (renewal.getPrestamoPorRenovacion().getIdCliente().getId() != null)
        {
            paramQuery.put("client_id", renewal.getPrestamoPorRenovacion().getIdCliente().getId());
            query.append(" and r.prestamoPorRenovacion.idCliente.id = :client_id");
        }
        if (renewal.getPrestamoPorRenovacion().getFilterStart() != null && renewal.getPrestamoPorRenovacion().getFilterEnd() != null)
        {
            paramQuery.put("start", renewal.getPrestamoPorRenovacion().getFilterStart());
            paramQuery.put("end", renewal.getPrestamoPorRenovacion().getFilterEnd());
            query.append(" and r.prestamoPorRenovacion.fecha between :start and :end ");
        }
        if (renewal.getPrestamoPorRenovacion().getIdSede().getId() != null)
        {
            paramQuery.put("sede_id", renewal.getPrestamoPorRenovacion().getIdSede().getId());
            query.append(" and r.prestamoPorRenovacion.idSede.id = :sede_id");
        }
        try{
            if (paramQuery.isEmpty()){
                Object o = obtenerPorHQL(query.toString(), null, null);
                return (double) o;
            }else{
                Object o = obtenerPorHQL(query.toString(), paramQuery, null);
                return (double) o;
            }
        }catch(Exception e){
            
            return 0.0;
        }
//        Object o = obtenerPorHQL(query.toString(), paramQuery, null);
//
//        if (o == null)
//        {
//            return 0.0;
//        }
//        else
//        {
//            return (double) o;
//        }
    }

    @Override
    public int save(Renewal renewal)
    {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO `renovacion` (`idPrestamoPorRenovacion`, `idPrestamoOrigenRenovacion`, `idCliente`, `saldo`, `importe`) VALUES ('")
                .append(renewal.getPrestamoPorRenovacion().getId())
                .append("', '")
                .append(renewal.getPrestamoOrigenRenovacion().getId())
                .append("', '").append(renewal.getPrestamoPorRenovacion().getIdCliente().getId())
                .append("', '").append(renewal.getSaldo())
                .append("', '").append(renewal.getPrestamoPorRenovacion().getPrestamo())
                .append("');");
        // Long lastId = ((Long) session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).longValue();
        ejecutarConsultaSQL(query.toString());
        int lastId = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).intValue();
        return lastId;
    }
}
