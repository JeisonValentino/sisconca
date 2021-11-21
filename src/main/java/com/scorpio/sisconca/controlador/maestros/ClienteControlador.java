package com.scorpio.sisconca.controlador.maestros;

import com.itextpdf.io.IOException;
import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.Cliente;
import com.scorpio.sisconca.entidad.ClienteAuditoria;
import com.scorpio.sisconca.entidad.DatoComercial;
import com.scorpio.sisconca.entidad.Distrito;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.Entidad;
import com.scorpio.sisconca.entidad.Estado;
import com.scorpio.sisconca.entidad.Giro;
import com.scorpio.sisconca.entidad.TipoDocumentoIdentidad;
import com.scorpio.sisconca.entidad.TipoPersona;
import com.scorpio.sisconca.entidad.TipoVivienda;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.entidad.ZonaCobranza;
import com.scorpio.sisconca.servicio.maestros.ClienteAuditoriaServicio;
import com.scorpio.sisconca.servicio.maestros.ClienteServicio;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.mantenimiento.ZonaCobranzaServicio;
import com.scorpio.sisconca.servicio.seguridad.AuditoriaServicio;
import com.scorpio.sisconca.servicio.seguridad.DistritoServicio;
import com.scorpio.sisconca.servicio.seguridad.EstadoServicio;
import com.scorpio.sisconca.servicio.seguridad.TipoDocumentoIdentidadServicio;
import com.scorpio.sisconca.servicio.seguridad.TipoPersonaServicio;
import com.scorpio.sisconca.servicio.seguridad.TipoViviendaServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.servicio.transaccion.DatoComercialServicio;
import com.scorpio.sisconca.servicio.transaccion.GiroServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEntidadAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.enums.EnumTipoAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumTipoOperacionAuditoria;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.rowset.serial.SerialBlob;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@ManagedBean
@SessionScoped
public class ClienteControlador implements Serializable {

