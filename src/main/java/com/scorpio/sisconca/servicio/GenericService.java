package com.scorpio.sisconca.servicio;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Stewen Mateo <smateovj@gmail.com>
 * @param <T>
 * @param <ID>
 */
public interface GenericService<T, ID extends java.io.Serializable>
{

    /**
     * Guarda un nuevo registro en la base de datos. Sus relaciones deben estar
     * completas, puesto que puede ocurrir un error al subir a la base de datos.
     * <p>
     * @param objeto nuevo objeto llena con sus relaciones
     * <p>
     * @return retorna el ID del objeto subido.
     * <p>
     * @throws java.lang.Exception
     */
    public Serializable guardar(T objeto) throws Exception;

    /**
     * Guarda una lista de objectos
     * <p>
     * @param objeto
     * <p>
     * @throws Exception
     */
    public void guardarColeccion(List<T> objeto) throws Exception;

    /**
     *
     * @param consulta
     * @param parametros
     * @param inicio
     * @param fin
     * @param clase
     * <p>
     * @return
     * <p>
     * @throws Exception
     */
    public List listarLazyHQL(String consulta, Map<String, Object> parametros,
            int inicio, int fin, Class clase)
            throws Exception;

    /**
     *
     * @param consulta
     * @param parametros
     * @param inicio
     * @param fin
     * @param clase
     * <p>
     * @return
     * <p>
     * @throws Exception
     */
    public List listarLazySQL(String consulta, Map<String, Object> parametros,
            int inicio, int fin, Class clase)
            throws Exception;

    /**
     * Realiza un <code>insert</code>, <code>update</code>, <code>delete</code>,
     * en la base de datos, segun sea el caso de sus relaciones.
     * <p>
     * <p>
     * @param objeto
     * <p>
     * @throws java.lang.Exception
     */
    public void guardarOActualizar(T objeto) throws Exception;

    /**
     * Borra en cascada todas las realciones del objeto en la base de datos
     * <p>
     * @param objeto Tiene que tener lleno sus datos para poder borrar todo en
     * cascada.
     * <p>
     * @throws java.lang.Exception
     */
    public void eliminar(T objeto) throws Exception;

    /**
     * Metodo que devuelve un objeto completo ingresando como parametro su Id
     * <p>
     * @param <T>
     * @param tipo
     * @param codigo Su identificador de la clase que esta relacionado con la
     * base de datos
     * <p>
     * @return Retorna un Objeto del tipo de la misma clase.
     * <p>
     * @throws java.lang.Exception
     */
    public <T> T obtenerPorCodigo(Class<T> tipo, Serializable codigo)
            throws Exception;

    /**
     * No hace falta mandarles la clase si ya está dentro del GenericEntity como
     * <code>oClass</code>
     * <p>
     * @param <T>
     * @param codigo
     * <p>
     * @return
     * <p>
     * @throws Exception
     */
    public <T> T obtenerPorCodigo(Serializable codigo) throws Exception;

    /**
     *
     * @param <T>
     * @param consulta
     * @param parametros
     * @param tipo
     * <p>
     * @return
     * <p>
     * @throws Exception
     */
    public <T> T obtenerPorHQL(String consulta, Map<String, Object> parametros,
            Class<T> tipo) throws Exception;

    /**
     *
     * @param <T>
     * @param consulta
     * @param parametros
     * @param tipo
     * <p>
     * @return
     * <p>
     * @throws Exception
     */
    public <T> T obtenerPorSQL(String consulta, Map<String, Object> parametros,
            Class<T> tipo) throws Exception;

    /**
     *
     * @param <T>
     * @param consulta
     * @param parametros
     * @param tipo
     * @param limite
     * <p>
     * @return
     * <p>
     * @throws Exception
     */
    public <T> T obtenerPorHQLPorUnLimite(String consulta, Map<String, Object> parametros,
            Class<T> tipo, int limite) throws Exception;

    /**
     *
     * @param <T>
     * @param consulta
     * @param parametros
     * @param tipo
     * @param limite
     * <p>
     * @return
     * <p>
     * @throws Exception
     */
    public <T> T obtenerPorSQLPorUnLimite(String consulta, Map<String, Object> parametros,
            Class<T> tipo, int limite) throws Exception;

