/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.transaccion;

import com.scorpio.sisconca.entidad.Artefacto;
import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.Cliente;
import com.scorpio.sisconca.entidad.Cobrador;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.Entidad;
import com.scorpio.sisconca.entidad.Estado;
import com.scorpio.sisconca.entidad.PagoAuditoria;
import com.scorpio.sisconca.entidad.Payment;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.Prestamo;
import com.scorpio.sisconca.entidad.Sede;
import com.scorpio.sisconca.entidad.TipoPrestamo;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.servicio.ReportService;
import com.scorpio.sisconca.servicio.maestros.ClienteServicio;
import com.scorpio.sisconca.servicio.maestros.CobradorService;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.seguridad.AuditoriaServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.SedeServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.servicio.transaccion.PagoAuditoriaServicio;
import com.scorpio.sisconca.servicio.transaccion.PaymentService;
import com.scorpio.sisconca.servicio.transaccion.PrestamoServicio;
import com.scorpio.sisconca.servicio.transaccion.TipoPrestamoServicio;
import com.scorpio.sisconca.utilitario.IReportManager;
import com.scorpio.sisconca.utilitario.enums.EnumEntidadAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import com.scorpio.sisconca.utilitario.enums.EnumTipoAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumTipoOperacionAuditoria;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

/**
 *
 * @author alejandro
 */
@ManagedBean
@SessionScoped
public class PaymentController {

    private final Logger log = Logger.getLogger(PaymentController.class.getName());

    @ManagedProperty("#{paymentService}")
    private PaymentService paymentService;

    @ManagedProperty("#{prestamoServicio}")
    private PrestamoServicio prestamoServicio;
    
    @ManagedProperty("#{sedeServicio}")
    private SedeServicio sedeServicio;
    
    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;
    
    @ManagedProperty("#{auditoriaServicio}")
    private AuditoriaServicio auditoriaServicio;
    
    @ManagedProperty("#{pagoAuditoriaServicio}")
    private PagoAuditoriaServicio pagoAuditoriaServicio;

    public PagoAuditoriaServicio getPagoAuditoriaServicio() {
        return pagoAuditoriaServicio;
    }

    public void setPagoAuditoriaServicio(PagoAuditoriaServicio pagoAuditoriaServicio) {
        this.pagoAuditoriaServicio = pagoAuditoriaServicio;
    }
    

    public AuditoriaServicio getAuditoriaServicio() {
        return auditoriaServicio;
    }

    public void setAuditoriaServicio(AuditoriaServicio auditoriaServicio) {
        this.auditoriaServicio = auditoriaServicio;
    }
    

    public SedeServicio getSedeServicio() {
        return sedeServicio;
    }

    public void setSedeServicio(SedeServicio sedeServicio) {
        this.sedeServicio = sedeServicio;
    }

    public UsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @ManagedProperty("#{clienteServicio}")
    private ClienteServicio clienteServicio;

    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;

    @ManagedProperty("#{cobradorService}")
    private CobradorService cobradorService;

    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;
    
    @ManagedProperty("#{reportService}")
    private ReportService reportService;

    @ManagedProperty("#{tipoPrestamoServicio}")
    private TipoPrestamoServicio tipoPrestamoServicio;

    private LazyDataModel paymentList;
    private String modalView;
    private List<Prestamo> prestamos;
    private List<Cliente> clientes;
    private Cliente cliente;
    private Prestamo prestamo;
    private Date start;
    private Date end;
    private List<Payment> payments;
    private Payment payment;
    
    private List<Cobrador> cobradores;

    
    private List<Sede> listaSede;
    
    //Modificar
    private Payment pagoModificar;
    
    private String contenidoModalPago = "modal-modificar-pago.xhtml";

    public String getContenidoModalPago() {
        return contenidoModalPago;
    }

    public void setContenidoModalPago(String contenidoModalPago) {
        this.contenidoModalPago = contenidoModalPago;
    }
    

    public Payment getPagoModificar() {
        return pagoModificar;
    }

    public void setPagoModificar(Payment pagoModificar) {
        this.pagoModificar = pagoModificar;
    }
            

