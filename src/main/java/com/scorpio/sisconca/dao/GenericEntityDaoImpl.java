package com.scorpio.sisconca.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = false)
public class GenericEntityDaoImpl<T, ID extends Serializable> implements GenericEntityDao<T, Serializable>, Serializable
{

    private static final long serialVersionUID = 1L;

    @Autowired
    protected SessionFactory sessionFactory;

    private final Class<T> oClass;

    public GenericEntityDaoImpl()
    {
        this.oClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Class<T> getObjectClass()
    {
        return this.oClass;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public Serializable guardar(T objeto) throws Exception
    {
        return getSessionFactory().getCurrentSession().save(objeto);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void guardarColeccion(List<T> object) throws Exception
    {
        Session ses = getSessionFactory().getCurrentSession();
        object.stream().forEach((model)
                ->
        {
            ses.save(model);
        });
    }

    @Transactional(readOnly = false)
    @Override
    public void guardarOActualizar(T object) throws Exception
    {
        getSessionFactory().getCurrentSession().saveOrUpdate(object);
    }

    @Transactional(readOnly = false)
    @Override
    public void eliminar(T objeto) throws Exception
    {
        getSessionFactory().getCurrentSession().delete(objeto);
    }

    @Transactional(readOnly = false)
    @Override
    public int ejecutarConsultaHQL(String consulta)
    {

        return getSessionFactory().getCurrentSession().createQuery(consulta).executeUpdate();
    }

    @Transactional(readOnly = false)
    @Override
    public int ejecutarConsultaSQL(String consulta)
    {
        return getSessionFactory().getCurrentSession().createSQLQuery(consulta).executeUpdate();
    }

    @Transactional(readOnly = false)
    @Override
    public int ejecutarConsultaHQL(String query, Map<String, Object> parametros)
    {
        Session ses = getSessionFactory().getCurrentSession();
        Query q;
        q = ses.createQuery(query);
        if (parametros != null)
        {
            parametros.entrySet().stream().forEach((entry)
                    ->
            {
                q.setParameter(entry.getKey(), entry.getValue());
            });
        }
        return q.executeUpdate();
    }

    @Override
    public int ejecutarConsultaSQL(String consulta, Map<String, Object> parametros) throws Exception
    {
        Session ses = getSessionFactory().getCurrentSession();
        Query q;
        q = ses.createSQLQuery(consulta);
        if (parametros != null)
        {
            parametros.entrySet().stream().forEach((entry)
                    ->
            {
                q.setParameter(entry.getKey(), entry.getValue());
            });
        }
        return q.executeUpdate();
    }

    @Override
    public <T> T obtenerPorCodigo(Class<T> tipo, Serializable codigo)
    {
        return (T) getSessionFactory().getCurrentSession().get(tipo, codigo);
    }

    @Override
    public <T> T obtenerPorCodigo(Serializable codigo)
    {
        return (T) getSessionFactory().getCurrentSession().get(oClass, codigo);
    }

    @Override
    public <T> T obtenerPorHQL(String consulta, Map<String, Object> parametros, Class<T> tipo)
    {
        Query que = getSessionFactory().getCurrentSession().createQuery(consulta);
        if (parametros != null)
        {
            parametros.entrySet().stream().forEach((entry)
                    ->
            {
                que.setParameter(entry.getKey(), entry.getValue());
            });
        }
        if (tipo != null)
        {
            que.setResultTransformer(Transformers.aliasToBean(tipo));
        }

        return (T) que.uniqueResult();
    }

    @Override
    public <T> T obtenerPorSQL(String consulta, Map<String, Object> parametros, Class<T> tipo)
    {
        Query que = getSessionFactory().getCurrentSession().createSQLQuery(consulta);
        if (parametros != null)
        {
            parametros.entrySet().stream().forEach((entry)
                    ->
            {
                que.setParameter(entry.getKey(), entry.getValue());
            });
        }
        if (tipo != null)
        {
            que.setResultTransformer(Transformers.aliasToBean(tipo));
        }

        return (T) que.uniqueResult();
    }

    @Override
    public <T> T obtenerPorHQLPorUnLimite(String consulta, Map<String, Object> parametros, Class<T> tipo, int limit)
    {
        Query que;
        que = getSessionFactory().getCurrentSession().createQuery(consulta);
        if (parametros != null)
        {
            parametros.entrySet().stream().forEach((entry)
                    ->
            {
                que.setParameter(entry.getKey(), entry.getValue());
            });
        }
        if (tipo != null)
        {
            que.setResultTransformer(Transformers.aliasToBean(tipo));
        }
        que.setMaxResults(limit);
        return (T) que.uniqueResult();
    }

    @Override
    public <T> T obtenerPorSQLPorUnLimite(String consulta, Map<String, Object> parametros, Class<T> tipo, int limit)
    {
        Query que;
        que = getSessionFactory().getCurrentSession().createSQLQuery(consulta);
        if (parametros != null)
        {
            parametros.entrySet().stream().forEach((entry)
                    ->
            {
                que.setParameter(entry.getKey(), entry.getValue());
            });
        }
        if (tipo != null)
        {
            que.setResultTransformer(Transformers.aliasToBean(tipo));
        }
        que.setMaxResults(limit);
        return (T) que.uniqueResult();
    }

    @Override
    public List listarHQL(String consulta, Map<String, Object> parametros, Class clase)
    {
        Query query;
        if (clase == null)
        {
            query = getSessionFactory().getCurrentSession().createQuery(consulta);
        }
        else
        {
            query = getSessionFactory().getCurrentSession().createQuery(consulta).setResultTransformer(Transformers.aliasToBean(clase));
        }
        if (parametros != null)
        {
            parametros.entrySet().stream().forEach((entry)
                    ->
            {
                query.setParameter(entry.getKey(), entry.getValue());
            });
        }
        return query.list();
    }

    @Override
    public List listarSQL(String consulta, Map<String, Object> parametros, Class clase)
    {
        Query query;
        if (clase == null)
        {
            query = getSessionFactory().getCurrentSession().createSQLQuery(consulta);
        }
        else
        {
            query = getSessionFactory().getCurrentSession().createSQLQuery(consulta).setResultTransformer(Transformers.aliasToBean(clase));
        }
        if (parametros != null)
        {
            parametros.entrySet().stream().forEach((entry)
                    ->
            {
                query.setParameter(entry.getKey(), entry.getValue());
            });
        }
        return query.list();
    }

    @Override
    public List listarLazyHQL(String consulta, Map<String, Object> parametros, int start, int finish, Class clazz)
    {
        Query query;
        if (clazz == null)
        {
            query = getSessionFactory().getCurrentSession().createQuery(consulta);
        }
        else
        {
            query = getSessionFactory().getCurrentSession().createQuery(consulta).setResultTransformer(Transformers.aliasToBean(clazz));
        }
        if (parametros != null)
        {
            parametros.entrySet().stream().forEach((entry)
                    ->
            {
                query.setParameter(entry.getKey(), entry.getValue());
            });
        }
        query.setFirstResult(start)
                .setMaxResults(finish);
        return query.list();
    }
    
    @Override
    public Long getCountLazy(String query, Map<String, Object> params) throws Exception {
        Session ses = getSessionFactory().getCurrentSession();
        Query queryx = ses.createQuery(query);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                queryx.setParameter(entry.getKey(), entry.getValue());
            }
        }
        Long count = (Long) queryx.uniqueResult();
        return count;
    }

    @Override
    public List listarLazySQL(String consulta, Map<String, Object> parametros, int start, int finish, Class clazz)
    {
        Query query;
        if (clazz == null)
        {
            query = getSessionFactory().getCurrentSession().createSQLQuery(consulta);
        }
        else
        {
            query = getSessionFactory().getCurrentSession().createSQLQuery(consulta).setResultTransformer(Transformers.aliasToBean(clazz));
        }
        if (parametros != null)
        {
            parametros.entrySet().stream().forEach((entry)
                    ->
            {
                query.setParameter(entry.getKey(), entry.getValue());
            });
        }
        query.setFirstResult(start)
                .setMaxResults(finish);
        return query.list();
    }

    @Override
    public List listarCriterion(List<Criterion> listaCriterion)
    {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);
        for (int i = 0; i < listaCriterion.size(); i++)
        {
            criteria.add(listaCriterion.get(i));
        }
        return criteria.list();
    }

