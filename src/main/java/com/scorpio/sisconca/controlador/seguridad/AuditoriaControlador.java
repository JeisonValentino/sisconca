/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.seguridad;

import com.scorpio.sisconca.controlador.report.ReporteResumenPagosController;
import com.scorpio.sisconca.controlador.transaccion.RenewalController;
import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.ClienteAuditoria;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.EmpleadoAuditoria;
import com.scorpio.sisconca.entidad.Entidad;
import com.scorpio.sisconca.entidad.PagoAuditoria;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.PrestamoAuditoria;
import com.scorpio.sisconca.entidad.RenovacionAuditoria;
import com.scorpio.sisconca.entidad.Sede;
import com.scorpio.sisconca.entidad.TipoCambio;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.servicio.maestros.ClienteAuditoriaServicio;
import com.scorpio.sisconca.servicio.maestros.EmpleadoAuditoriaServicio;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.seguridad.AuditoriaServicio;
import com.scorpio.sisconca.servicio.seguridad.EntidadServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.SedeServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.servicio.transaccion.PagoAuditoriaServicio;
import com.scorpio.sisconca.servicio.transaccion.PrestamoAuditoriaServicio;
import com.scorpio.sisconca.servicio.transaccion.RenovacionAuditoriaServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEntidadAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author YVAN
 */
@ManagedBean
@SessionScoped
public class AuditoriaControlador implements Serializable {