    public List<Sede> getListaSede() {
        return listaSede;
    }

    public void setListaSede(List<Sede> listaSede) {
        this.listaSede = listaSede;
    }
    private boolean isUserAdmin;
    private boolean fisu;
    private double totalAmountPayments = 0;
    private Integer idPrestamo;

    public void init() {
        iniciarListaSede();
        totalAmountPayments = 0;
        setFisu(false);
        payment = new Payment();
        try {
            if (!isSoporte()) {
                /*Para sede en el menú derecho*/
                Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
                Empleado empleado = empleadoServicio.obtenerPorCodigo((usuario).getIdEmpleado().getId());
                getPayment().getPrestamo().setIdSede(empleado.getIdSede());
                System.out.println("Sede de pago" + getPayment().getPrestamo().getIdSede().getNombre());
                //getPrestamoFiltro().setIdSede(empleado.getIdSede());
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }        
        paymentList = new LazyDataModel() {
            @Override
            public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                List<Payment> payments = new ArrayList<>();
                try {
                    Calendar today = Calendar.getInstance();
                    today.set(Calendar.DAY_OF_MONTH, 1);
                    start = today.getTime();
                    end = Calendar.getInstance().getTime();
                    payment.setStart(today.getTime());
                    payment.setEnd(Calendar.getInstance().getTime());
                    payment.setFirst(first);
                    payment.setPageSize(pageSize);
                    
                    payments = getPaymentService().getPaginated(payment);
                    payments.forEach(p -> totalAmountPayments += p.getPago());
                    totalAmountPayments = Math.round(getPaymentService().getTotalAmount(payment) * 100.0) / 100.0;
                } catch (Exception e) {
                    log.error("error: " + e.getMessage());
                }
                return payments;
            }
        };
        try {
            setClientes(getClienteServicio().obtenerTodo());

            paymentList.setRowCount(getPaymentService().count(payment));
            auditoria(EnumEntidadAuditoria.PAGO.getId(),EnumTipoAuditoria.ACCESO.getId(),EnumTipoOperacionAuditoria.OPCION.getId(),null);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("dataTablePayment");
        Faces.getRequestContext().update("divMenuDerecha");
    }
    
    public void mostrarModalModificarPago(Payment pago)
    {
        try
        {
            setPagoModificar(pago);
            setContenidoModalPago("modal-modificar-pago.xhtml");            
            Faces.getRequestContext().update("formSede");
            Faces.getRequestContext().execute("show('#modalSede');");
            Faces.getUserInSession();
            Faces.getRequestContext().update("paymentModal");
            Faces.getRequestContext().execute("hide('#prestamoSelectedModalContainer');");
            Faces.getRequestContext().update("formListPayments");
            setModalView("modal-modificar-pago.xhtml");
            Faces.getRequestContext().update("formListPayments");
            Faces.getRequestContext().execute("show('#modalModificarPago');");

        } catch (Exception e)
        {
            
            log.error("error mostrando pago (modificar): " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }
    public void actualizarPago(){
        try {
            getPaymentService().actualizar(getPagoModificar());
            
            {
                Prestamo prestamo = new Prestamo();
                prestamo = prestamoServicio.obtenerPorCodigo(getPagoModificar().getPrestamo().getId());
                
                Double totalPagado = 0.00;
                Double totalPorPagar = 0.00;
                totalPagado= prestamo.getPagado();
                System.out.println("totalPagado: "+totalPagado);
                totalPorPagar = prestamo.getMontoPagar();
                System.out.println("totalPorPagar: "+totalPorPagar);
                
                if (totalPagado >= totalPorPagar) {
                    prestamo.setIdEstado(new Estado(EnumEstado.CANCELADO.getId()));
                } else {
                    prestamo.setIdEstado(new Estado(EnumEstado.ACTIVO.getId()));
                }
                prestamoServicio.actualizar(prestamo);
            }            
            auditoriaPago(getPagoModificar(), EnumTipoOperacionAuditoria.MODIFICACION.getId());
            Faces.getRequestContext().execute("hide('#modalModificarPago');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente el pago", FacesMessage.SEVERITY_INFO);
            init();
        } catch (Exception e) {
            log.error("error modificando pago: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
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
    
    public void auditoriaPago(Payment pago,String operacionId) {
        try {
            PagoAuditoria pagoAuditoria= new PagoAuditoria();
            
            Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            
            pagoAuditoria.setUsuarioId(usuario);
            pagoAuditoria.setFechaOperacion(new Date());
            pagoAuditoria.setTipoOperacion(operacionId);
            pagoAuditoria.setIdCodigo(pago.getId());
            pagoAuditoria.setPrestamo(pago.getPrestamo());
            pagoAuditoria.setCobrador(pago.getCobrador());
            pagoAuditoria.setFechaPago(pago.getFechaPago());
            pagoAuditoria.setPago(pago.getPago());
            pagoAuditoria.setFlagSupervisor(pago.getFlagSupervisor());
            pagoAuditoria.setFormaPago(pago.getFormaPago());
            
            pagoAuditoriaServicio.guardar(pagoAuditoria);
            
        } catch (Exception e) {
            log.error("error auditoria pago: " + e);
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
            
        } catch (Exception e)
        {
            log.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }
    
    
    public void downloadFile() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("fecha_fin", end);
        param.put("fecha_inicio", start);
        param.put("id_cliente", payment.getPrestamo().getIdCliente().getId() == null ? 0 : payment.getPrestamo().getIdCliente().getId());
        param.put("id_prestamo", idPrestamo == null ? 0: idPrestamo);
        param.put("id_sede", payment.getPrestamo().getIdSede().getId() == null ? 0: payment.getPrestamo().getIdSede().getId());

        if (start != null && end != null && payment.getPrestamo().getIdCliente().getId() != null) {
            IReportManager i = new IReportManager(null);
            i.createFilePDF("pagos");
            i.setJasperPrint("/resources/reportes/pagos.jasper", param, reportService.connect());
            i.exportPDF("pagos");
        } else {
            Faces.addMessage("Debe llenar todos los campos del filtro!", "", FacesMessage.SEVERITY_WARN);
        }

    }

    public void showModalNewPayment() {
        try{
            String motivoError = "";
            try {
                clientes = clienteServicio.obtenerTodo();
                prestamos = new ArrayList<>();
                List<Object> result = (List<Object>) getPrestamoServicio().getUnpaidPrestamos();
                Iterator itr = result.iterator();
                while (itr.hasNext()) {
                    Object[] obj = (Object[]) itr.next();

                    Prestamo prestamo = getPrestamoServicio().obtenerPorCodigo((int) obj[0]);
                    boolean control = false;
                    if (isSoporte()) {
                        if (getPayment().getPrestamo().getIdSede().getId() == null) {
                            control=true;
                        }else{
                            if((prestamo.getIdSede().getId() == payment.getPrestamo().getIdSede().getId()))
                                control=true;
                        }
                    }else{
                        if((prestamo.getIdSede().getId() == payment.getPrestamo().getIdSede().getId()))
                                control=true;
                    }
                    
                    if (control) {
                        
                        Prestamo pp = new Prestamo();
                        
                        pp.setIdSede(prestamo.getIdSede());
                        pp.setId((int) obj[0]);                        
                        pp.setFecha((Date) obj[1]);
                        motivoError="Préstamo "+pp.getId().toString()+" registrado con fecha de préstamo "+pp.getFecha().toString();
                        pp.setPrestamo((double) obj[2]);
                        pp.setCuota((double) obj[3]);
                        pp.setMontoPagar((double) obj[4]);

                        Empleado e = empleadoServicio.obtenerPorCodigo((int) obj[5]);
                        pp.setIdEmpleado(e);
                        pp.setIdEstado(new Estado((int) obj[6]));
                        pp.setIdArtefacto(new Artefacto((int) obj[7]));
                        TipoPrestamo tp = tipoPrestamoServicio.obtenerPorCodigo((int) obj[8]);

                        pp.setIdTipoPrestamo(tp);
                        Cliente c = clienteServicio.obtenerPorCodigo((int) obj[9]);
                        pp.setIdCliente(c);
                        pp.setGarante(c.getId());
                        pp.setFrecuencia((int) obj[11]);
                        pp.setNumeroRenovacion((Integer) obj[12]);
                        pp.setFechaRenovacion((Date) obj[13]);
                        pp.setIndicadorRenovacion((Character) obj[14]);
                        pp.setInteres((Integer) obj[15]);
                        System.out.println("Esan4");
                        pp.setPagado((Double) obj[16]);
                        prestamos.add(pp);
                    }
                }

                Faces.getRequestContext().update("paymentModal");
                setModalView("modals/newPayment.xhtml");
                Faces.getRequestContext().update("formListPayments");
                Faces.getRequestContext().execute("show('#paymentModalContainer');");
            } catch (Exception ex) {
                log.error(ex.getMessage());
                log.error(ex.toString());
                Faces.addMessage("¡ERROR!", "No se pudo completar la operación, póngase en contacto "
                    + "con el administrador del sistema "
                    + "Problemas con el " + motivoError
                    , FacesMessage.SEVERITY_INFO);

            }
            
        }catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.toString());

        }
        

    }

    public void selectPrestamo(int id) {
        try {
            prestamo = prestamoServicio.obtenerPorCodigo(id);
            payments = paymentService.findByPrestamoId(id);
            log.info("seleccionando préstamo");
            Faces.getRequestContext().update("paymentModal");
            Faces.getRequestContext().execute("hide('#paymentModalContainer');");
            Faces.getRequestContext().update("formListPayments");
            setModalView("modals/prestamoSelected.xhtml");
            Faces.getRequestContext().update("formListPayments");
            Faces.getRequestContext().execute("show('#prestamoSelectedModalContainer');");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void filter() {
        System.out.println("Start: " + start);
        System.out.println("end: " + end);
        
        
        totalAmountPayments = 0;
        paymentList = new LazyDataModel() {
            @Override
            public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                List<Payment> payments = new ArrayList<>();
                try {
                    payment.setStart(start);
                    payment.setEnd(end);
                    payment.setFirst(first);
                    payment.setPageSize(pageSize);
                    
                   
                    
                    payments = getPaymentService().getPaginated(payment);
                } catch (Exception e) {
                    log.error("error: " + e);
                }
                System.out.println("Paso por aqui1: "+payment.toString());
                totalAmountPayments = Math.round(getPaymentService().getTotalAmount(payment) * 100.0) / 100.0;
                System.out.println("Paso por aqui2: "+payment.toString());
                return payments;
                
                
            }
        };
        
        try {
            paymentList.setRowCount(getPaymentService().count(payment));
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
        Faces.getRequestContext().update("formListPayments:dataTablePayment");
        Faces.getRequestContext().update("dataTablePayment");
    }

    public void addPayment() {
        try {
            payment = new Payment();
            setCobradores(cobradorService.obtenerTodo());
            Cobrador cobrador = cobradorService.getByEmployeeId(Faces.getUserInSession().getIdEmpleado().getId());
            payment.setCobrador(cobrador);
            payment.setPrestamo(prestamo);
            Faces.getUserInSession();
            Faces.getRequestContext().update("paymentModal");
            Faces.getRequestContext().execute("hide('#prestamoSelectedModalContainer');");
            Faces.getRequestContext().update("formListPayments");
            setModalView("modals/addPayment.xhtml");
            Faces.getRequestContext().update("formListPayments");
            Faces.getRequestContext().execute("show('#addPaymentModalContainer');");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerPayment() {
        try {

            if (payment.getPago() >= (payment.getPrestamo().getMontoPagar() - payment.getPrestamo().getPagado())) {

                payment.getPrestamo().setIdEstado(new Estado(EnumEstado.CANCELADO.getId()));

            }

            paymentService.save(payment);
            System.out.println("Actualizar estado de prestamo");
            System.out.println(payment.getPrestamo().getIdEstado().getId());
            System.out.println(prestamo.getIdEstado().getId());
            prestamoServicio.guardarOActualizar(prestamo);
            auditoria(EnumEntidadAuditoria.PAGO.getId(),EnumTipoAuditoria.DATOS.getId(),EnumTipoOperacionAuditoria.REGISTRO.getId(),payment.getId());
            auditoriaPago(payment,EnumTipoOperacionAuditoria.REGISTRO.getId());

            init();
            Faces.getRequestContext().update("paymentModal");
            Faces.getRequestContext().execute("hide('#addPaymentModalContainer');");
            Faces.getRequestContext().update("formListPayments");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isAdmin() throws Exception {
        List<PerfilPorUsuario> perfilPorUsuarios = perfilPorUsuarioServicio.getByUserId(Faces.getUserInSession().getId());
        return perfilPorUsuarios.stream().anyMatch((perfilPorUsuario) -> (perfilPorUsuario.getIdPerfil().getId() == EnumPerfil.ADMINISTRADOR.getId()));
    }
    
    public boolean isAdministrador() throws Exception {
        List<PerfilPorUsuario> perfilPorUsuarios = perfilPorUsuarioServicio.getByUserId(Faces.getUserInSession().getId());
        return perfilPorUsuarios.stream().anyMatch((perfilPorUsuario) -> (perfilPorUsuario.getIdPerfil().getId() == EnumPerfil.ADMINISTRADOR.getId()));
    }

    public void setFisuValue() {
        if (fisu) {
            payment.setFlagSupervisor(1);
        } else {
            payment.setFlagSupervisor(0);
        }
        System.out.println("Nuevo valir de fisu: " + payment.getFlagSupervisor());
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public LazyDataModel getPaymentList() {
        return paymentList;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public Integer getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Integer idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public void setPaymentList(LazyDataModel paymentList) {
        this.paymentList = paymentList;
    }

    public String getModalView() {
        return modalView;
    }

    public void setModalView(String modalView) {
        this.modalView = modalView;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public PrestamoServicio getPrestamoServicio() {
        return prestamoServicio;
    }

    public void setPrestamoServicio(PrestamoServicio prestamoServicio) {
        this.prestamoServicio = prestamoServicio;
    }

    public ClienteServicio getClienteServicio() {
        return clienteServicio;
    }

    public void setClienteServicio(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Payment getPayment() {
        if (payment == null) {
            payment = new Payment();
        }
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public EmpleadoServicio getEmpleadoServicio() {
        return empleadoServicio;
    }

    public void setEmpleadoServicio(EmpleadoServicio empleadoServicio) {
        this.empleadoServicio = empleadoServicio;
    }

    public CobradorService getCobradorService() {
        return cobradorService;
    }

    public void setCobradorService(CobradorService cobradorService) {
        this.cobradorService = cobradorService;
    }

    public List<Cobrador> getCobradores() {
        return cobradores;
    }

    public void setCobradores(List<Cobrador> cobradores) {
        this.cobradores = cobradores;
    }

    public PerfilPorUsuarioServicio getPerfilPorUsuarioServicio() {
        return perfilPorUsuarioServicio;
    }

    public void setPerfilPorUsuarioServicio(PerfilPorUsuarioServicio perfilPorUsuarioServicio) {
        this.perfilPorUsuarioServicio = perfilPorUsuarioServicio;
    }

    public boolean isIsUserAdmin() {
        return isUserAdmin;
    }

    public void setIsUserAdmin(boolean isUserAdmin) {
        this.isUserAdmin = isUserAdmin;
    }

    public boolean getFisu() {
        return fisu;
    }

    public void setFisu(boolean fisu) {
        this.fisu = fisu;
    }

    public double getTotalAmountPayments() {
        return totalAmountPayments;
    }

    public void setTotalAmountPayments(double totalAmountPayments) {
        this.totalAmountPayments = totalAmountPayments;
    }

    public TipoPrestamoServicio getTipoPrestamoServicio() {
        return tipoPrestamoServicio;
    }

    public void setTipoPrestamoServicio(TipoPrestamoServicio tipoPrestamoServicio) {
        this.tipoPrestamoServicio = tipoPrestamoServicio;
    }

}
 // </editor-fold>