    @Transactional(readOnly = false)
    @Override
    public void actualizar(T objeto) throws Exception
    {
        try
        {
            getSessionFactory().getCurrentSession().update(objeto);
        }
        catch (Exception e)
        {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void actualizarColeccion(List<T> listaObjeto) throws Exception
    {
        Session ses = getSessionFactory().getCurrentSession();
        listaObjeto.stream().forEach((model)
                ->
        {
            ses.update(model);
        });
    }

    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    @Override
    public List obtenerTodo() throws Exception
    {
        Query query;
        String consulta = "FROM " + getObjectClass().getSimpleName() + " t";
        query = getSessionFactory().getCurrentSession().createQuery(consulta);
        return query.list();
    }

    @Override
    public int contarTodo() throws Exception
    {
        StringBuilder consulta = new StringBuilder();
        consulta.append("select count(*) from ").append(getObjectClass().getSimpleName()).append(" t");
        return Integer.parseInt(obtenerPorHQL(consulta.toString(), null, null).toString());
    }

    @Override
    public List listarHQLPorId(Serializable codigo) throws Exception
    {
        String consulta = "from " + getObjectClass().getSimpleName() + " t where t.id=" + codigo;
        Query query;
        query = getSessionFactory().getCurrentSession().createQuery(consulta);
        return query.list();
    }

}