    /**
     * Realiza Update a los campos que no sean entidades
     * <p>
     * @param consulta
     * <p>
     * @return Numero de filas afectadas
     * <p>
     * @throws Exception
     */
    public int ejecutarConsultaHQL(String consulta) throws Exception;

    /**
     * Realiza Update a los campos que no sean entidades
     * <p>
     * @param consulta
     * <p>
     * @return Numero de filas afectadas
     * <p>
     * @throws Exception
     */
    public int ejecutarConsultaSQL(String consulta) throws Exception;

    /**
     * Realiza Update a los campos que no sean entidades
     * <p>
     * @param consulta
     * @param parametros
     * @param clase
     * <p>
     * @return Numero de filas afectadas
     * <p>
     * @throws Exception
     */
    public List listarHQL(String consulta, Map<String, Object> parametros, Class clase)
            throws Exception;

    /**
     *
     * @param consulta
     * @param parametros
     * @param clase
     * <p>
     * @return
     * <p>
     * @throws Exception
     */
    public List listarSQL(String consulta, Map<String, Object> parametros, Class clase)
            throws Exception;

    /**
     * Se debe crear una lista de objetos Criterion de Hibernate: <ul>
     * <code>List(Criterion) listCriterion = new ArrayList();</code> Ejemplos:
     * </ul> <ul> <li> To get records having salary more than 2000 </li>
     * <code>listCriterion.add(Restrictions.gt("salary", 2000));</code>
     * <p>
     * </ul> <ul><li>To get records having salary less than 2000</li>
     * <code>listCriterion.add(Restrictions.lt("salary", 2000));</code> </ul>
     * <ul> <li>To get records having fistName starting with zara</li>
     * <code>listCriterion.add(Restrictions.like("firstName", "zara%"));</code>
     * </ul> <ul> <li>Case sensitive form of the above restriction.</li>
     * <code>listCriterion.add(Restrictions.ilike("firstName", "zara%"));</code>
     * </ul> <ul> <li> To get records having salary in between 1000 and
     * 2000</li>
     * <code>listCriterion.add(Restrictions.between("salary", 1000, 2000));</code>
     * </ul> <ul> <li> To check if the given property is null</li>
     * <code>listCriterion.add(Restrictions.isNull("salary"));</code> </ul> <ul>
     * <li> To check if the given property is not null</li>
     * <code>listCriterion.add(Restrictions.isNotNull("salary"));</code> </ul>
     * <ul> <li> To check if the given property is empty</li>
     * <code>listCriterion.add(Restrictions.isEmpty("salary"));</code> </ul>
     * <ul> <li> To check if the given property is not empty</li>
     * <code>listCriterion.add(Restrictions.isNotEmpty("salary"));</code> </ul>
     *
     * @param listarCriterion Lista de restricciones para realizar la consulta.
     * <p>
     * @return Retorna una lista con condiciones dadas.
     * <p>
     * @throws java.lang.Exception
     */
    public List listarCriterion(List<Criterion> listarCriterion) throws Exception;

    /**
     *
     * @param objeto
     * <p>
     * @throws Exception
     */
    public void actualizar(T objeto) throws Exception;

    /**
     *
     * @param objeto
     * <p>
     * @throws Exception
     */
    public void actualizarColeccion(List<T> objeto) throws Exception;

    /**
     *
     * @param consulta
     * @param parametros
     * <p>
     * @return
     * <p>
     * @throws Exception
     */
    public int ejecutarConsultaHQL(String consulta, Map<String, Object> parametros)
            throws Exception;

    /**
     *
     * @param consulta
     * @param parametros
     * <p>
     * @return
     * <p>
     * @throws Exception
     */
    public int ejecutarConsultaSQL(String consulta, Map<String, Object> parametros)
            throws Exception;

    /**
     *
     * @return @throws Exception
     */
    public List obtenerTodo() throws Exception;

    /**
     *
     * @return @throws Exception
     */
    public int contarTodo() throws Exception;

    public List listarHQLPorId(Serializable codigo) throws Exception;
}
