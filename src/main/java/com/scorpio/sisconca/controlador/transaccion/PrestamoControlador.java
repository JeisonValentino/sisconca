package com.scorpio.sisconca.controlador.transaccion;

import com.scorpio.sisconca.entidad.Prestamo;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.Cliente;
import com.scorpio.sisconca.entidad.Estado;
import com.scorpio.sisconca.entidad.Artefacto;
import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.DatoComercial;
import com.scorpio.sisconca.entidad.Entidad;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.PrestamoAuditoria;
import com.scorpio.sisconca.entidad.Sede;
import com.scorpio.sisconca.entidad.TipoPrestamo;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.servicio.transaccion.PrestamoServicio;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.maestros.ClienteServicio;
import com.scorpio.sisconca.servicio.seguridad.EstadoServicio;
import com.scorpio.sisconca.servicio.seguridad.ArtefactoServicio;
import com.scorpio.sisconca.servicio.seguridad.AuditoriaServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.SedeServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.servicio.transaccion.DatoComercialServicio;
import com.scorpio.sisconca.servicio.transaccion.PrestamoAuditoriaServicio;
import com.scorpio.sisconca.servicio.transaccion.TipoPrestamoServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEntidadAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import com.scorpio.sisconca.utilitario.enums.EnumTipoAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumTipoOperacionAuditoria;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.transaction.annotation.Transactional;

@ManagedBean
@SessionScoped
public class PrestamoControlador implements Serializable
{

    // quién te ha dicho que una variable tiene que ir en matyúsculas?
    // por qué static?? es private!!
    private final Logger log = Logger.getLogger(PrestamoControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{prestamoServicio}")
    private PrestamoServicio prestamoServicio;
    
    @ManagedProperty("#{sedeServicio}")
    private SedeServicio sedeServicio;
    
    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;
    
    @ManagedProperty("#{auditoriaServicio}")
    private AuditoriaServicio auditoriaServicio;
    
    @ManagedProperty("#{prestamoAuditoriaServicio}")
    private PrestamoAuditoriaServicio prestamoAuditoriaServicio;

    public PrestamoAuditoriaServicio getPrestamoAuditoriaServicio() {
        return prestamoAuditoriaServicio;
    }

    public void setPrestamoAuditoriaServicio(PrestamoAuditoriaServicio prestamoAuditoriaServicio) {
        this.prestamoAuditoriaServicio = prestamoAuditoriaServicio;
    }
    
    

    public AuditoriaServicio getAuditoriaServicio() {
        return auditoriaServicio;
    }

    public void setAuditoriaServicio(AuditoriaServicio auditoriaServicio) {
        this.auditoriaServicio = auditoriaServicio;
    }
    

    public PerfilPorUsuarioServicio getPerfilPorUsuarioServicio() {
        return perfilPorUsuarioServicio;
    }

    public void setPerfilPorUsuarioServicio(PerfilPorUsuarioServicio perfilPorUsuarioServicio) {
        this.perfilPorUsuarioServicio = perfilPorUsuarioServicio;
    }

    public SedeServicio getSedeServicio() {
        return sedeServicio;
    }

    public void setSedeServicio(SedeServicio sedeServicio) {
        this.sedeServicio = sedeServicio;
    }

    @ManagedProperty("#{clienteServicio}")
    private ClienteServicio clienteServicio;

    @ManagedProperty("#{estadoServicio}")
    private EstadoServicio estadoServicio;

    @ManagedProperty("#{artefactoServicio}")
    private ArtefactoServicio artefactoServicio;
    
    
    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;

    public UsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }
    

    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;

    @ManagedProperty("#{tipoPrestamoServicio}")
    private TipoPrestamoServicio tipoPrestamoServicio;
    
    @ManagedProperty("#{datoComercialServicio}")
    private DatoComercialServicio datoComercialServicio;

    private List<Prestamo> listaPrestamos;
    private LazyDataModel<Prestamo> listaLazyPrestamos;
    private List<Cliente> listaCliente;
    private List<Estado> listaEstado;
    private List<Artefacto> listaArtefacto;
    private List<Empleado> listaEmpleado;
    private List<TipoPrestamo> listaTipoPrestamo;
    private List<Prestamo> prestamos;
    private List<Sede> listaSede;

    public List<Sede> getListaSede() {
        return listaSede;
    }

    public void setListaSede(List<Sede> listaSede) {
        this.listaSede = listaSede;
    }
    private Cliente garante;