    private static final Logger LOG = Logger.getLogger(AuditoriaControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{auditoriaServicio}")
    private AuditoriaServicio auditoriaServicio;

    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;

    @ManagedProperty("#{sedeServicio}")
    private SedeServicio sedeServicio;

    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;

    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;
    
    @ManagedProperty("#{entidadServicio}")
    private EntidadServicio entidadServicio;
    
    @ManagedProperty("#{empleadoAuditoriaServicio}")
    private EmpleadoAuditoriaServicio empleadoAuditoriaServicio;
    
    @ManagedProperty("#{clienteAuditoriaServicio}")
    private ClienteAuditoriaServicio clienteAuditoriaServicio;
    
    @ManagedProperty("#{prestamoAuditoriaServicio}")
    private PrestamoAuditoriaServicio prestamoAuditoriaServicio;
    
    @ManagedProperty("#{pagoAuditoriaServicio}")
    private PagoAuditoriaServicio pagoAuditoriaServicio;
    
    @ManagedProperty("#{renovacionAuditoriaServicio}")
    private RenovacionAuditoriaServicio renovacionAuditoriaServicio;

    public ClienteAuditoriaServicio getClienteAuditoriaServicio() {
        return clienteAuditoriaServicio;
    }

    public void setClienteAuditoriaServicio(ClienteAuditoriaServicio clienteAuditoriaServicio) {
        this.clienteAuditoriaServicio = clienteAuditoriaServicio;
    }

    public PrestamoAuditoriaServicio getPrestamoAuditoriaServicio() {
        return prestamoAuditoriaServicio;
    }

    public void setPrestamoAuditoriaServicio(PrestamoAuditoriaServicio prestamoAuditoriaServicio) {
        this.prestamoAuditoriaServicio = prestamoAuditoriaServicio;
    }

    public PagoAuditoriaServicio getPagoAuditoriaServicio() {
        return pagoAuditoriaServicio;
    }

    public void setPagoAuditoriaServicio(PagoAuditoriaServicio pagoAuditoriaServicio) {
        this.pagoAuditoriaServicio = pagoAuditoriaServicio;
    }

    public RenovacionAuditoriaServicio getRenovacionAuditoriaServicio() {
        return renovacionAuditoriaServicio;
    }

    public void setRenovacionAuditoriaServicio(RenovacionAuditoriaServicio renovacionAuditoriaServicio) {
        this.renovacionAuditoriaServicio = renovacionAuditoriaServicio;
    }
    
    

    public EmpleadoAuditoriaServicio getEmpleadoAuditoriaServicio() {
        return empleadoAuditoriaServicio;
    }

    public void setEmpleadoAuditoriaServicio(EmpleadoAuditoriaServicio empleadoAuditoriaServicio) {
        this.empleadoAuditoriaServicio = empleadoAuditoriaServicio;
    }
    
    

    public EntidadServicio getEntidadServicio() {
        return entidadServicio;
    }

    public void setEntidadServicio(EntidadServicio entidadServicio) {
        this.entidadServicio = entidadServicio;
    }

    private List<Sede> listaSede;
    private List<Entidad> listaEntidad;
    //private List<Auditoria> listaAuditoria;
    private LazyDataModel<Auditoria> listaLazyAuditoria;
    
    private LazyDataModel<EmpleadoAuditoria> listaLazyAuditoriaEmpleado;
    private LazyDataModel<ClienteAuditoria> listaLazyAuditoriaCliente;    
    private LazyDataModel<PrestamoAuditoria> listaLazyAuditoriaPrestamo;
    private LazyDataModel<PagoAuditoria> listaLazyAuditoriaPago;
    private LazyDataModel<RenovacionAuditoria> listaLazyAuditoriaRenovacion;

    public LazyDataModel<EmpleadoAuditoria> getListaLazyAuditoriaEmpleado() {
        return listaLazyAuditoriaEmpleado;
    }

    public void setListaLazyAuditoriaEmpleado(LazyDataModel<EmpleadoAuditoria> listaLazyAuditoriaEmpleado) {
        this.listaLazyAuditoriaEmpleado = listaLazyAuditoriaEmpleado;
    }

    public LazyDataModel<ClienteAuditoria> getListaLazyAuditoriaCliente() {
        return listaLazyAuditoriaCliente;
    }

    public void setListaLazyAuditoriaCliente(LazyDataModel<ClienteAuditoria> listaLazyAuditoriaCliente) {
        this.listaLazyAuditoriaCliente = listaLazyAuditoriaCliente;
    }

    public LazyDataModel<PrestamoAuditoria> getListaLazyAuditoriaPrestamo() {
        return listaLazyAuditoriaPrestamo;
    }

    public void setListaLazyAuditoriaPrestamo(LazyDataModel<PrestamoAuditoria> listaLazyAuditoriaPrestamo) {
        this.listaLazyAuditoriaPrestamo = listaLazyAuditoriaPrestamo;
    }

    public LazyDataModel<PagoAuditoria> getListaLazyAuditoriaPago() {
        return listaLazyAuditoriaPago;
    }

    public void setListaLazyAuditoriaPago(LazyDataModel<PagoAuditoria> listaLazyAuditoriaPago) {
        this.listaLazyAuditoriaPago = listaLazyAuditoriaPago;
    }

    public LazyDataModel<RenovacionAuditoria> getListaLazyAuditoriaRenovacion() {
        return listaLazyAuditoriaRenovacion;
    }

    public void setListaLazyAuditoriaRenovacion(LazyDataModel<RenovacionAuditoria> listaLazyAuditoriaRenovacion) {
        this.listaLazyAuditoriaRenovacion = listaLazyAuditoriaRenovacion;
    }

    public List<Entidad> getListaEntidad() {
        return listaEntidad;
    }

    public void setListaEntidad(List<Entidad> listaEntidad) {
        this.listaEntidad = listaEntidad;
    }

    private Auditoria auditoriaFiltro;

    public Auditoria getAuditoriaFiltro() {
        return auditoriaFiltro;
    }

    public void setAuditoriaFiltro(Auditoria auditoriaFiltro) {
        this.auditoriaFiltro = auditoriaFiltro;
    }

    public LazyDataModel<Auditoria> getListaLazyAuditoria() {
        return listaLazyAuditoria;
    }

    public void setListaLazyAuditoria(LazyDataModel<Auditoria> listaLazyAuditoria) {
        this.listaLazyAuditoria = listaLazyAuditoria;
    }

    //Filtros
    private Date fechaInicio;
    private Date fechaFin;
    private Integer sedeId;

    public void iniciar() {

        setAuditoriaFiltro(new Auditoria());

        iniciarListaSede();
        asignarSedeFiltro();
        iniciarListaEntidad();
        asignarEntidadFiltro();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, 1);
        //today.getTime()
        //Calendar.getInstance().getTime()
        auditoriaFiltro.setFilterStart(Calendar.getInstance().getTime());
        auditoriaFiltro.setFilterEnd(Calendar.getInstance().getTime());

        iniciarListaAuditoria();

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");

    }
    