    private static final Logger LOG = Logger.getLogger(ClienteControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{clienteServicio}")
    private ClienteServicio clienteServicio;

    @ManagedProperty("#{tipoDocumentoIdentidadServicio}")
    private TipoDocumentoIdentidadServicio tipoDocumentoIdentidadServicio;

    @ManagedProperty("#{tipoPersonaServicio}")
    private TipoPersonaServicio tipoPersonaServicio;

    @ManagedProperty("#{tipoViviendaServicio}")
    private TipoViviendaServicio tipoViviendaServicio;

    @ManagedProperty("#{distritoServicio}")
    private DistritoServicio distritoServicio;

    @ManagedProperty("#{datoComercialServicio}")
    private DatoComercialServicio datoComercialServicio;

    @ManagedProperty("#{zonaCobranzaServicio}")
    private ZonaCobranzaServicio zonaCobranzaServicio;

    @ManagedProperty("#{estadoServicio}")
    private EstadoServicio estadoServicio;

    @ManagedProperty("#{giroServicio}")
    private GiroServicio giroServicio;
    
    @ManagedProperty("#{auditoriaServicio}")
    private AuditoriaServicio auditoriaServicio;

    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;
    
    @ManagedProperty("#{clienteAuditoriaServicio}")
    private ClienteAuditoriaServicio clienteAuditoriaServicio;

    public ClienteAuditoriaServicio getClienteAuditoriaServicio() {
        return clienteAuditoriaServicio;
    }

    public void setClienteAuditoriaServicio(ClienteAuditoriaServicio clienteAuditoriaServicio) {
        this.clienteAuditoriaServicio = clienteAuditoriaServicio;
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

    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;
            
//    @ManagedProperty("#zonaCobranzaServicio")
//    private ZonaCobranzaServicio zonaCobranzaServicio;

    private List<Cliente> listaClientes;
    private List<Cliente> listaAval;

    public List<Cliente> getListaAval() {
        return listaAval;
    }

    public void setListaAval(List<Cliente> listaAval) {
        this.listaAval = listaAval;
    }
    private LazyDataModel<Cliente> listaLazyClientes;
    private List<TipoDocumentoIdentidad> listaTipoDocumentoIdentidad;
    private List<TipoPersona> listaTipoPersona;
    private List<TipoVivienda> listaTipoVivienda;
    private List<Distrito> listaDistrito;
    private List<ZonaCobranza> listaZonaCobranza;
    private List<Estado> listaEstadoSolicitud;
    private List<Giro> listaGiro;
    private UploadedFile file;

    // Agregar/Modificar Cliente
    private boolean estadoCliente;
    private boolean estadoClienteA;

    // Agregar
    private Cliente clienteAgregar;
    private Cliente avalAgregar;
    private DatoComercial datoComercialAgregar;

    //Modificar
    private Cliente clienteModificar;
    private DatoComercial datoComercialModificar;

    private boolean estadoDatoComercial;

    //Fltro
    private Cliente clienteFiltro;

    private String contenidoModalCliente = "modal-agregar-cliente.xhtml";
    private int activo;
    private int inactivo;

    public void iniciar() {
        System.out.println("entro");
        setClienteFiltro(new Cliente());
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        iniciarListaAval();
        iniciarListaClientes();
        iniciarListaTipoDocumento();
        iniciarListaTipoPersona();
        iniciarListaTipoVivienda();
        iniciarListaDistrito();
        iniciarListaGiro();
        iniciarListaZonaCobranza();
        iniciarListaEstadoSolicitud();
        auditoria(EnumEntidadAuditoria.CLIENTE.getId(),EnumTipoAuditoria.ACCESO.getId(),EnumTipoOperacionAuditoria.OPCION.getId(),null);
        //Faces.getRequestContext().update("menu");
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }
    public void auditoria(int entidadId , String tipoId, String operacionId,Integer registroId) {

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
    public void auditoriaCliente(Cliente cliente, DatoComercial datoComercial, String operacionId) {

        try {
            
            ClienteAuditoria clienteAuditoria = new ClienteAuditoria();
            
            //Cliente
            Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            clienteAuditoria.setUsuarioId(usuario);
            clienteAuditoria.setFechaOperacion(new Date());
            clienteAuditoria.setTipoOperacion(operacionId);
            clienteAuditoria.setIdCodigo(cliente.getId());
            clienteAuditoria.setNombre(cliente.getNombre());
            clienteAuditoria.setApellidoPaterno(cliente.getApellidoPaterno());
            clienteAuditoria.setApellidoMaterno(cliente.getApellidoMaterno());
            clienteAuditoria.setNumeroDocumento(cliente.getNumeroDocumento());
            clienteAuditoria.setDireccion(cliente.getDireccion());
            clienteAuditoria.setTelefono(cliente.getTelefono());
            clienteAuditoria.setCorreo(cliente.getCorreo());
            clienteAuditoria.setOtro(cliente.getOtro());
            clienteAuditoria.setFechaRegistro(cliente.getFechaRegistro());
            clienteAuditoria.setIdAval(cliente.getIdAval());
            clienteAuditoria.setIdTipoDocumentoIdentidad(cliente.getIdTipoDocumentoIdentidad());
            clienteAuditoria.setIdEstado(cliente.getIdEstado());
            clienteAuditoria.setIdDistrito(cliente.getIdDistrito());
            clienteAuditoria.setIdTipoPersona(cliente.getIdTipoPersona());
            clienteAuditoria.setIdTipoVivienda(cliente.getIdTipoVivienda());
            clienteAuditoria.setIdZonaCobranza(cliente.getIdZonaCobranza());
            clienteAuditoria.setIdEstadoSolicitud(cliente.getIdEstadoSolicitud());
            clienteAuditoria.setInicioAlquiler(cliente.getInicioAlquiler());
            clienteAuditoria.setFinAlquiler(cliente.getFinAlquiler());
            clienteAuditoria.setReferencia(cliente.getReferencia());
            clienteAuditoria.setTelefono2(cliente.getTelefono2());
            clienteAuditoria.setCelular(cliente.getCelular());
            clienteAuditoria.setCelular2(cliente.getCelular2());
            clienteAuditoria.setOtrosDatos(cliente.getOtrosDatos());
            clienteAuditoria.setSedeId(usuario.getIdEmpleado().getIdSede());
                        
            //Dato Comercial
            clienteAuditoria.setRucDC(datoComercial.getRuc());
            clienteAuditoria.setRazonSocialDC(datoComercial.getRazonSocial());
            clienteAuditoria.setDireccionDC(datoComercial.getDireccion());
            clienteAuditoria.setReferenciaDC(datoComercial.getReferencia());
            clienteAuditoria.setTelefonoDC(datoComercial.getTelefono());
            clienteAuditoria.setLineaCredritoDC(datoComercial.getLineaCredrito());
            clienteAuditoria.setOtroDC(datoComercial.getOtro());
            clienteAuditoria.setInicioAlquilerDC(datoComercial.getInicioAlquilerDC());
            clienteAuditoria.setFinAlquilerDC(datoComercial.getFinAlquilerDC());
            clienteAuditoria.setOtrosDatosDC(datoComercial.getOtrosDatosDC());
            clienteAuditoria.setIdTipoViviendaDC(datoComercial.getIdTipoVivienda());
            clienteAuditoria.setIdGiro(datoComercial.getIdGiro());
            
            clienteAuditoriaServicio.guardar(clienteAuditoria);
            
            
        } catch (Exception e) {
            LOG.error("error auditoria ciente: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);

        }
    }

    public void iniciarListaAval() {
        try {
            setListaAval(getClienteServicio().obtenerTodo());
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaClientes() {
        try {
            Cliente clienteFiltroInicio = new Cliente();
            clienteFiltroInicio.getIdEstado().setId(EnumEstado.ACTIVO.getId());
            clienteFiltroInicio.setNombre("");
            listaLazyClientes = new LazyDataModel() {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                    List<Cliente> listaClientes = new ArrayList<>();
                    try {
                        clienteFiltroInicio.setFirst(first);
                        clienteFiltroInicio.setPageSize(pageSize);
                        System.out.println("VA A CONSULTAR");
                        listaClientes = getClienteServicio().listarFiltro(clienteFiltroInicio);
                        System.out.println("TERMINÓ DE CONSULTAR");
                        /* cambiar */
                        for (Cliente listaCliente : listaClientes) {
                            if (!(listaCliente.getImagen() == null)) {
                                InputStream is = listaCliente.getImagen().getBinaryStream();
                                //InputStream is = new ByteArrayInputStream((byte[]) listaCliente.getImagen());
                                listaCliente.setDsc(new DefaultStreamedContent(is, listaCliente.getFormato()));
                                listaCliente.setSc(new DefaultStreamedContent(is, listaCliente.getFormato()));

                                int blobLength = (int) listaCliente.getImagen().length();

                                byte[] blobAsBytes = listaCliente.getImagen().getBytes(1, blobLength);
                                listaCliente.setBytes(blobAsBytes);
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("error: " + e);
                    }
                    return listaClientes;
                }
            };
            listaLazyClientes.setRowCount(getClienteServicio().contarFiltro(clienteFiltroInicio));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaClientes:dataTableCliente");
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaGiro() {
        try {
            setListaGiro(getGiroServicio().obtenerTodo());
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaTipoDocumento() {
        try {
            setListaTipoDocumentoIdentidad(getTipoDocumentoIdentidadServicio().obtenerTodo());
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaTipoPersona() {
        try {
            setListaTipoPersona(getTipoPersonaServicio().obtenerTodo());
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaTipoVivienda() {
        try {
            setListaTipoVivienda(getTipoViviendaServicio().obtenerTodo());
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaDistrito() {
        try {
            setListaDistrito(getDistritoServicio().obtenerTodo());
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaEstadoSolicitud() {
        try {
            setListaEstadoSolicitud(getEstadoServicio().listarEstadoSolicitud());
            System.out.println("HOLAAAAA");
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaZonaCobranza() {
        try {
            setListaZonaCobranza(getZonaCobranzaServicio().obtenerTodo());
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void filtrar() {
        try {
            listaLazyClientes = new LazyDataModel() {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                    List<Cliente> listaClientes = new ArrayList<>();
                    try {
                        getClienteFiltro().setFirst(first);
                        getClienteFiltro().setPageSize(pageSize);
                        listaClientes = getClienteServicio().listarFiltro(getClienteFiltro());
                    } catch (Exception e) {
                        LOG.error("error: " + e);
                    }
                    return listaClientes;
                }
            };
            listaLazyClientes.setRowCount(getClienteServicio().contarFiltro(getClienteFiltro()));
            Faces.getRequestContext().update("formListaClientes");
            Faces.getRequestContext().update("formListaClientes:dataTableCliente");
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarCliente() {
        try {
            setEstadoCliente(true);
            setClienteAgregar(new Cliente());
            setDatoComercialAgregar(new DatoComercial());
            setAvalAgregar(new Cliente());
//            getClienteAgregar().setIdTipoPersona(new TipoPersona();
            Faces.getRequestContext().update("divModalCliente");
            setContenidoModalCliente("modal-agregar-cliente.xhtml");
            Faces.getRequestContext().update("formCliente");
            Faces.getRequestContext().execute("show('#modalCliente');");
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarCliente() {
        try {
            if (isEstadoCliente()) {
                getClienteAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else {
                getClienteAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }

            if (isEstadoClienteA()) {
                getAvalAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else {
                getAvalAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }

       
            getClienteServicio().guardar(getClienteAgregar());
            auditoria(EnumEntidadAuditoria.CLIENTE.getId(),EnumTipoAuditoria.DATOS.getId(),EnumTipoOperacionAuditoria.REGISTRO.getId(),getClienteAgregar().getId());
            
                        
            if (isEstadoDatoComercial()) {
                getDatoComercialAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else {
                getDatoComercialAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }

            getDatoComercialAgregar().getIdCliente().setId(getClienteAgregar().getId());

            getDatoComercialServicio().guardar(getDatoComercialAgregar());
            
            auditoriaCliente(getClienteAgregar(), getDatoComercialAgregar(),EnumTipoOperacionAuditoria.REGISTRO.getId() );

            Faces.getRequestContext().execute("hide('#modalCliente');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente al cliente: "+getClienteAgregar().getNombreCompleto()+" con código "+getClienteAgregar().getId()+".", FacesMessage.SEVERITY_INFO);
            iniciarListaClientes();
            Faces.getRequestContext().update("formListaClientes");
            Faces.getRequestContext().update("formListaClientes:dataTableCliente");
        } catch (ConstraintViolationException e) {
            Faces.addMessage("", "Existe un Cliente registrado con esa número de documento en el sistema. ", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    /**
     * Cargar Datos del Cliente
     * <p>
     * @param cliente
     */
    public void mostrarModalModificarCliente(Cliente cliente) {
        try {
            setClienteModificar(cliente);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx");
            System.out.println(cliente.getId());

            setDatoComercialModificar(datoComercialServicio.obtenerPorIdClienteDatoComercial(cliente.getId()));
            System.out.println("YYYYYYYYYYYYYYYYYYYYYYY");
            //System.out.println(datoComercialModificar.getRazonSocial());
            iniciarListaClientes();
            if (getClienteModificar().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId())) {
                setEstadoCliente(true);
            } else {
                setEstadoCliente(false);
            }
            Faces.getRequestContext().update("divModalCliente");
            setContenidoModalCliente("modal-modificar-cliente.xhtml");
            Faces.getRequestContext().update("formCliente");
            Faces.getRequestContext().execute("show('#modalCliente');");
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void modificarHandleFileUpload(FileUploadEvent event) {
        try {
            Blob blob = new SerialBlob(event.getFile().getContents());
            clienteModificar.setImagen(blob);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void crearHandleFileUpload(FileUploadEvent event) {
        try {
            Blob blob = new SerialBlob(event.getFile().getContents());
            clienteAgregar.setImagen(blob);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String modificarBlobToImagen() throws SQLException, IOException, java.io.IOException {
        System.out.println("holaaaaaaaaaaaaaaaaaa");
        String encode = "resources/imagenes/imagen-none.png";
        if (clienteModificar != null && clienteModificar.getImagen() != null) {
            encode = "data:image/jpeg;base64," + Base64.encodeBase64String(toByteArrayUsingCommons(clienteModificar.getImagen().getBinaryStream()));
        }
        return encode;
    }
    public String crearBlobToImagen() throws SQLException, IOException, java.io.IOException {
        String encode = "resources/imagenes/imagen-none.png";
        if (clienteAgregar != null && clienteAgregar.getImagen() != null) {
            encode = "data:image/jpeg;base64," + Base64.encodeBase64String(toByteArrayUsingCommons(clienteAgregar.getImagen().getBinaryStream()));
        }
        return encode;
    }

    public static byte[] toByteArrayUsingCommons(InputStream is) throws IOException, java.io.IOException {

        return IOUtils.toByteArray(is);
    }

    public void upload(FileUploadEvent event) throws java.io.IOException, SQLException {
        try {
            //copyFile(event.getFile().getFileName(), event.getFile().getInputstream());

            InputStream x = event.getFile().getInputstream();
            byte[] bytes = toByteArrayUsingCommons(x);
            //Blob image_vis = rs1.getBlob(10);
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            this.clienteAgregar.setImagen(blob);

            if (event.getFile().getFileName().endsWith("png")) {
                this.clienteAgregar.setFormato("image/png");
            } else if (event.getFile().getFileName().endsWith("jpg") || event.getFile().getFileName().endsWith("jpeg")) {
                this.clienteAgregar.setFormato("image/jpeg");
            } else if (event.getFile().getFileName().endsWith("gif")) {
                this.clienteAgregar.setFormato("image/gif");
            } else {
                this.clienteAgregar.setFormato("application/octet-stream");
            }
            //this.clienteAgregar.setFormato("image/");
            //String s=new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarCliente() {
        try {
            if (isEstadoCliente()) {
                getClienteModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else {
                getClienteModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getClienteServicio().actualizar(getClienteModificar());
            auditoria(EnumEntidadAuditoria.CLIENTE.getId(),EnumTipoAuditoria.DATOS.getId(),EnumTipoOperacionAuditoria.MODIFICACION.getId(),getClienteModificar().getId());

            getDatoComercialServicio().actualizar(getDatoComercialModificar());
            
            auditoriaCliente(getClienteModificar(), getDatoComercialModificar(),EnumTipoOperacionAuditoria.MODIFICACION.getId() );

            Faces.getRequestContext().execute("hide('#modalCliente');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente los datos del cliente "
                    + getClienteModificar().getNombre(), FacesMessage.SEVERITY_INFO);
            iniciarListaClientes();
            Faces.getRequestContext().update("formListaClientes");
            Faces.getRequestContext().update("formListaClientes:dataTableCliente");
        } catch (DataIntegrityViolationException e) {
            Faces.addMessage("", "Existe un Cliente registrado con esa número de documento en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarCliente(Cliente cliente) {
        setClienteModificar(cliente);
        Faces.getRequestContext().update("divModalCliente");
        setContenidoModalCliente("modal-inhabilitar-cliente.xhtml");
        Faces.getRequestContext().update("formCliente");
        Faces.getRequestContext().execute("show('#modalCliente');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarCliente() {
        try {
            getClienteModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getClienteServicio().actualizar(getClienteModificar());

            Faces.getRequestContext().execute("hide('#modalCliente');");
            Faces.addMessage("¡Atención!",
                    "El cliente ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaClientes();
            Faces.getRequestContext().update("formListaClientes");
            Faces.getRequestContext().update("formListaClientes:dataTableCliente");
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarCliente(Cliente cliente) {
        setClienteModificar(cliente);
        Faces.getRequestContext().update("divModalCliente");
        setContenidoModalCliente("modal-habilitar-cliente.xhtml");
        Faces.getRequestContext().update("formCliente");
        Faces.getRequestContext().execute("show('#modalCliente');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarCliente() {
        try {
            getClienteModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getClienteServicio().actualizar(getClienteModificar());

            Faces.getRequestContext().execute("hide('#modalCliente');");
            Faces.addMessage("¡Atención!",
                    "El cliente ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaClientes();
            Faces.getRequestContext().update("formListaClientes");
            Faces.getRequestContext().update("formListaClientes:dataTableCliente");
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarCliente(Cliente cliente) {
        setClienteModificar(cliente);
        Faces.getRequestContext().update("divModalCliente");
        setContenidoModalCliente("modal-eliminar-cliente.xhtml");
        Faces.getRequestContext().update("formCliente");
        Faces.getRequestContext().execute("show('#modalCliente');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarCliente() {
        try {
            DatoComercial datoComercial = new DatoComercial();
            datoComercial=getDatoComercialServicio().obtenerPorIdClienteDatoComercial(getClienteModificar().getId());
            if (datoComercial!=null){
                getDatoComercialServicio().eliminar(datoComercial);
            }
            getClienteServicio().eliminar(getClienteModificar());
            auditoria(EnumEntidadAuditoria.CLIENTE.getId(),EnumTipoAuditoria.DATOS.getId(),EnumTipoOperacionAuditoria.ELIMINACION.getId(),getClienteModificar().getId());
            
            auditoriaCliente(getClienteModificar(), datoComercial,EnumTipoOperacionAuditoria.ELIMINACION.getId() );
            
            Faces.getRequestContext().execute("hide('#modalCliente');");
            Faces.addMessage("¡Atención!",
                    "El cliente ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaClientes();
            Faces.getRequestContext().update("formListaClientes");
            Faces.getRequestContext().update("formListaClientes:dataTableCliente");
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public String getContenidoModalCliente() {
        return contenidoModalCliente;
    }

    public void setContenidoModalCliente(String contenidoModalCliente) {
        this.contenidoModalCliente = contenidoModalCliente;
    }

    public ClienteServicio getClienteServicio() {
        return clienteServicio;
    }

    public void setClienteServicio(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    public boolean isEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(boolean estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Cliente getClienteAgregar() {
        return clienteAgregar;
    }

    public void setClienteAgregar(Cliente clienteAgregar) {
        this.clienteAgregar = clienteAgregar;
    }

    public Cliente getClienteModificar() {
        return clienteModificar;
    }

    public void setClienteModificar(Cliente clienteModificar) {
        this.clienteModificar = clienteModificar;
    }

    public List<TipoDocumentoIdentidad> getListaTipoDocumentoIdentidad() {
        return listaTipoDocumentoIdentidad;
    }

    public void setListaTipoDocumentoIdentidad(List<TipoDocumentoIdentidad> listaTipoDocumentoIdentidad) {
        this.listaTipoDocumentoIdentidad = listaTipoDocumentoIdentidad;
    }

    public TipoDocumentoIdentidadServicio getTipoDocumentoIdentidadServicio() {
        return tipoDocumentoIdentidadServicio;
    }

    public void setTipoDocumentoIdentidadServicio(TipoDocumentoIdentidadServicio tipoDocumentoIdentidadServicio) {
        this.tipoDocumentoIdentidadServicio = tipoDocumentoIdentidadServicio;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getInactivo() {
        return inactivo;
    }

    public void setInactivo(int inactivo) {
        this.inactivo = inactivo;
    }

    public Cliente getClienteFiltro() {
        return clienteFiltro;
    }

    public void setClienteFiltro(Cliente clienteFiltro) {
        this.clienteFiltro = clienteFiltro;
    }

    public LazyDataModel<Cliente> getListaLazyClientes() {
        return listaLazyClientes;
    }

    public void setListaLazyClientes(LazyDataModel<Cliente> listaLazyClientes) {
        this.listaLazyClientes = listaLazyClientes;
    }

    public TipoPersonaServicio getTipoPersonaServicio() {
        return tipoPersonaServicio;
    }

    public void setTipoPersonaServicio(TipoPersonaServicio tipoPersonaServicio) {
        this.tipoPersonaServicio = tipoPersonaServicio;
    }

    public List<TipoPersona> getListaTipoPersona() {
        return listaTipoPersona;
    }

    public void setListaTipoPersona(List<TipoPersona> listaTipoPersona) {
        this.listaTipoPersona = listaTipoPersona;
    }

    public TipoViviendaServicio getTipoViviendaServicio() {
        return tipoViviendaServicio;
    }

    public void setTipoViviendaServicio(TipoViviendaServicio tipoViviendaServicio) {
        this.tipoViviendaServicio = tipoViviendaServicio;
    }

    public List<TipoVivienda> getListaTipoVivienda() {
        return listaTipoVivienda;
    }

    public void setListaTipoVivienda(List<TipoVivienda> listaTipoVivienda) {
        this.listaTipoVivienda = listaTipoVivienda;
    }

    public DistritoServicio getDistritoServicio() {
        return distritoServicio;
    }

    public void setDistritoServicio(DistritoServicio distritoServicio) {
        this.distritoServicio = distritoServicio;
    }

    public List<Distrito> getListaDistrito() {
        return listaDistrito;
    }

    public void setListaDistrito(List<Distrito> listaDistrito) {
        this.listaDistrito = listaDistrito;
    }

    public DatoComercialServicio getDatoComercialServicio() {
        return datoComercialServicio;
    }

    public void setDatoComercialServicio(DatoComercialServicio datoComercialServicio) {
        this.datoComercialServicio = datoComercialServicio;
    }

    public DatoComercial getDatoComercialModificar() {
        if (datoComercialModificar == null) {
            datoComercialModificar = new DatoComercial();
        }
        return datoComercialModificar;
    }

    public void setDatoComercialModificar(DatoComercial datoComercialModificar) {
        this.datoComercialModificar = datoComercialModificar;
    }

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

    public List<Estado> getListaEstadoSolicitud() {
        return listaEstadoSolicitud;
    }

    public void setListaEstadoSolicitud(List<Estado> listaEstadoSolicitud) {
        this.listaEstadoSolicitud = listaEstadoSolicitud;
    }

    public EstadoServicio getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public DatoComercial getDatoComercialAgregar() {
        return datoComercialAgregar;
    }

    public void setDatoComercialAgregar(DatoComercial datoComercialAgregar) {
        this.datoComercialAgregar = datoComercialAgregar;
    }

    public boolean isEstadoDatoComercial() {
        return estadoDatoComercial;
    }

    public void setEstadoDatoComercial(boolean estadoDatoComercial) {
        this.estadoDatoComercial = estadoDatoComercial;
    }

    public List<Giro> getListaGiro() {
        return listaGiro;
    }

    public void setListaGiro(List<Giro> listaGiro) {
        this.listaGiro = listaGiro;
    }

    public Cliente getAvalAgregar() {
        return avalAgregar;
    }

    public void setAvalAgregar(Cliente avalAgregar) {
        this.avalAgregar = avalAgregar;
    }

    public GiroServicio getGiroServicio() {
        return giroServicio;
    }

    public void setGiroServicio(GiroServicio giroServicio) {
        this.giroServicio = giroServicio;
    }

    public boolean isEstadoClienteA() {
        return estadoClienteA;
    }

    public void setEstadoClienteA(boolean estadoClienteA) {
        this.estadoClienteA = estadoClienteA;
    }

}
    // </editor-fold>