    private double totalAmountLoans = 0;

    private List<Cliente> clientes;

    // Agregar/Modificar Prestamo
    private boolean estadoPrestamo;

    // Agregar
    private Prestamo prestamoAgregar;

    //Modificar
    private Prestamo prestamoModificar;

    //Fltro
    private Prestamo prestamoFiltro;

    private String contenidoModalPrestamo = "modal-agregar-prestamo.xhtml";
    private int activo;
    private int inactivo;

    private Date filterStart;
    private Date filterEnd;

//    private int newPrestamoTime = 0;
//    private int newPrestamoRate = 0;
    private boolean isNewPrestamo;

    private TipoPrestamo tipoPrestamo;
    
    private DatoComercial datoComercial;

    public DatoComercialServicio getDatoComercialServicio() {
        return datoComercialServicio;
    }

    public void setDatoComercialServicio(DatoComercialServicio datoComercialServicio) {
        this.datoComercialServicio = datoComercialServicio;
    }

    public DatoComercial getDatoComercial() {
        return datoComercial;
    }

    public void setDatoComercial(DatoComercial datoComercial) {
        this.datoComercial = datoComercial;
    }

    public void iniciar()
    {
        setPrestamoFiltro(new Prestamo());
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, 1);
        getPrestamoFiltro().setFilterStart(today.getTime());
        getPrestamoFiltro().setFilterEnd(Calendar.getInstance().getTime());
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        iniciarListaPrestamos();
        iniciarListaSede();
        iniciarListaCliente();
        iniciarListaEstado();
        iniciarListaArtefacto();
        iniciarListaEmpleado();
        iniciarListaTipoPrestamo();
        listaReportePrestamoControlador();
        auditoria(EnumEntidadAuditoria.PRESTAMO.getId(),EnumTipoAuditoria.ACCESO.getId(),EnumTipoOperacionAuditoria.OPCION.getId(),null);
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
    
