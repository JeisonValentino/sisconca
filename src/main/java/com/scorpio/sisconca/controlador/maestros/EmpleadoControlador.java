package com.scorpio.sisconca.controlador.maestros;

import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.Cliente;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.EmpleadoAuditoria;
import com.scorpio.sisconca.entidad.Entidad;
import com.scorpio.sisconca.entidad.Perfil;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.Prestamo;
import com.scorpio.sisconca.entidad.Sede;
import com.scorpio.sisconca.entidad.TipoDocumentoIdentidad;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.entidad.ZonaCobranza;
import com.scorpio.sisconca.servicio.maestros.EmpleadoAuditoriaServicio;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.mantenimiento.ZonaCobranzaServicio;
import com.scorpio.sisconca.servicio.seguridad.AuditoriaServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilServicio;
import com.scorpio.sisconca.servicio.seguridad.SedeServicio;
import com.scorpio.sisconca.servicio.seguridad.TipoDocumentoIdentidadServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.servicio.transaccion.PrestamoServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEntidadAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import com.scorpio.sisconca.utilitario.enums.EnumTipoAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumTipoOperacionAuditoria;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@ManagedBean
@SessionScoped
public class EmpleadoControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(EmpleadoControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;
    
    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;
    
    @ManagedProperty("#{prestamoServicio}")
    private PrestamoServicio prestamoServicio;
    
    @ManagedProperty("#{auditoriaServicio}")
    private AuditoriaServicio auditoriaServicio;
    
    @ManagedProperty("#{empleadoAuditoriaServicio}")
    private EmpleadoAuditoriaServicio empleadoAuditoriaServicio;

    public EmpleadoAuditoriaServicio getEmpleadoAuditoriaServicio() {
        return empleadoAuditoriaServicio;
    }

    public void setEmpleadoAuditoriaServicio(EmpleadoAuditoriaServicio empleadoAuditoriaServicio) {
        this.empleadoAuditoriaServicio = empleadoAuditoriaServicio;
    }

    public AuditoriaServicio getAuditoriaServicio() {
        return auditoriaServicio;
    }

    public void setAuditoriaServicio(AuditoriaServicio auditoriaServicio) {
        this.auditoriaServicio = auditoriaServicio;
    }
    
    

    public PrestamoServicio getPrestamoServicio() {
        return prestamoServicio;
    }

    public void setPrestamoServicio(PrestamoServicio prestamoServicio) {
        this.prestamoServicio = prestamoServicio;
    }
    

    @ManagedProperty("#{tipoDocumentoIdentidadServicio}")
    private TipoDocumentoIdentidadServicio tipoDocumentoIdentidadServicio;

    @ManagedProperty("#{sedeServicio}")
    private SedeServicio sedeServicio;
    
    @ManagedProperty("#{perfilServicio}")
    private PerfilServicio perfilServicio;
    
    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;

    public PerfilPorUsuarioServicio getPerfilPorUsuarioServicio() {
        return perfilPorUsuarioServicio;
    }

    public void setPerfilPorUsuarioServicio(PerfilPorUsuarioServicio perfilPorUsuarioServicio) {
        this.perfilPorUsuarioServicio = perfilPorUsuarioServicio;
    }
    
    
    @ManagedProperty("#{zonaCobranzaServicio}")
    private ZonaCobranzaServicio zonaCobranzaServicio;

    public PerfilServicio getPerfilServicio() {
        return perfilServicio;
    }

    public void setPerfilServicio(PerfilServicio perfilServicio) {
        this.perfilServicio = perfilServicio;
    }

    private List<Empleado> listaEmpleados;
    private LazyDataModel<Empleado> listaLazyEmpleados;
    private List<TipoDocumentoIdentidad> listaTipoDocumentoIdentidad;
    private List<Sede> listaSede;
    private List<Perfil> listaPerfil;
    

    public ZonaCobranzaServicio getZonaCobranzaServicio() {
        return zonaCobranzaServicio;
    }

    public void setZonaCobranzaServicio(ZonaCobranzaServicio zonaCobranzaServicio) {
        this.zonaCobranzaServicio = zonaCobranzaServicio;
    }

    public List<ZonaCobranza> getListaZonaCobranza() {
        return listaZonaCobranza;
    }

    public void setListaZonaCobranza(List<ZonaCobranza> listaZonaCobranza) {
        this.listaZonaCobranza = listaZonaCobranza;
    }
    private List<ZonaCobranza> listaZonaCobranza;

    // Agregar/Modificar Empleado
    private boolean estadoEmpleado;

    // Agregar
    private Empleado empleadoAgregar;

    //Modificar
    private Empleado empleadoModificar;

    //Fltro
    private Empleado empleadoFiltro;

    private String contenidoModalEmpleado = "modal-agregar-empleado.xhtml";
    private int activo;
    private int inactivo;
    
    //Cantidad de prestamos asignados
    private int cantidadPrestamos;

    public int getCantidadPrestamos() {
        return cantidadPrestamos;
    }

    public void setCantidadPrestamos(int cantidadPrestamos) {
        this.cantidadPrestamos = cantidadPrestamos;
    }

    public void iniciar()
    {
        setEmpleadoFiltro(new Empleado());
        iniciarListaSede();
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        
        iniciarListaEmpleados();
        iniciarListaTipoDocumento();        
        auditoria(EnumEntidadAuditoria.EMPLEADO.getId(),EnumTipoAuditoria.ACCESO.getId(),EnumTipoOperacionAuditoria.OPCION.getId(),null);
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }
    
    public void auditoria(int entidadId , String tipoId, String operacionId, Integer registroId) {

        /*Registro de auditoria al sistema*/
        /*
            @ManagedProperty("#{auditoriaServicio}")
            private AuditoriaServicio auditoriaServicio;
    
            @ManagedProperty("#{usuarioServicio}")
            private UsuarioServicio usuarioServicio;

            @ManagedProperty("#{empleadoServicio}")
            private EmpleadoServicio empleadoServicio;
        
            auditoria(EnumEntidadAuditoria.SISTEMAS.getId(),EnumTipoAuditoria.ACCESO.getId(),EnumTipoOperacionAuditoria.INGRESO.getId());
        
         */
        try {
            Auditoria auditoria = new Auditoria();
            auditoria.setTipo(tipoId);
            Entidad entidad = new Entidad();
            entidad.setId(entidadId);
            auditoria.setEntidadId(entidad);
            auditoria.setTipoOperacion(operacionId);
            Date fechaActual = new Date();
            auditoria.setFechaOperacion(fechaActual);
            Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            auditoria.setUsuarioId(usuario);
            Empleado empleado = empleadoServicio.obtenerPorCodigo(usuario.getIdEmpleado().getId());
            auditoria.setSedeId(empleado.getIdSede());
            auditoria.setRegistroId(registroId);
            auditoriaServicio.guardar(auditoria);
        } catch (Exception e) {
        }
    }
    public void auditoriaEmpleado(Empleado empleado,String operacionId){
        try {
            EmpleadoAuditoria empleadoAuditoria = new EmpleadoAuditoria();
            empleadoAuditoria.setIdCodigo(empleado.getId());            
            empleadoAuditoria.setApellidoMaterno(empleado.getApellidoMaterno());
            empleadoAuditoria.setApellidoPaterno(empleado.getApellidoPaterno());
            empleadoAuditoria.setConocimientoInformatico(empleado.getConocimientoInformatico());
            empleadoAuditoria.setCorreo(empleado.getCorreo());            
            empleadoAuditoria.setDireccion(empleado.getDireccion());
            empleadoAuditoria.setEstadoCivil(empleado.getEstadoCivil());
            
            empleadoAuditoria.setGradoInstruccion(empleado.getGradoInstruccion());
            empleadoAuditoria.setIdEstado(empleado.getIdEstado());
            empleadoAuditoria.setIdSede(empleado.getIdSede());
            empleadoAuditoria.setIdTipoDocumentoIdentidad(empleado.getIdTipoDocumentoIdentidad());
            empleadoAuditoria.setNombre(empleado.getNombre());
            empleadoAuditoria.setNumeroDocumento(empleado.getNumeroDocumento());
            empleadoAuditoria.setTelefono(empleado.getTelefono());
            
            empleadoAuditoria.setTipoOperacion(operacionId);
            Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            empleadoAuditoria.setUsuarioId(usuario);
            empleadoAuditoria.setFechaOperacion(new Date());            
            empleadoAuditoriaServicio.guardar(empleadoAuditoria);
        } catch (Exception e) {
            
            LOG.error("error auditoria empleado: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
            
        }
        
        
    }
    
    public boolean isSoporte() throws Exception {
        List<PerfilPorUsuario> perfilPorUsuarios = perfilPorUsuarioServicio.getByUserId(Faces.getUserInSession().getId());
        return perfilPorUsuarios.stream().anyMatch((perfilPorUsuario) -> (perfilPorUsuario.getIdPerfil().getId() == EnumPerfil.SOPORTE.getId()));
    }    

    public void iniciarListaEmpleados()
    {
        try
        {
            Empleado empleadoFiltroInicio = new Empleado();
            empleadoFiltroInicio.getIdEstado().setId(EnumEstado.ACTIVO.getId());
            empleadoFiltroInicio.setNombre("");
            empleadoFiltroInicio.setNumeroDocumento("");
            Usuario usuario=usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            Empleado empleado=empleadoServicio.obtenerPorCodigo((usuario).getIdEmpleado().getId());            
            if (isSoporte()) {
                Sede sede = new Sede();
                empleadoFiltroInicio.setIdSede(sede);
            } else {
                empleadoFiltroInicio.setIdSede(empleado.getIdSede());
            }
            
            listaLazyEmpleados = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<Empleado> listaEmpleados = new ArrayList<>();
                    try
                    {
                        empleadoFiltroInicio.setFirst(first);
                        empleadoFiltroInicio.setPageSize(pageSize);
                        listaEmpleados = getEmpleadoServicio().listarFiltro(empleadoFiltroInicio);
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaEmpleados;
                }
            };
            listaLazyEmpleados.setRowCount(getEmpleadoServicio().contarFiltro(empleadoFiltroInicio));
            Faces.getRequestContext().update("formListaEmpleados:dataTableEmpleado");
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaTipoDocumento()
    {
        try
        {
            setListaTipoDocumentoIdentidad(getTipoDocumentoIdentidadServicio().obtenerTodo());
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaSede()
    {
        try
        {
            setListaSede(getSedeServicio().obtenerTodo());
            /*Sede en el menú derecho*/
            Usuario usuario=usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            Empleado empleado=empleadoServicio.obtenerPorCodigo((usuario).getIdEmpleado().getId());
            if(isSoporte()){
                Sede sede = new Sede();
                getEmpleadoFiltro().setIdSede(sede);
            }else{
                getEmpleadoFiltro().setIdSede(empleado.getIdSede());
            }
            
            
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void filtrar()
    {
        try
        {
            listaLazyEmpleados = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<Empleado> listaEmpleados = new ArrayList<>();
                    try
                    {
                        getEmpleadoFiltro().setFirst(first);
                        getEmpleadoFiltro().setPageSize(pageSize);
                        listaEmpleados = getEmpleadoServicio().listarFiltro(getEmpleadoFiltro());
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaEmpleados;
                }
            };
            listaLazyEmpleados.setRowCount(getEmpleadoServicio().contarFiltro(getEmpleadoFiltro()));
            Faces.getRequestContext().update("formListaEmpleados");
            Faces.getRequestContext().update("formListaEmpleados:dataTableEmpleado");
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarEmpleado()
    {
        try
        {
            setEstadoEmpleado(true);
            setEmpleadoAgregar(new Empleado());
            Usuario usuario=usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            Empleado empleado=empleadoServicio.obtenerPorCodigo(usuario.getIdEmpleado().getId());            
            getEmpleadoAgregar().setIdSede(empleado.getIdSede());
            
            
            Faces.getRequestContext().update("divModalEmpleado");
            setContenidoModalEmpleado("modal-agregar-empleado.xhtml");
            Faces.getRequestContext().update("formEmpleado");
            Faces.getRequestContext().execute("show('#modalEmpleado');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarEmpleado()
    {
        try
        {
            if (isEstadoEmpleado())
            {
                getEmpleadoAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getEmpleadoAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getEmpleadoServicio().guardar(getEmpleadoAgregar());
            
            auditoria(EnumEntidadAuditoria.EMPLEADO.getId(),EnumTipoAuditoria.DATOS.getId(),EnumTipoOperacionAuditoria.REGISTRO.getId(),getEmpleadoAgregar().getId());
            auditoriaEmpleado(getEmpleadoAgregar(), EnumTipoOperacionAuditoria.REGISTRO.getId());
            
            Faces.getRequestContext().execute("hide('#modalEmpleado');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente al empleado.", FacesMessage.SEVERITY_INFO);
            iniciarListaEmpleados();
            iniciarListaSede();
            Faces.getRequestContext().update("formListaEmpleados");
            Faces.getRequestContext().update("formListaEmpleados:dataTableEmpleado");
        } catch (ConstraintViolationException e)
        {
            Faces.addMessage("Atención", "Existe un Empleado registrado con ese número de documento en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }
    

    /**
     * Cargar Datos del Empleado
     * <p>
     * @param empleado
     */
    public void mostrarModalModificarEmpleado(Empleado empleado)
    {
        try
        {
            
            
            
            setEmpleadoModificar(empleado);
            
            
            Usuario usuario = new Usuario();
            usuario.setIdEmpleado(empleado);
            
            //Perfil asignado
            usuario = usuarioServicio.obtenerPorIdEmpleado(usuario);            
            PerfilPorUsuario perfilPorUsuario = new PerfilPorUsuario();
            perfilPorUsuario.setIdUsuario(usuario);
            List<PerfilPorUsuario> listaPerfilPorUsuario;
            listaPerfilPorUsuario=perfilPorUsuarioServicio.obtenerTodosPorUsuario(perfilPorUsuario);
            List<Perfil> listaPerfilAsignado= new ArrayList<>();
            for(PerfilPorUsuario obj:listaPerfilPorUsuario){                
                listaPerfilAsignado.add(obj.getIdPerfil());
            }
            setListaPerfil(listaPerfilAsignado);
            
            //Zona de Cobranza
            Prestamo prestamo=new Prestamo();
            prestamo.setIdEmpleado(empleado);
            List<Prestamo> listaPrestamo;
            listaPrestamo=prestamoServicio.listarFiltro(prestamo);
            List<ZonaCobranza> listaZonaCobranzaAsignada= new ArrayList<>();
            
            for(Prestamo objPrestamo:listaPrestamo){                
                System.out.println("Prestamo: "+objPrestamo.getIdCliente().getIdZonaCobranza().getNombre());
                boolean control=true;
                for(ZonaCobranza objZona:listaZonaCobranzaAsignada){
                    if (objPrestamo.getIdCliente().getIdZonaCobranza().getId()==objZona.getId()){                        
                        control=false;
                    }
                }
                if (control){
                    listaZonaCobranzaAsignada.add(objPrestamo.getIdCliente().getIdZonaCobranza());
                }
            }
            setListaZonaCobranza(listaZonaCobranzaAsignada);
            //Cantidad de prestamos
            int numeroPrestamos=0;
            List<Cliente> listaCliente = new ArrayList<>();
            for(Prestamo objPrestamo:listaPrestamo){                
                boolean control=true;
                for(Cliente objCliente:listaCliente){
                    if (objPrestamo.getIdCliente().getId()==objCliente.getId()){                        
                        control=false;
                    }
                }
                if(control){
                    numeroPrestamos++;
                    listaCliente.add(objPrestamo.getIdCliente());
                }
            }
            setCantidadPrestamos(numeroPrestamos);
            
            
            
            
            
            if (getEmpleadoModificar().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoEmpleado(true);
            } else
            {
                setEstadoEmpleado(false);
            }
            Faces.getRequestContext().update("divModalEmpleado");
            setContenidoModalEmpleado("modal-modificar-empleado.xhtml");
            Faces.getRequestContext().update("formEmpleado");
            Faces.getRequestContext().execute("show('#modalEmpleado');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public List<Perfil> getListaPerfil() {
        return listaPerfil;
    }

    public void setListaPerfil(List<Perfil> listaPerfil) {
        this.listaPerfil = listaPerfil;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarEmpleado()
    {
        try
        {
            if (isEstadoEmpleado())
            {
                getEmpleadoModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getEmpleadoModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getEmpleadoServicio().actualizar(getEmpleadoModificar());
            auditoria(EnumEntidadAuditoria.EMPLEADO.getId(),EnumTipoAuditoria.DATOS.getId(),EnumTipoOperacionAuditoria.MODIFICACION.getId(),getEmpleadoModificar().getId());
            auditoriaEmpleado(getEmpleadoModificar(), EnumTipoOperacionAuditoria.MODIFICACION.getId());
            Faces.getRequestContext().execute("hide('#modalEmpleado');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente los datos del empleado "
                    + getEmpleadoModificar().getNombre(), FacesMessage.SEVERITY_INFO);
            iniciarListaEmpleados();
            Faces.getRequestContext().update("formListaEmpleados");
            Faces.getRequestContext().update("formListaEmpleados:dataTableEmpleado");
        } catch (DataIntegrityViolationException e)
        {
            Faces.addMessage("", "Existe un Empleado registrado con esa número de documento en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarEmpleado(Empleado empleado)
    {
        setEmpleadoModificar(empleado);
        Faces.getRequestContext().update("divModalEmpleado");
        setContenidoModalEmpleado("modal-inhabilitar-empleado.xhtml");
        Faces.getRequestContext().update("formEmpleado");
        Faces.getRequestContext().execute("show('#modalEmpleado');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarEmpleado()
    {
        try
        {
            getEmpleadoModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getEmpleadoServicio().actualizar(getEmpleadoModificar());

            Usuario usuarioEliminar = new Usuario();
            usuarioEliminar.setIdEmpleado(getEmpleadoModificar());

            Usuario usuarioFiltro = getUsuarioServicio().obtenerPorIdEmpleado(usuarioEliminar);

            if (usuarioFiltro != null)
            {
                usuarioFiltro.getIdEstado().setId(EnumEstado.INACTIVO.getId());
                usuarioEliminar = usuarioFiltro;
                getUsuarioServicio().actualizar(usuarioEliminar);
            }
            Faces.getRequestContext().execute("hide('#modalEmpleado');");
            Faces.addMessage("¡Atención!",
                    "El empleado ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaEmpleados();
            Faces.getRequestContext().update("formListaEmpleados");
            Faces.getRequestContext().update("formListaEmpleados:dataTableEmpleado");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarEmpleado(Empleado empleado)
    {
        setEmpleadoModificar(empleado);
        Faces.getRequestContext().update("divModalEmpleado");
        setContenidoModalEmpleado("modal-habilitar-empleado.xhtml");
        Faces.getRequestContext().update("formEmpleado");
        Faces.getRequestContext().execute("show('#modalEmpleado');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarEmpleado()
    {
        try
        {
            getEmpleadoModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getEmpleadoServicio().actualizar(getEmpleadoModificar());

            Usuario usuarioEliminar = new Usuario();
            usuarioEliminar.setIdEmpleado(getEmpleadoModificar());

            Usuario usuarioFiltro = getUsuarioServicio().obtenerPorIdEmpleado(usuarioEliminar);

            if (usuarioFiltro != null)
            {
                usuarioFiltro.getIdEstado().setId(EnumEstado.ACTIVO.getId());
                usuarioEliminar = usuarioFiltro;
                getUsuarioServicio().actualizar(usuarioEliminar);
            }
            Faces.getRequestContext().execute("hide('#modalEmpleado');");
            Faces.addMessage("¡Atención!",
                    "El empleado ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaEmpleados();
            Faces.getRequestContext().update("formListaEmpleados");
            Faces.getRequestContext().update("formListaEmpleados:dataTableEmpleado");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarEmpleado(Empleado empleado)
    {
        setEmpleadoModificar(empleado);
        Faces.getRequestContext().update("divModalEmpleado");
        setContenidoModalEmpleado("modal-eliminar-empleado.xhtml");
        Faces.getRequestContext().update("formEmpleado");
        Faces.getRequestContext().execute("show('#modalEmpleado');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarEmpleado()
    {
        try
        {
            
            getEmpleadoServicio().eliminar(getEmpleadoModificar());
            auditoria(EnumEntidadAuditoria.EMPLEADO.getId(),EnumTipoAuditoria.DATOS.getId(),EnumTipoOperacionAuditoria.ELIMINACION.getId(),getEmpleadoModificar().getId());
            auditoriaEmpleado(getEmpleadoModificar(), EnumTipoOperacionAuditoria.ELIMINACION.getId());
            Faces.getRequestContext().execute("hide('#modalEmpleado');");
            Faces.addMessage("¡Atención!",
                    "El empleado ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaEmpleados();
            Faces.getRequestContext().update("formListaEmpleados");
            Faces.getRequestContext().update("formListaEmpleados:dataTableEmpleado");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public String getContenidoModalEmpleado()
    {
        return contenidoModalEmpleado;
    }

    public void setContenidoModalEmpleado(String contenidoModalEmpleado)
    {
        this.contenidoModalEmpleado = contenidoModalEmpleado;
    }

    public EmpleadoServicio getEmpleadoServicio()
    {
        return empleadoServicio;
    }

    public void setEmpleadoServicio(EmpleadoServicio empleadoServicio)
    {
        this.empleadoServicio = empleadoServicio;
    }

    public boolean isEstadoEmpleado()
    {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(boolean estadoEmpleado)
    {
        this.estadoEmpleado = estadoEmpleado;
    }

    public List<Empleado> getListaEmpleados()
    {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados)
    {
        this.listaEmpleados = listaEmpleados;
    }

    public Empleado getEmpleadoAgregar()
    {
        return empleadoAgregar;
    }

    public void setEmpleadoAgregar(Empleado empleadoAgregar)
    {
        this.empleadoAgregar = empleadoAgregar;
    }

    public Empleado getEmpleadoModificar()
    {
        return empleadoModificar;
    }

    public void setEmpleadoModificar(Empleado empleadoModificar)
    {
        this.empleadoModificar = empleadoModificar;
    }

    public List<TipoDocumentoIdentidad> getListaTipoDocumentoIdentidad()
    {
        return listaTipoDocumentoIdentidad;
    }

    public void setListaTipoDocumentoIdentidad(List<TipoDocumentoIdentidad> listaTipoDocumentoIdentidad)
    {
        this.listaTipoDocumentoIdentidad = listaTipoDocumentoIdentidad;
    }

    public TipoDocumentoIdentidadServicio getTipoDocumentoIdentidadServicio()
    {
        return tipoDocumentoIdentidadServicio;
    }

    public void setTipoDocumentoIdentidadServicio(TipoDocumentoIdentidadServicio tipoDocumentoIdentidadServicio)
    {
        this.tipoDocumentoIdentidadServicio = tipoDocumentoIdentidadServicio;
    }

    public UsuarioServicio getUsuarioServicio()
    {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio)
    {
        this.usuarioServicio = usuarioServicio;
    }

    public int getActivo()
    {
        return activo;
    }

    public void setActivo(int activo)
    {
        this.activo = activo;
    }

    public int getInactivo()
    {
        return inactivo;
    }

    public void setInactivo(int inactivo)
    {
        this.inactivo = inactivo;
    }

    public Empleado getEmpleadoFiltro()
    {
        return empleadoFiltro;
    }

    public void setEmpleadoFiltro(Empleado empleadoFiltro)
    {
        this.empleadoFiltro = empleadoFiltro;
    }

    public LazyDataModel<Empleado> getListaLazyEmpleados()
    {
        return listaLazyEmpleados;
    }

    public void setListaLazyEmpleados(LazyDataModel<Empleado> listaLazyEmpleados)
    {
        this.listaLazyEmpleados = listaLazyEmpleados;
    }

    public SedeServicio getSedeServicio()
    {
        return sedeServicio;
    }

    public void setSedeServicio(SedeServicio sedeServicio)
    {
        this.sedeServicio = sedeServicio;
    }

    public List<Sede> getListaSede()
    {
        return listaSede;
    }

    public void setListaSede(List<Sede> listaSede)
    {
        this.listaSede = listaSede;
    }

}
    // </editor-fold>
