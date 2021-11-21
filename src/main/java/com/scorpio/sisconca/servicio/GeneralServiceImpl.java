package com.scorpio.sisconca.servicio;

import com.scorpio.sisconca.dao.GeneralDao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Stewen Mateo <smateovj@gmail.com>
 */
@Service("generalService")
public class GeneralServiceImpl implements GeneralService, Serializable
{

    private static final long serialVersionUID = -5815915319583120116L;

    @Autowired
    GeneralDao dao;

    @Override
    public Serializable guardar(Object objeto) throws Exception
    {
        return dao.guardar(objeto);
    }

    @Override
    public void guardarColeccion(List<Object> objeto) throws Exception
    {
        dao.guardarColeccion(objeto);
    }

    @Override
    public List listarLazyHQL(String consulta, Map<String, Object> parametros, int inicio, int fin, Class clase) throws Exception
    {
        return dao.listarLazyHQL(consulta, parametros, inicio, fin, clase);
    }

    @Override
    public List listarLazySQL(String consulta, Map<String, Object> parametros, int inicio, int fin, Class clase) throws Exception
    {
        return dao.listarLazySQL(consulta, parametros, inicio, fin, clase);
    }

    @Override
    public void guardarOActualizar(Object objeto) throws Exception
    {
        dao.guardarOActualizar(objeto);
    }

    @Override
    public void eliminar(Object objeto) throws Exception
    {
        dao.eliminar(objeto);
    }

    @Override
    public <T> T obtenerPorCodigo(Class<T> tipo, Serializable codigo) throws Exception
    {
        return dao.obtenerPorCodigo(tipo, codigo);
    }

    @Override
    public <T> T obtenerPorCodigo(Serializable codigo) throws Exception
    {
        return dao.obtenerPorCodigo(codigo);
    }

    @Override
    public <T> T obtenerPorHQL(String consulta, Map<String, Object> parametros, Class<T> tipo) throws Exception
    {
        return dao.obtenerPorHQL(consulta, parametros, tipo);
    }

    @Override
    public <T> T obtenerPorSQL(String consulta, Map<String, Object> parametros, Class<T> tipo) throws Exception
    {
        return dao.obtenerPorSQL(consulta, parametros, tipo);
    }

    @Override
    public <T> T obtenerPorHQLPorUnLimite(String consulta, Map<String, Object> parametros, Class<T> tipo, int limite) throws Exception
    {
        return dao.obtenerPorHQLPorUnLimite(consulta, parametros, tipo, limite);
    }

    @Override
    public <T> T obtenerPorSQLPorUnLimite(String consulta, Map<String, Object> parametros, Class<T> tipo, int limite) throws Exception
    {
        return dao.obtenerPorSQLPorUnLimite(consulta, parametros, tipo, limite);
    }

    @Override
    public int ejecutarConsultaHQL(String consulta) throws Exception
    {
        return dao.ejecutarConsultaHQL(consulta);
    }

    @Override
    public int ejecutarConsultaSQL(String consulta) throws Exception
    {
        return dao.ejecutarConsultaSQL(consulta);
    }

    @Override
    public List listarHQL(String consulta, Map<String, Object> parametros, Class clase) throws Exception
    {
        return dao.listarHQL(consulta, parametros, clase);
    }

    @Override
    public List listarSQL(String consulta, Map<String, Object> parametros, Class clase) throws Exception
    {
        return dao.listarSQL(consulta, parametros, clase);
    }

    @Override
    public List listarCriterion(List<Criterion> listarCriterion) throws Exception
    {
        return dao.listarCriterion(listarCriterion);
    }

    @Override
    public void actualizar(Object objeto) throws Exception
    {
        dao.actualizar(objeto);
    }

    @Override
    public void actualizarColeccion(List<Object> objeto) throws Exception
    {
        dao.actualizarColeccion(objeto);
    }

    @Override
    public int ejecutarConsultaHQL(String consulta, Map<String, Object> parametros) throws Exception
    {
        return dao.ejecutarConsultaHQL(consulta, parametros);
    }

    @Override
    public int ejecutarConsultaSQL(String consulta, Map<String, Object> parametros) throws Exception
    {
        return dao.ejecutarConsultaSQL(consulta, parametros);
    }

    @Override
    public List obtenerTodo() throws Exception
    {
        return dao.obtenerTodo();
    }

    @Override
    public int contarTodo() throws Exception
    {
        return dao.contarTodo();
    }

    @Override
    public List listarHQLPorId(Serializable codigo) throws Exception
    {
        return dao.listarHQLPorId(codigo);
    }

}