    public void auditoriaPrestamo(Prestamo prestamo,String operacionId) {
        
        try {
            PrestamoAuditoria prestamoAuditoria = new PrestamoAuditoria();
            
            Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            
            prestamoAuditoria.setUsuarioId(usuario);
            prestamoAuditoria.setFechaOperacion(new Date());
            prestamoAuditoria.setTipoOperacion(operacionId);
            prestamoAuditoria.setIdCodigo(prestamo.getId());            
            prestamoAuditoria.setFecha(prestamo.getFecha());
            prestamoAuditoria.setPrestamo(prestamo.getPrestamo());
            prestamoAuditoria.setCuota(prestamo.getCuota());
            prestamoAuditoria.setMontoPagar(prestamo.getMontoPagar());
            prestamoAuditoria.setIdEmpleado(prestamo.getIdEmpleado());
            prestamoAuditoria.setIdEstado(prestamo.getIdEstado());
            prestamoAuditoria.setIdArtefacto(prestamo.getIdArtefacto());
            prestamoAuditoria.setIdTipoPrestamo(prestamo.getIdTipoPrestamo());
            prestamoAuditoria.setIdCliente(prestamo.getIdCliente());
            prestamoAuditoria.setGarante(prestamo.getGarante());
            prestamoAuditoria.setFrecuencia(prestamo.getFrecuencia());
            prestamoAuditoria.setNumeroRenovacion(prestamo.getNumeroRenovacion());
            prestamoAuditoria.setFechaRenovacion(prestamo.getFechaRenovacion());
            prestamoAuditoria.setIndicadorRenovacion(prestamo.getIndicadorRenovacion());
            prestamoAuditoria.setInteres(prestamo.getInteres());
            prestamoAuditoria.setLugarEntrega(prestamo.getLugarEntrega());
            prestamoAuditoria.setTipoMoneda(prestamo.getTipoMoneda());
            prestamoAuditoria.setTipoGarantia(prestamo.getTipoGarantia());
            prestamoAuditoria.setIdSede(prestamo.getIdSede());
             
            prestamoAuditoriaServicio.guardar(prestamoAuditoria);
            
            
            
        } catch (Exception e) {

            log.error("error auditoria prestamo: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }
    
    public boolean isSoporte() throws Exception {
        List<PerfilPorUsuario> perfilPorUsuarios = perfilPorUsuarioServicio.getByUserId(Faces.getUserInSession().getId());
        return perfilPorUsuarios.stream().anyMatch((perfilPorUsuario) -> (perfilPorUsuario.getIdPerfil().getId() == EnumPerfil.SOPORTE.getId()));
    }
	
    
    public void iniciarListaSede()
    {
        try
        {
            setListaSede(getSedeServicio().obtenerTodo());
            
            if(!isSoporte()){
                /*Para sede en el menú derecho*/
                Usuario usuario=usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
                Empleado empleado=empleadoServicio.obtenerPorCodigo((usuario).getIdEmpleado().getId());
                getPrestamoFiltro().setIdSede(empleado.getIdSede());
            }
            
                        
            
        } catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaPrestamos()
    {
        try
        {
            totalAmountLoans = 0;
            Prestamo prestamoFiltroInicio = new Prestamo();
            prestamoFiltroInicio.getIdEstado().setId(EnumEstado.ACTIVO.getId());
            if(!isSoporte()){
                Usuario usuario=usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
                Empleado empleado=empleadoServicio.obtenerPorCodigo(usuario.getIdEmpleado().getId());      
                prestamoFiltroInicio.setIdSede(empleado.getIdSede());
            }
            
            listaLazyPrestamos = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<Prestamo> listaPrestamos = new ArrayList<>();
                    Calendar today = Calendar.getInstance();
                    today.set(Calendar.DAY_OF_MONTH, 1);
                    filterStart = today.getTime();
                    filterEnd = Calendar.getInstance().getTime();
                    prestamoFiltroInicio.setFilterStart(today.getTime());
                    prestamoFiltroInicio.setFilterEnd(Calendar.getInstance().getTime());
                    prestamoFiltroInicio.setFirst(first);
                    prestamoFiltroInicio.setPageSize(pageSize);
                    
                    totalAmountLoans = getPrestamoServicio().getTotalAmount(prestamoFiltro);
                    try
                    {
                        listaPrestamos = getPrestamoServicio().listarFiltro(prestamoFiltroInicio);

                    }
                    catch (Exception e)
                    {
                        log.error("error: " + e);
                    }
                    return listaPrestamos;
                }
            };
            listaLazyPrestamos.setRowCount(getPrestamoServicio().contarFiltro(prestamoFiltroInicio));
            getTotalAmountOfLoans();
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void listaReportePrestamoControlador()
    {
        try
        {
            clientes = clienteServicio.obtenerTodo();
            prestamos = new ArrayList<>();
            List<Object> result = (List<Object>) getPrestamoServicio().getListaPrestamosPorCobrar(prestamoFiltro);
            Iterator itr = result.iterator();
            while (itr.hasNext())
            {
                Object[] obj = (Object[]) itr.next();

//                for (int i = 0; i < obj.length; i++)
//                {
//                    System.out.println(i + " : " + obj[i]);
//                }
//                for (Object object : obj)
//                {
//                    System.out.println("R: " + object);
//                }
                Prestamo pp = new Prestamo();
                pp.setId((int) obj[0]);
                pp.setFecha((Date) obj[1]);
                pp.setPrestamo((double) obj[2]);
                pp.setCuota((double) obj[3]);
                pp.setMontoPagar((double) obj[4]);
                Empleado e = empleadoServicio.obtenerPorCodigo((int) obj[5]);
                pp.setIdEmpleado(e);
                pp.setIdEstado(new Estado((int) obj[6]));
                pp.setIdArtefacto(new Artefacto((int) obj[7]));
                pp.setIdTipoPrestamo(new TipoPrestamo((int) obj[8]));
                Cliente c = clienteServicio.obtenerPorCodigo((int) obj[9]);
                pp.setIdCliente(c);
                pp.setGarante(c.getId());
                pp.setFrecuencia((Integer) obj[11]);
                pp.setNumeroRenovacion((Integer) obj[12]);
                pp.setFechaRenovacion((Date) obj[13]);
                pp.setIndicadorRenovacion((Character) obj[14]);
                pp.setInteres((Integer) obj[15]);
                pp.setPagado((Double) obj[16]);
                prestamos.add(pp);
            }
            Faces.getRequestContext().update("formListaPrestamos");
            Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void iniciarListaCliente()
    {
        try
        {
            setListaCliente(getClienteServicio().obtenerTodo());
            
           
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaEstado()
    {
        try
        {
            setListaEstado(getEstadoServicio().obtenerTodo());
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaArtefacto()
    {
        try
        {
            setListaArtefacto(getArtefactoServicio().obtenerTodo());
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaEmpleado()
    {
        try
        {
            setListaEmpleado(getEmpleadoServicio().obtenerTodo());
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaTipoPrestamo()
    {
        try
        {
            setListaTipoPrestamo(getTipoPrestamoServicio().obtenerTodo());
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void filter()
    {
        totalAmountLoans = 0;
        log.info(prestamoFiltro.getFilterStart() == null ? "null" : prestamoFiltro.getFilterStart());
        log.info(prestamoFiltro.getFilterEnd() == null ? "null" : prestamoFiltro.getFilterEnd());
        log.info(prestamoFiltro.getFilterEnd() == null ? "null" : prestamoFiltro.getFilterEstado());
        try
        {
            listaLazyPrestamos = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<Prestamo> listaPrestamos = new ArrayList<>();
                    try
                    {
                        getPrestamoFiltro().setFirst(first);
                        getPrestamoFiltro().setPageSize(pageSize);
                        listaPrestamos = getPrestamoServicio().listarFiltro(getPrestamoFiltro());
                        totalAmountLoans = getPrestamoServicio().getTotalAmount(prestamoFiltro);
                    }
                    catch (Exception e)
                    {
                        log.error("error: " + e);
                    }
                    return listaPrestamos;
                }
            };

            listaLazyPrestamos.setRowCount(getPrestamoServicio().contarFiltro(getPrestamoFiltro()));
            getTotalAmountOfLoans();
            Faces.getRequestContext().update("formListaPrestamos");
            Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarPrestamo()
    {
        try
        {
            setIsNewPrestamo(true);
            setEstadoPrestamo(true);
            setPrestamoAgregar(new Prestamo());
            getPrestamoAgregar().setIdEstado(new Estado());
            getPrestamoAgregar().setIdCliente(new Cliente());
            getPrestamoAgregar().setIdEmpleado(new Empleado());
            getPrestamoAgregar().setIdTipoPrestamo(new TipoPrestamo());
            
            Usuario usuario=usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            Empleado empleado=empleadoServicio.obtenerPorCodigo(usuario.getIdEmpleado().getId());            
            
            getPrestamoAgregar().setIdSede(empleado.getIdSede());

            if (getListaTipoPrestamo().isEmpty())
            {
                Faces.addMessage("¡ERROR!", "Debe agregar tipos de préstamo antes de agregar un préstamo", FacesMessage.SEVERITY_FATAL);
            }
            else
            {
                prestamoAgregar.setFrecuencia(1);
                
                Faces.getRequestContext().update("divModalPrestamo");
                setContenidoModalPrestamo("modal-agregar-prestamo.xhtml");
                Faces.getRequestContext().update("formPrestamo");
                Faces.getRequestContext().execute("show('#modalPrestamo');");
            }
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarPrestamo()
    {
        try
        {
            boolean guardar=true;
            {
                if(getPrestamoAgregar().getFecha()==null){
                    guardar=false;
                    Faces.addMessage("¡Atención!",
                        "La fecha de préstamo no debe tener valor vacio.", FacesMessage.SEVERITY_INFO);
                }
                if(getPrestamoAgregar().getPrestamo()<=0.00){
                    guardar=false;
                    Faces.addMessage("¡Atención!",
                        "El préstamo debe ser mayor de cero.", FacesMessage.SEVERITY_INFO);
                }
            }
            if (guardar){
                if (isEstadoPrestamo()) {
                    getPrestamoAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
                } else {
                    getPrestamoAgregar().getIdEstado().setId(EnumEstado.RENOVADO.getId());
                }

                getPrestamoServicio().guardar(getPrestamoAgregar());
                auditoria(EnumEntidadAuditoria.PRESTAMO.getId(), EnumTipoAuditoria.DATOS.getId(), EnumTipoOperacionAuditoria.REGISTRO.getId(), getPrestamoAgregar().getId());
                auditoriaPrestamo(getPrestamoAgregar(), EnumTipoOperacionAuditoria.REGISTRO.getId());
                Faces.getRequestContext().execute("hide('#modalPrestamo');");
                Faces.addMessage("¡Atención!",
                        "Se agrego correctamente al préstamo.", FacesMessage.SEVERITY_INFO);
                iniciarListaPrestamos();
                iniciarListaCliente();
                iniciarListaEmpleado();
                Faces.getRequestContext().update("formListaPrestamos");
                Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
            }
            
            
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    /**
     * Cargar Datos del Prestamo
     * <p>
     * @param prestamo
     */
    public void mostrarModalModificarPrestamo(Prestamo prestamo)
    {
        try
        {
            setIsNewPrestamo(false);
            setPrestamoAgregar(prestamo);
            if (getPrestamoAgregar().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoPrestamo(true);
            }
            else
            {
                setEstadoPrestamo(false);
            }
            Faces.getRequestContext().update("divModalPrestamo");
            setContenidoModalPrestamo("modal-agregar-prestamo.xhtml");
            Faces.getRequestContext().update("formPrestamo");
            Faces.getRequestContext().execute("show('#modalPrestamo');");
            /*Mostrar línea de crédito del cliente a partir de datos comerciales*/
            updateListaCliente();
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarPrestamo()
    {
        try
            
        {
            
            boolean guardar = true;
            {
                if (getPrestamoAgregar().getFecha() == null) {
                    guardar = false;
                    Faces.addMessage("¡Atención!",
                            "La fecha de préstamo no debe tener valor vacio.", FacesMessage.SEVERITY_INFO);
                }
                if (getPrestamoAgregar().getPrestamo() <= 0.00) {
                    guardar = false;
                    Faces.addMessage("¡Atención!",
                            "El préstamo debe ser mayor de cero.", FacesMessage.SEVERITY_INFO);
                }
            }
            if (guardar) {
                if (isEstadoPrestamo()) {
                    getPrestamoAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
                } else {
                    getPrestamoAgregar().getIdEstado().setId(EnumEstado.RENOVADO.getId());
                }
                getPrestamoServicio().actualizar(getPrestamoAgregar());
                auditoria(EnumEntidadAuditoria.PRESTAMO.getId(), EnumTipoAuditoria.DATOS.getId(), EnumTipoOperacionAuditoria.MODIFICACION.getId(), getPrestamoAgregar().getId());
                auditoriaPrestamo(getPrestamoAgregar(), EnumTipoOperacionAuditoria.MODIFICACION.getId());
                Faces.getRequestContext().execute("hide('#modalPrestamo');");
                Faces.addMessage("¡Atención!",
                        "Se actualizo correctamente los datos del préstamo "
                        + getPrestamoAgregar().getId(), FacesMessage.SEVERITY_INFO);
                iniciarListaPrestamos();
                Faces.getRequestContext().update("formListaPrestamos");
                Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
            }

            
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarPrestamo(Prestamo prestamo)
    {
        try
        {
            
            setPrestamoModificar(prestamo);
            garante = clienteServicio.obtenerPorCodigo(prestamo.getGarante());
            Faces.getRequestContext().update("divModalPrestamo");
            setContenidoModalPrestamo("modal-inhabilitar-prestamo.xhtml");
            Faces.getRequestContext().update("formPrestamo");
            Faces.getRequestContext().execute("show('#modalPrestamo');");
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PrestamoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarPrestamo()
    {
        try
        {
            getPrestamoModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getPrestamoServicio().actualizar(getPrestamoModificar());

            Faces.getRequestContext().execute("hide('#modalPrestamo');");
            Faces.addMessage("¡Atención!",
                    "El préstamo ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaPrestamos();
            Faces.getRequestContext().update("formListaPrestamos");
            Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarPrestamo(Prestamo prestamo)
    {
        setPrestamoModificar(prestamo);
        Faces.getRequestContext().update("divModalPrestamo");
        setContenidoModalPrestamo("modal-habilitar-prestamo.xhtml");
        Faces.getRequestContext().update("formPrestamo");
        Faces.getRequestContext().execute("show('#modalPrestamo');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarPrestamo()
    {
        try
        {
            getPrestamoModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getPrestamoServicio().actualizar(getPrestamoModificar());

            Faces.getRequestContext().execute("hide('#modalPrestamo');");
            Faces.addMessage("¡Atención!",
                    "El préstamo ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaPrestamos();
            Faces.getRequestContext().update("formListaPrestamos");
            Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        }
        catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarPrestamo(Prestamo prestamo)
    {
        setPrestamoModificar(prestamo);
        Faces.getRequestContext().update("divModalPrestamo");
        setContenidoModalPrestamo("modal-eliminar-prestamo.xhtml");
        Faces.getRequestContext().update("formPrestamo");
        Faces.getRequestContext().execute("show('#modalPrestamo');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarPrestamo()
    {
        try
        {
            System.out.println("Eliminarrrrr");
            System.out.println(getPrestamoModificar().getId());
            getPrestamoServicio().eliminar(getPrestamoModificar());
            auditoria(EnumEntidadAuditoria.PRESTAMO.getId(),EnumTipoAuditoria.DATOS.getId(),EnumTipoOperacionAuditoria.ELIMINACION.getId(),getPrestamoModificar().getId());
            auditoriaPrestamo(getPrestamoModificar(),EnumTipoOperacionAuditoria.ELIMINACION.getId());
            Faces.getRequestContext().execute("hide('#modalPrestamo');");
            Faces.addMessage("¡Atención!",
                    "El préstamo ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaPrestamos();
            Faces.getRequestContext().update("formListaPrestamos");
            Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        }
        catch (Exception ex)
        {
            log.error("error: " + ex);
            Faces.addMessage("¡ERROR!", "No se puede eliminar un préstamo que tiene pagos realizados.", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void calculateQuote()
    {
        if (prestamoAgregar.getInteres() != null && prestamoAgregar.getPrestamo() > 0)
        {
            prestamoAgregar.setMontoPagar(prestamoAgregar.getPrestamo()
                    + (prestamoAgregar.getPrestamo()
                    * prestamoAgregar.getInteres() / 100));
            prestamoAgregar.setCuota(prestamoAgregar.getMontoPagar() / prestamoAgregar.getIdTipoPrestamo().getTiempo());
            Faces.getRequestContext().update("formPrestamo:txtCuota");
            Faces.getRequestContext().update("formPrestamo:txtApagar");
        }
    }

    public void updateTipoPrestamo()
    {
        try
        {
            tipoPrestamo = new TipoPrestamo();
            tipoPrestamo = tipoPrestamoServicio.obtenerPorCodigo(prestamoAgregar.getIdTipoPrestamo().getId());
            prestamoAgregar.setIdTipoPrestamo(tipoPrestamo);
            prestamoAgregar.setFrecuencia(prestamoAgregar.getIdTipoPrestamo().getCobra());
            prestamoAgregar.setInteres(tipoPrestamo.getInteres());
            getPrestamoAgregar().setFrecuencia(tipoPrestamo.getCobra());
            Faces.getRequestContext().update("formPrestamo:txtCuota");
            Faces.getRequestContext().update("formPrestamo:txtApagar");
            Faces.getRequestContext().update("formPrestamo:cobro");
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PrestamoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateListaCliente()
    {
        try
        {
            datoComercial = new DatoComercial();
            datoComercial = datoComercialServicio.obtenerPorIdClienteDatoComercial(prestamoAgregar.getIdCliente().getId());
            setDatoComercial(datoComercial);
            Faces.getRequestContext().update("formPrestamo:lineaCredito");
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(PrestamoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    private void getTotalAmountOfLoans()
    {
        System.out.println("getTotalAmountOfLoans: " + listaLazyPrestamos.getRowCount());
        listaLazyPrestamos.forEach(p -> System.out.println("prestamo: " + p.getPrestamo()));
        for (Prestamo listaLazyPrestamo : listaLazyPrestamos)
        {
            System.out.println(listaLazyPrestamo.getPrestamo());
            totalAmountLoans += listaLazyPrestamo.getPrestamo();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">

    public String getContenidoModalPrestamo()
    {
        return contenidoModalPrestamo;
    }

    public void setContenidoModalPrestamo(String contenidoModalPrestamo)
    {
        this.contenidoModalPrestamo = contenidoModalPrestamo;
    }

    public PrestamoServicio getPrestamoServicio()
    {
        return prestamoServicio;
    }

    public void setPrestamoServicio(PrestamoServicio prestamoServicio)
    {
        this.prestamoServicio = prestamoServicio;
    }

    public boolean isEstadoPrestamo()
    {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(boolean estadoPrestamo)
    {
        this.estadoPrestamo = estadoPrestamo;
    }

    public List<Prestamo> getListaPrestamos()
    {
        return listaPrestamos;
    }

    public void setListaPrestamos(List<Prestamo> listaPrestamos)
    {
        this.listaPrestamos = listaPrestamos;
    }

    public Prestamo getPrestamoAgregar()
    {
        return prestamoAgregar;
    }

    public void setPrestamoAgregar(Prestamo prestamoAgregar)
    {
        this.prestamoAgregar = prestamoAgregar;
    }

    public Prestamo getPrestamoModificar()
    {
        return prestamoModificar;
    }

    public void setPrestamoModificar(Prestamo prestamoModificar)
    {
        this.prestamoModificar = prestamoModificar;
    }

    public List<Cliente> getListaCliente()
    {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente)
    {
        this.listaCliente = listaCliente;
    }

    public ClienteServicio getClienteServicio()
    {
        return clienteServicio;
    }

    public void setClienteServicio(ClienteServicio clienteServicio)
    {
        this.clienteServicio = clienteServicio;
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

    public Prestamo getPrestamoFiltro()
    {
        return prestamoFiltro;
    }

    public void setPrestamoFiltro(Prestamo prestamoFiltro)
    {
        this.prestamoFiltro = prestamoFiltro;
    }

    public LazyDataModel<Prestamo> getListaLazyPrestamos()
    {
        return listaLazyPrestamos;
    }

    public void setListaLazyPrestamos(LazyDataModel<Prestamo> listaLazyPrestamos)
    {
        this.listaLazyPrestamos = listaLazyPrestamos;
    }

    public EstadoServicio getEstadoServicio()
    {
        return estadoServicio;
    }

    public void setEstadoServicio(EstadoServicio estadoServicio)
    {
        this.estadoServicio = estadoServicio;
    }

    public List<Estado> getListaEstado()
    {
        return listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado)
    {
        this.listaEstado = listaEstado;
    }

    public ArtefactoServicio getArtefactoServicio()
    {
        return artefactoServicio;
    }

    public void setArtefactoServicio(ArtefactoServicio artefactoServicio)
    {
        this.artefactoServicio = artefactoServicio;
    }

    public List<Artefacto> getListaArtefacto()
    {
        return listaArtefacto;
    }

    public void setListaArtefacto(List<Artefacto> listaArtefacto)
    {
        this.listaArtefacto = listaArtefacto;
    }

    public EmpleadoServicio getEmpleadoServicio()
    {
        return empleadoServicio;
    }

    public void setEmpleadoServicio(EmpleadoServicio empleadoServicio)
    {
        this.empleadoServicio = empleadoServicio;
    }

    public List<Empleado> getListaEmpleado()
    {
        return listaEmpleado;
    }

    public void setListaEmpleado(List<Empleado> listaEmpleado)
    {
        this.listaEmpleado = listaEmpleado;
    }

    public List<TipoPrestamo> getListaTipoPrestamo()
    {
        return listaTipoPrestamo;
    }

    public void setListaTipoPrestamo(List<TipoPrestamo> listaTipoPrestamo)
    {
        this.listaTipoPrestamo = listaTipoPrestamo;
    }

    public TipoPrestamoServicio getTipoPrestamoServicio()
    {
        return tipoPrestamoServicio;
    }

    public void setTipoPrestamoServicio(TipoPrestamoServicio tipoPrestamoServicio)
    {
        this.tipoPrestamoServicio = tipoPrestamoServicio;
    }

    public Date getFilterStart()
    {
        return filterStart;
    }

    public void setFilterStart(Date filterStart)
    {
        this.filterStart = filterStart;
    }

    public Date getFilterEnd()
    {
        return filterEnd;
    }

    public void setFilterEnd(Date filterEnd)
    {
        this.filterEnd = filterEnd;
    }

    public boolean isIsNewPrestamo()
    {
        return isNewPrestamo;
    }

    public void setIsNewPrestamo(boolean isNewPrestamo)
    {
        this.isNewPrestamo = isNewPrestamo;
    }

    public double getTotalAmountLoans()
    {
        return totalAmountLoans;
    }

    public void setTotalAmountLoans(double totalAmountLoans)
    {
        this.totalAmountLoans = totalAmountLoans;
    }

    public List<Prestamo> getPrestamos()
    {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos)
    {
        this.prestamos = prestamos;
    }

    public List<Cliente> getClientes()
    {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes)
    {
        this.clientes = clientes;
    }

    public Cliente getGarante()
    {
        return garante;
    }

    public void setGarante(Cliente garante)
    {
        this.garante = garante;
    }

    public TipoPrestamo getTipoPrestamo()
    {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(TipoPrestamo tipoPrestamo)
    {
        this.tipoPrestamo = tipoPrestamo;
    }
}
    // </editor-fold>