    public void iniciarAuditoriaEmpleado() {

        setAuditoriaFiltro(new Auditoria());

        iniciarListaSede();
        asignarSedeFiltro();
        iniciarListaEntidad();
        asignarEntidadFiltro();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, 1);
        //today.getTime()
        //Calendar.getInstance().getTime()
        auditoriaFiltro.setFilterStart(Calendar.getInstance().getTime());
        auditoriaFiltro.setFilterEnd(Calendar.getInstance().getTime());

        iniciarListaAuditoriaEmpleado();

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");

    }
    public void iniciarAuditoriaCliente() {

        setAuditoriaFiltro(new Auditoria());

        iniciarListaSede();
        asignarSedeFiltro();
        iniciarListaEntidad();
        asignarEntidadFiltro();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, 1);
        //today.getTime()
        //Calendar.getInstance().getTime()
        auditoriaFiltro.setFilterStart(Calendar.getInstance().getTime());
        auditoriaFiltro.setFilterEnd(Calendar.getInstance().getTime());

        iniciarListaAuditoriaCliente();

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");

    }
    public void iniciarAuditoriaPrestamo() {

        setAuditoriaFiltro(new Auditoria());

        iniciarListaSede();
        asignarSedeFiltro();
        iniciarListaEntidad();
        asignarEntidadFiltro();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, 1);
        //today.getTime()
        //Calendar.getInstance().getTime()
        auditoriaFiltro.setFilterStart(Calendar.getInstance().getTime());
        auditoriaFiltro.setFilterEnd(Calendar.getInstance().getTime());

        iniciarListaAuditoriaPrestamo();

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");

    }
    public void iniciarAuditoriaPago() {

        setAuditoriaFiltro(new Auditoria());

        iniciarListaSede();
        asignarSedeFiltro();
        iniciarListaEntidad();
        asignarEntidadFiltro();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, 1);
        //today.getTime()
        //Calendar.getInstance().getTime()
        auditoriaFiltro.setFilterStart(Calendar.getInstance().getTime());
        auditoriaFiltro.setFilterEnd(Calendar.getInstance().getTime());

        iniciarListaAuditoriaPago();

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");

    }
    public void iniciarAuditoriaRenovacion() {

        setAuditoriaFiltro(new Auditoria());

        iniciarListaSede();
        asignarSedeFiltro();
        iniciarListaEntidad();
        asignarEntidadFiltro();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, 1);
        //today.getTime()
        //Calendar.getInstance().getTime()
        auditoriaFiltro.setFilterStart(Calendar.getInstance().getTime());
        auditoriaFiltro.setFilterEnd(Calendar.getInstance().getTime());

        iniciarListaAuditoriaRenovacion();

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");

    }
    
    

    public void lazyListTable() throws Exception {
        if (listaLazyAuditoria == null) {
            System.out.println("XXXXXXXXXXXXXXXXXX3");
            listaLazyAuditoria = new LazyDataModel<Auditoria>() {

                private static final long serialVersionUID = 1L;

                @Override
                public List<Auditoria> load(int first, int pageSize, String sortField, org.primefaces.model.SortOrder sortOrder, Map<String, Object> filters) {
                    System.out.println("XXXXXXXXXXXXXXXXXX4");
                    List<Auditoria> listData = null;
                    System.out.println("XXXXXXXXXXXXXXXXXX5");
                    try {
                        System.out.println("XXXXXXXXXXXXXXXXXX1");
                        getAuditoriaFiltro().setFirst(first);
                        getAuditoriaFiltro().setPageSize(pageSize);
                        System.out.println("XXXXXXXXXXXXXXXXXX2");
                        listData = getAuditoriaServicio().listarPorFecha(auditoriaFiltro);
                    } catch (Exception e) {
                        LOG.error("error6: " + e);
                    }

                    return listData;
                }
            };
            listaLazyAuditoria.setRowCount(getAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
            //listaLazyAuditoria.setRowCount(1);
        }
    }
    public void lazyListTableEmpleado() throws Exception {
        if (listaLazyAuditoriaEmpleado == null) {            
            listaLazyAuditoriaEmpleado = new LazyDataModel<EmpleadoAuditoria>() {
                private static final long serialVersionUID = 1L;
                @Override
                public List<EmpleadoAuditoria> load(int first, int pageSize, String sortField, org.primefaces.model.SortOrder sortOrder, Map<String, Object> filters) {
                    List<EmpleadoAuditoria> listData = null;
                    try {
                        getAuditoriaFiltro().setFirst(first);
                        getAuditoriaFiltro().setPageSize(pageSize);
                        listData = getEmpleadoAuditoriaServicio().listarPorFecha(auditoriaFiltro);
                    } catch (Exception e) {
                        LOG.error("error6: " + e);
                    }
                    return listData;
                }
            };
            listaLazyAuditoriaEmpleado.setRowCount(getEmpleadoAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        }
    }
    public void lazyListTableCliente() throws Exception {
        if (listaLazyAuditoriaCliente == null) {            
            listaLazyAuditoriaCliente = new LazyDataModel<ClienteAuditoria>() {
                private static final long serialVersionUID = 1L;
                @Override
                public List<ClienteAuditoria> load(int first, int pageSize, String sortField, org.primefaces.model.SortOrder sortOrder, Map<String, Object> filters) {
                    List<ClienteAuditoria> listData = null;
                    try {
                        getAuditoriaFiltro().setFirst(first);
                        getAuditoriaFiltro().setPageSize(pageSize);
                        listData = getClienteAuditoriaServicio().listarPorFecha(auditoriaFiltro);
                    } catch (Exception e) {
                        LOG.error("error6: " + e);
                    }
                    return listData;
                }
            };
            listaLazyAuditoriaCliente.setRowCount(getClienteAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        }
    }
    public void lazyListTablePrestamo() throws Exception {
        if (listaLazyAuditoriaPrestamo == null) {            
            listaLazyAuditoriaPrestamo = new LazyDataModel<PrestamoAuditoria>() {
                private static final long serialVersionUID = 1L;
                @Override
                public List<PrestamoAuditoria> load(int first, int pageSize, String sortField, org.primefaces.model.SortOrder sortOrder, Map<String, Object> filters) {
                    List<PrestamoAuditoria> listData = null;
                    try {
                        getAuditoriaFiltro().setFirst(first);
                        getAuditoriaFiltro().setPageSize(pageSize);
                        listData = getPrestamoAuditoriaServicio().listarPorFecha(auditoriaFiltro);
                    } catch (Exception e) {
                        LOG.error("error6: " + e);
                    }
                    return listData;
                }
            };
            listaLazyAuditoriaPrestamo.setRowCount(getPrestamoAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        }
    }
    public void lazyListTablePago() throws Exception {
        if (listaLazyAuditoriaPago == null) {            
            listaLazyAuditoriaPago = new LazyDataModel<PagoAuditoria>() {
                private static final long serialVersionUID = 1L;
                @Override
                public List<PagoAuditoria> load(int first, int pageSize, String sortField, org.primefaces.model.SortOrder sortOrder, Map<String, Object> filters) {
                    List<PagoAuditoria> listData = null;
                    try {
                        getAuditoriaFiltro().setFirst(first);
                        getAuditoriaFiltro().setPageSize(pageSize);
                        listData = getPagoAuditoriaServicio().listarPorFecha(auditoriaFiltro);
                    } catch (Exception e) {
                        LOG.error("error6: " + e);
                    }
                    return listData;
                }
            };
            listaLazyAuditoriaPago.setRowCount(getPagoAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        }
    }
    
    public void lazyListTableRenovacion() throws Exception {
        if (listaLazyAuditoriaRenovacion == null) {            
            listaLazyAuditoriaRenovacion = new LazyDataModel<RenovacionAuditoria>() {
                private static final long serialVersionUID = 1L;
                @Override
                public List<RenovacionAuditoria> load(int first, int pageSize, String sortField, org.primefaces.model.SortOrder sortOrder, Map<String, Object> filters) {
                    List<RenovacionAuditoria> listData = null;
                    try {
                        getAuditoriaFiltro().setFirst(first);
                        getAuditoriaFiltro().setPageSize(pageSize);
                        listData = getRenovacionAuditoriaServicio().listarPorFecha(auditoriaFiltro);
                    } catch (Exception e) {
                        LOG.error("error6: " + e);
                    }
                    return listData;
                }
            };
            listaLazyAuditoriaRenovacion.setRowCount(getRenovacionAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        }
    }
    
    
    
    
    public void filtrar() throws Exception{
        listaLazyAuditoria.setRowCount(getAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        Faces.getRequestContext().update("formListaAuditoria:dataTableAuditoria");
    }
    
    public void filtrarEmpleado() throws Exception{
        listaLazyAuditoriaEmpleado.setRowCount(getEmpleadoAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        Faces.getRequestContext().update("formListaAuditoria:dataTableAuditoria");
    }
    public void filtrarCliente() throws Exception{
        listaLazyAuditoriaCliente.setRowCount(getClienteAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        Faces.getRequestContext().update("formListaAuditoria:dataTableAuditoria");
    }
    public void filtrarPrestamo() throws Exception{
        listaLazyAuditoriaPrestamo.setRowCount(getPrestamoAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        Faces.getRequestContext().update("formListaAuditoria:dataTableAuditoria");
    }
    public void filtrarPago() throws Exception{
        listaLazyAuditoriaPago.setRowCount(getPagoAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        Faces.getRequestContext().update("formListaAuditoria:dataTableAuditoria");
    }
    public void filtrarRenovacion() throws Exception{
        listaLazyAuditoriaRenovacion.setRowCount(getRenovacionAuditoriaServicio().getCountLazy(auditoriaFiltro).intValue());
        Faces.getRequestContext().update("formListaAuditoria:dataTableAuditoria");
    }
    

    public boolean isSoporte() throws Exception {
        List<PerfilPorUsuario> perfilPorUsuarios = perfilPorUsuarioServicio.getByUserId(Faces.getUserInSession().getId());
        return perfilPorUsuarios.stream().anyMatch((perfilPorUsuario) -> (perfilPorUsuario.getIdPerfil().getId() == EnumPerfil.SOPORTE.getId()));
    }

    public void iniciarListaAuditoria() {
        try {
            //Filtro inicializado (seteo)

            lazyListTable();
            //setListaAuditoria(auditoriaServicio.obtenerTodo());
            //setListaAuditoria(getAuditoriaServicio().listarPorFecha(auditoriaFiltro));

        } catch (Exception e) {
            LOG.error("Error:" + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación (auditoria), póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }

    }
    
    public void iniciarListaAuditoriaEmpleado() {
        try {
            //Filtro inicializado (seteo)
            lazyListTableEmpleado();
            
        } catch (Exception e) {
            LOG.error("Error:" + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación (auditoria), póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }

    }
    public void iniciarListaAuditoriaCliente() {
        try {
            //Filtro inicializado (seteo)
            lazyListTableCliente();
            
        } catch (Exception e) {
            LOG.error("Error:" + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación (auditoria), póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }

    }
    public void iniciarListaAuditoriaPrestamo() {
        try {
            //Filtro inicializado (seteo)
            lazyListTablePrestamo();
            
        } catch (Exception e) {
            LOG.error("Error:" + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación (auditoria), póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }

    }
    public void iniciarListaAuditoriaPago() {
        try {
            //Filtro inicializado (seteo)
            lazyListTablePago();
            
        } catch (Exception e) {
            LOG.error("Error:" + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación (auditoria), póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }

    }
    public void iniciarListaAuditoriaRenovacion() {
        try {
            //Filtro inicializado (seteo)
            lazyListTableRenovacion();
            
        } catch (Exception e) {
            LOG.error("Error:" + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación (auditoria), póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }

    }
    

    public void iniciarListaSede() {
        try {
            setListaSede(getSedeServicio().obtenerTodo());

        } catch (Exception e) {
            LOG.error("Error:" + e);

            java.util.logging.Logger.getLogger(AuditoriaControlador.class.getName()).log(Level.SEVERE, null, e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación (Lista sede), póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }
    
    public void iniciarListaEntidad() {
        try {
            
            
            List<Entidad> listaEntidadOrigen= new ArrayList<Entidad>();
            listaEntidadOrigen=getEntidadServicio().obtenerTodo();
            List<Entidad> listaEntidadFiltro= new ArrayList<Entidad>();
            for(Entidad eo:listaEntidadOrigen){                
                for (EnumEntidadAuditoria ed: EnumEntidadAuditoria.values()){
                    if (eo.getId()==ed.getId()) {
                        listaEntidadFiltro.add(eo);
                    }
                }                
            }
            setListaEntidad(listaEntidadFiltro);
            
        } catch (Exception e) {
            LOG.error("Error:" + e);

            java.util.logging.Logger.getLogger(AuditoriaControlador.class.getName()).log(Level.SEVERE, null, e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación (Lista Entidad), póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void asignarSedeFiltro() {

        try {
            if (!isSoporte()) {
                /*Para sede en el menú derecho*/
                Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
                Empleado empleado = empleadoServicio.obtenerPorCodigo((usuario).getIdEmpleado().getId());

                setSedeId(empleado.getIdSede().getId());
                auditoriaFiltro.setSedeId(empleado.getIdSede());

            }else{
                Sede sedeId = new Sede();
                auditoriaFiltro.setSedeId(sedeId);
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AuditoriaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void asignarEntidadFiltro() {

        try {
            Entidad entidad=new Entidad();
            auditoriaFiltro.setEntidadId(entidad);
            
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AuditoriaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registrarAuditoria(Integer usuarioId, String tipoId, int entidadId, String operacionId) {

        try {
            
            Auditoria auditoriaRegistro = new Auditoria();
            
            Usuario usuarioAuditoria = usuarioServicio.obtenerPorCodigo(usuarioId);
            auditoriaRegistro.setUsuarioId(usuarioAuditoria);
            
            auditoriaRegistro.setTipo(tipoId);
            Entidad entidadAuditoria = new Entidad();
            entidadAuditoria.setId(entidadId);
            auditoriaRegistro.setEntidadId(entidadAuditoria);
            auditoriaRegistro.setTipoOperacion(operacionId);
            Date fechaActual = new Date();
            auditoriaRegistro.setFechaOperacion(fechaActual);
            
            Empleado empleadoAuditoria = empleadoServicio.obtenerPorCodigo(usuarioAuditoria.getIdEmpleado().getId());
            auditoriaRegistro.setSedeId(empleadoAuditoria.getIdSede());
            auditoriaServicio.guardar(auditoriaRegistro);
        } catch (Exception e) {
            
            LOG.error("ErrorAuditoria:" + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación (registro auditoria), póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }

    }

    public AuditoriaServicio getAuditoriaServicio() {
        return auditoriaServicio;
    }

    public void setAuditoriaServicio(AuditoriaServicio auditoriaServicio) {
        this.auditoriaServicio = auditoriaServicio;
    }



    public UsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    public EmpleadoServicio getEmpleadoServicio() {
        return empleadoServicio;
    }

    public void setEmpleadoServicio(EmpleadoServicio empleadoServicio) {
        this.empleadoServicio = empleadoServicio;
    }

    public SedeServicio getSedeServicio() {
        return sedeServicio;
    }

    public void setSedeServicio(SedeServicio sedeServicio) {
        this.sedeServicio = sedeServicio;
    }

    public PerfilPorUsuarioServicio getPerfilPorUsuarioServicio() {
        return perfilPorUsuarioServicio;
    }

    public void setPerfilPorUsuarioServicio(PerfilPorUsuarioServicio perfilPorUsuarioServicio) {
        this.perfilPorUsuarioServicio = perfilPorUsuarioServicio;
    }

    public List<Sede> getListaSede() {
        return listaSede;
    }

    public void setListaSede(List<Sede> listaSede) {
        this.listaSede = listaSede;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getSedeId() {
        return sedeId;
    }

    public void setSedeId(Integer sedeId) {
        this.sedeId = sedeId;
    }

}
