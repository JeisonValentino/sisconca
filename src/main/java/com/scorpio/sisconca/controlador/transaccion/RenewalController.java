package com.scorpio.sisconca.controlador.transaccion;

import com.scorpio.sisconca.entidad.Artefacto;
import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.Cliente;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.Entidad;
import com.scorpio.sisconca.entidad.Estado;
import com.scorpio.sisconca.entidad.Payment;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.Prestamo;
import com.scorpio.sisconca.entidad.Renewal;
import com.scorpio.sisconca.entidad.RenovacionAuditoria;
import com.scorpio.sisconca.entidad.Sede;
import com.scorpio.sisconca.entidad.TipoPrestamo;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.servicio.maestros.ClienteServicio;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.seguridad.AuditoriaServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.SedeServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.servicio.transaccion.PrestamoServicio;
import com.scorpio.sisconca.servicio.transaccion.RenewalService;
import com.scorpio.sisconca.servicio.transaccion.RenovacionAuditoriaServicio;
import com.scorpio.sisconca.servicio.transaccion.TipoPrestamoServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEntidadAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import com.scorpio.sisconca.utilitario.enums.EnumTipoAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumTipoOperacionAuditoria;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * @author Alejandro Matos<amatos@gmail.com>
 */
@ManagedBean
@SessionScoped
public class RenewalController
{
    private final Logger log = Logger.getLogger(RenewalController.class.getName());

    @ManagedProperty("#{renewalService}")
    private RenewalService renewalService;

    @ManagedProperty("#{clienteServicio}")
    private ClienteServicio clienteServicio;

    @ManagedProperty("#{tipoPrestamoServicio}")
    private TipoPrestamoServicio tipoPrestamoServicio;

    @ManagedProperty("#{prestamoServicio}")
    private PrestamoServicio prestamoServicio;
    
    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;
    
    @ManagedProperty("#{sedeServicio}")
    private SedeServicio sedeServicio;
    
    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;
    
    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;
    
    @ManagedProperty("#{auditoriaServicio}")
    private AuditoriaServicio auditoriaServicio;
    
    @ManagedProperty("#{renovacionAuditoriaServicio}")
    private RenovacionAuditoriaServicio renovacionAuditoriaServicio;

    public RenovacionAuditoriaServicio getRenovacionAuditoriaServicio() {
        return renovacionAuditoriaServicio;
    }

    public void setRenovacionAuditoriaServicio(RenovacionAuditoriaServicio renovacionAuditoriaServicio) {
        this.renovacionAuditoriaServicio = renovacionAuditoriaServicio;
    }
    
    

    public AuditoriaServicio getAuditoriaServicio() {
        return auditoriaServicio;
    }

    public void setAuditoriaServicio(AuditoriaServicio auditoriaServicio) {
        this.auditoriaServicio = auditoriaServicio;
    }
    

    public EmpleadoServicio getEmpleadoServicio() {
        return empleadoServicio;
    }

    public void setEmpleadoServicio(EmpleadoServicio empleadoServicio) {
        this.empleadoServicio = empleadoServicio;
    }

    public UsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
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

    private LazyDataModel renewals;
    private Renewal renewal;
    private Renewal newRenewal;

    private List<Renewal> renewalList;
    private double totalAmountRenewals;
    private List<Cliente> clientes;
    private String modalView;
    private List<TipoPrestamo> tipoPrestamoList;
    private List<Prestamo> prestamos;
    private List<Sede> listaSede;

    public List<Sede> getListaSede() {
        return listaSede;
    }

    public void setListaSede(List<Sede> listaSede) {
        this.listaSede = listaSede;
    }
    private String clientName;

    public void init()
    {
        iniciarListaSede();
        clientName = "";
        renewal = new Renewal();
        newRenewal = new Renewal();
        
        try {
            if (!isSoporte()) {
                /*Para sede en el menú derecho*/
                Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
                Empleado empleado = empleadoServicio.obtenerPorCodigo((usuario).getIdEmpleado().getId());
                
                getRenewal().getPrestamoPorRenovacion().setIdSede(empleado.getIdSede());
                System.out.println("Sede de pago nombre1" + getRenewal().getPrestamoPorRenovacion().getIdSede().getId());
                System.out.println("Sede de pago nombre2" + getRenewal().getPrestamoPorRenovacion().getIdSede().getNombre());
                
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        renewals = new LazyDataModel()
        {
            @Override
            public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
            {
                Calendar today = Calendar.getInstance();
                today.set(Calendar.DAY_OF_MONTH, 1);
                renewal.setStart(today.getTime());
                renewal.setEnd(Calendar.getInstance().getTime());
                renewal.setFirst(first);
                renewal.setPageSize(pageSize);
                renewalList = renewalService.getPaginatedResult(renewal);
                totalAmountRenewals = Math.round(getRenewalService().getTotalAmount(renewal) * 100.0) / 100.0;
                return renewalList;
            }
        };

        getRenewalList().clear();
        try
        {
            getClientes().clear();
            getTipoPrestamoList().clear();
            setClientes(getClienteServicio().obtenerTodo());
            setTipoPrestamoList(getTipoPrestamoServicio().obtenerTodo());
            renewals.setRowCount(renewalService.contarTodo());
            auditoria(EnumEntidadAuditoria.RENOVACION.getId(),EnumTipoAuditoria.ACCESO.getId(),EnumTipoOperacionAuditoria.OPCION.getId(),null);
        }
        catch (Exception ex)
        {
            Logger.getLogger(RenewalController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public void auditoriaRenovacion(Prestamo renovacion,Prestamo origenRenovacion,String operacionId) {
        try {
            
            RenovacionAuditoria renovacionAuditoria = new RenovacionAuditoria();
            
            Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            
            renovacionAuditoria.setUsuarioId(usuario);
            renovacionAuditoria.setFechaOperacion(new Date());
            renovacionAuditoria.setTipoOperacion(operacionId);
            renovacionAuditoria.setIdCodigo(renovacion.getId());
            renovacionAuditoria.setIdCodigoOrigen(origenRenovacion.getId());
            renovacionAuditoria.setFecha(origenRenovacion.getFecha());
            renovacionAuditoria.setPrestamo(origenRenovacion.getPrestamo());
            renovacionAuditoria.setCuota(origenRenovacion.getCuota());
            renovacionAuditoria.setMontoPagar(origenRenovacion.getMontoPagar());
            renovacionAuditoria.setIdEmpleado(origenRenovacion.getIdEmpleado());
            renovacionAuditoria.setIdEstado(origenRenovacion.getIdEstado());
            renovacionAuditoria.setIdArtefacto(origenRenovacion.getIdArtefacto());
            renovacionAuditoria.setIdTipoPrestamo(origenRenovacion.getIdTipoPrestamo());
            renovacionAuditoria.setIdCliente(origenRenovacion.getIdCliente());
            renovacionAuditoria.setGarante(origenRenovacion.getGarante());
            renovacionAuditoria.setFrecuencia(origenRenovacion.getFrecuencia());
            renovacionAuditoria.setNumeroRenovacion(origenRenovacion.getNumeroRenovacion());
            renovacionAuditoria.setFechaRenovacion(origenRenovacion.getFechaRenovacion());
            renovacionAuditoria.setIndicadorRenovacion(origenRenovacion.getIndicadorRenovacion());
            renovacionAuditoria.setInteres(origenRenovacion.getInteres());
            renovacionAuditoria.setLugarEntrega(origenRenovacion.getLugarEntrega());
            
            renovacionAuditoria.setTipoMoneda(origenRenovacion.getTipoMoneda());
            renovacionAuditoria.setTipoGarantia(origenRenovacion.getTipoGarantia());
            renovacionAuditoria.setIdSede(origenRenovacion.getIdSede());
            
            renovacionAuditoriaServicio.guardar(renovacionAuditoria);
            
        } catch (Exception e) {
            
            Logger.getLogger(RenewalController.class.getName()).log(Level.SEVERE, null, e);
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
            
            Logger.getLogger(RenewalController.class.getName()).log(Level.SEVERE, null, e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void filter()
    {
        try
        {
            renewals = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    renewal.setFirst(first);
                    renewal.setPageSize(pageSize);
                    renewalList = renewalService.getPaginatedResult(renewal);
                    totalAmountRenewals = Math.round(getRenewalService().getTotalAmount(renewal) * 100.0) / 100.0;
                    return renewalList;
                }
            };
            renewals.setRowCount(renewalService.contarTodo());
        }
        catch (Exception ex)
        {
            Logger.getLogger(RenewalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }

    public void showModalNewRenewal()
    {
        newRenewal = new Renewal();
        getNewRenewal().getPrestamoPorRenovacion().setFecha(new Date());
        Faces.getRequestContext().update("renewalModal");
        setModalView("modals/newRenewal.xhtml");
        Faces.getRequestContext().update("formNewRenewal");
        Faces.getRequestContext().execute("show('#newRenewalModalContainer');");
    }

    public void updateTipoPrestamo()
    {
        try
        {
            if (getNewRenewal().getPrestamoPorRenovacion().getIdTipoPrestamo().getId() != null)
            {
                getNewRenewal().getPrestamoPorRenovacion().setIdTipoPrestamo(tipoPrestamoServicio.obtenerPorCodigo(getNewRenewal().getPrestamoPorRenovacion().getIdTipoPrestamo().getId()));
                int frecuencia = getNewRenewal().getPrestamoPorRenovacion().getIdTipoPrestamo().getCobra();
                getNewRenewal().getPrestamoPorRenovacion().setFrecuencia(frecuencia);
                System.out.println("Cobra de entidad:" + getNewRenewal().getPrestamoPorRenovacion().getFrecuencia());
                Faces.getRequestContext().update("formNewRenewal:cobro");
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(RenewalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openModalLendSearch()
    {
        clientName = "";
        if (getNewRenewal().getPrestamoPorRenovacion().getIdCliente().getId() != null && getNewRenewal().getPrestamoPorRenovacion().getIdTipoPrestamo().getId() != null)
        {
            try
            {
                clientes = clienteServicio.obtenerTodo();
                prestamos = new ArrayList<>();
                addAllUnpaidPrestamos();
            }
            catch (Exception ex)
            {
                Logger.getLogger(RenewalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Faces.getRequestContext().update("renewalModal");
            Faces.getRequestContext().execute("hide('#newRenewalModalContainer');");
            setModalView("modals/lendSearch.xhtml");
            Faces.getRequestContext().execute("show('#newRenewalPrestamoSearchModalContainer');");
        }
        else
        {
            Faces.addMessage("¡Atención!",
                    "Debe seleccionar un cliente y un tipo de préstamo antes de continuar.", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void closeModalLendSearch()
    {
        Faces.getRequestContext().execute("hide('#newRenewalPrestamoSearchModalContainer');");
    }

    public void findPrestamoByClientName()
    {
        addAllUnpaidPrestamos();

        if (!clientName.isEmpty())
        {
            List<Prestamo> prestamosToShow = new ArrayList<>();
            prestamos.stream().filter((prestamo) -> (prestamo.getIdCliente().getNombreCompleto().toLowerCase().contains(clientName.toLowerCase()))).forEachOrdered((prestamo) ->
            {
                prestamosToShow.add(prestamo);
            });
            prestamos.clear();
            prestamos.addAll(prestamosToShow);
        }
        Faces.getRequestContext().update("formRenewalSearchLend");
    }

    private void addAllUnpaidPrestamos()
    {
        prestamos.clear();
        List<Object> result = (List<Object>) getPrestamoServicio().getUnpaidAndOverdue();
        Iterator itr = result.iterator();
        while (itr.hasNext())
        {
            try {
                Object[] obj = (Object[]) itr.next();
//                for (Object object : obj)
//                {
//                    System.out.println(object);
//                }
                Prestamo prestamo = prestamoServicio.obtenerPorCodigo((int) obj[0]);
                boolean control = false;
                if (isSoporte()) {
                    if (getRenewal().getPrestamoPorRenovacion().getIdSede().getId() == null) {
                        control = true;
                    } else {
                        if ((prestamo.getIdSede().getId() == renewal.getPrestamoPorRenovacion().getIdSede().getId())) {
                            control = true;
                        }
                    }
                } else {
                    if ((prestamo.getIdSede().getId() == renewal.getPrestamoPorRenovacion().getIdSede().getId())) {
                        control = true;
                    }
                }
                if (control) {

                    Prestamo pp = new Prestamo();
                    pp.setIdSede(prestamo.getIdSede());
                    pp.setId((int) obj[0]);
                    pp.setFecha((Date) obj[1]);
                    pp.setPrestamo((double) obj[2]);
                    pp.setCuota((double) obj[3]);
                    pp.setMontoPagar((double) obj[4]);
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
                    pp.setPagado((Double) obj[16]);
                    prestamos.add(pp);
                }

            } catch (Exception ex) {
                Logger.getLogger(RenewalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void selectPrestamo(int id)
    {
        try
        {
            getNewRenewal().setPrestamoOrigenRenovacion(getPrestamoServicio().obtenerPorCodigo(id));
            // newRenewal.prestamoOrigenRenovacion.montoPagar - renewalController.newRenewal.prestamoOrigenRenovacion.pagado
            getNewRenewal().setSaldo(getNewRenewal().getPrestamoOrigenRenovacion().getMontoPagar() - getNewRenewal().getPrestamoOrigenRenovacion().getPagado());
//            newRenewal.prestamoPorRenovacion.prestamo
            getNewRenewal().getPrestamoPorRenovacion().setPrestamo(getNewRenewal().getSaldo());
            getNewRenewal().getPrestamoPorRenovacion().setIdSede(getNewRenewal().getPrestamoOrigenRenovacion().getIdSede());
            Faces.getRequestContext().execute("hide('#newRenewalModalContainer');");
            Faces.getRequestContext().update("renewalModal");
            setModalView("modals/addRenewal.xhtml");
            Faces.getRequestContext().update("formNewRenewal");
            Faces.getRequestContext().execute("show('#addRenewalModalContainer');");
        }
        catch (Exception ex)
        {
            Logger.getLogger(RenewalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeQuotas()
    {
//        prestamoAgregar.setCuota(prestamoAgregar.getMontoPagar() / newPrestamoTime);
//        newRenewal.prestamoPorRenovacion.idTipoPrestamo.tiempo
        System.out.println("monto: " + getNewRenewal().getPrestamoPorRenovacion().getMontoPagar());
        System.out.println("tiempo: " + getNewRenewal().getPrestamoPorRenovacion().getIdTipoPrestamo().getTiempo());
        double cuota = getNewRenewal().getPrestamoPorRenovacion().getPrestamo()
                / getNewRenewal().getPrestamoPorRenovacion().getIdTipoPrestamo().getTiempo();
        getNewRenewal().getPrestamoPorRenovacion().setCuota(cuota);
        getNewRenewal().getPrestamoPorRenovacion()
                .setMontoPagar(
                        getNewRenewal().getPrestamoPorRenovacion().getPrestamo()
                        + (cuota * getNewRenewal().getPrestamoPorRenovacion().getIdTipoPrestamo().getTiempo()));
        Faces.getRequestContext().update("formNewRenewal");
    }

    public void saveRenewal()
    {
        try
        {
            // Seteamos el nuevo estado del préstamo, la nueva fecha y su saldo
            newRenewal.getPrestamoOrigenRenovacion().setIdEstado(new Estado(EnumEstado.RENOVADO.getId()));
            newRenewal.getPrestamoOrigenRenovacion().setIndicadorRenovacion(null);
            newRenewal.getPrestamoOrigenRenovacion().setFecha(newRenewal.getPrestamoPorRenovacion().getFecha());
            newRenewal.getPrestamoOrigenRenovacion().setFechaRenovacion(newRenewal.getPrestamoPorRenovacion().getFecha());
            newRenewal.setSaldo(totalAmountRenewals);

            getPrestamoServicio().actualizar(newRenewal.getPrestamoOrigenRenovacion());

            // seteamos el nuevo estado del préstamo
            newRenewal.getPrestamoPorRenovacion().setIdEstado(new Estado(EnumEstado.ACTIVO.getId()));
            newRenewal.getPrestamoPorRenovacion().setIndicadorRenovacion('R');

            // los datos se repiten...
            newRenewal.getPrestamoPorRenovacion().setIdArtefacto(newRenewal.getPrestamoOrigenRenovacion().getIdArtefacto());
            newRenewal.getPrestamoPorRenovacion().setIdEmpleado(newRenewal.getPrestamoOrigenRenovacion().getIdEmpleado());
            newRenewal.getPrestamoPorRenovacion().setFrecuencia(newRenewal.getPrestamoOrigenRenovacion().getFrecuencia());

            // Jalamos el tipo de préstamo con el ID (no tenemos la entidad)
            TipoPrestamo tp = tipoPrestamoServicio.obtenerPorCodigo(newRenewal.getPrestamoPorRenovacion().getIdTipoPrestamo().getId());
            newRenewal.getPrestamoPorRenovacion().setIdTipoPrestamo(tp);

            // Calculamos el monto a pagar y la cuota en variables independientes para claridad.
            double montoPagar
                    = newRenewal.getPrestamoPorRenovacion().getPrestamo()
                    + (newRenewal.getPrestamoPorRenovacion().getPrestamo()
                    * newRenewal.getPrestamoOrigenRenovacion().getInteres() / 100);
            double cuota = montoPagar / newRenewal.getPrestamoPorRenovacion().getIdTipoPrestamo().getTiempo();

            newRenewal.getPrestamoPorRenovacion().setMontoPagar(montoPagar);
            newRenewal.getPrestamoPorRenovacion().setCuota(cuota);

            // guardamos el nuevo préstamo y almacenamos el ID para actualizar el préstamo origen.
            newRenewal.getPrestamoPorRenovacion().setInteres(newRenewal.getPrestamoOrigenRenovacion().getInteres());
            int renovacion = (int) getPrestamoServicio().guardar(newRenewal.getPrestamoPorRenovacion());

            // Seteamos el valor y guardamos el préstamo origen.
            newRenewal.getPrestamoOrigenRenovacion().setNumeroRenovacion(renovacion);
            getPrestamoServicio().actualizar(newRenewal.getPrestamoOrigenRenovacion());

            newRenewal.setEstado(1);
            getRenewalService().save(newRenewal);
            auditoria(EnumEntidadAuditoria.RENOVACION.getId(),EnumTipoAuditoria.DATOS.getId(),EnumTipoOperacionAuditoria.REGISTRO.getId(),newRenewal.getPrestamoPorRenovacion().getId());
            auditoriaRenovacion(newRenewal.getPrestamoPorRenovacion(),newRenewal.getPrestamoOrigenRenovacion(),EnumTipoOperacionAuditoria.REGISTRO.getId());

            Faces.getRequestContext().execute("hide('#addRenewalModalContainer');");
            init();
        }
        catch (Exception ex)
        {
            Logger.getLogger(RenewalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showEditModal(Renewal renewal)
    {
        System.out.println("levantando modal");
        newRenewal = new Renewal();
        newRenewal = renewal;
        Faces.getRequestContext().update("renewalModal");
        setModalView("modals/edit.xhtml");
        Faces.getRequestContext().update("formNewRenewal");
        Faces.getRequestContext().execute("show('#addRenewalModalContainer');");
    }

    public void edit()
    {
        try
        {
            getPrestamoServicio().actualizar(newRenewal.getPrestamoPorRenovacion());
            auditoria(EnumEntidadAuditoria.RENOVACION.getId(),EnumTipoAuditoria.DATOS.getId(),EnumTipoOperacionAuditoria.MODIFICACION.getId(),newRenewal.getPrestamoPorRenovacion().getId());
            auditoriaRenovacion(newRenewal.getPrestamoPorRenovacion(),newRenewal.getPrestamoOrigenRenovacion(),EnumTipoOperacionAuditoria.MODIFICACION.getId());
            
            Faces.getRequestContext().execute("hide('#addRenewalModalContainer');");
            init();
        }
        catch (Exception ex)
        {
            Logger.getLogger(RenewalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Renewal getRenewal()
    {
        return renewal;
    }

    public void setRenewal(Renewal renewal)
    {
        this.renewal = renewal;
    }

    public LazyDataModel getRenewals()
    {
        return renewals;
    }

    public void setRenewals(LazyDataModel renewals)
    {
        this.renewals = renewals;
    }

    public RenewalService getRenewalService()
    {
        return renewalService;
    }

    public void setRenewalService(RenewalService renewalService)
    {
        this.renewalService = renewalService;
    }

    public List<Renewal> getRenewalList()
    {
        if (renewalList == null)
        {
            renewalList = new ArrayList<>();
        }
        return renewalList;
    }

    public void setRenewalList(List<Renewal> renewalList)
    {
        this.renewalList = renewalList;
    }

    public double getTotalAmountRenewals()
    {
        return totalAmountRenewals;
    }

    public void setTotalAmountRenewals(double totalAmountRenewals)
    {
        this.totalAmountRenewals = totalAmountRenewals;
    }

    public List<Cliente> getClientes()
    {
        if (clientes == null)
        {
            clientes = new ArrayList<>();
        }
        return clientes;
    }

    public void setClientes(List<Cliente> clientes)
    {
        this.clientes = clientes;
    }

    public ClienteServicio getClienteServicio()
    {
        return clienteServicio;
    }

    public void setClienteServicio(ClienteServicio clienteServicio)
    {
        this.clienteServicio = clienteServicio;
    }

    public String getModalView()
    {
        return modalView;
    }

    public void setModalView(String modalView)
    {
        this.modalView = modalView;
    }

    public List<TipoPrestamo> getTipoPrestamoList()
    {
        if (tipoPrestamoList == null)
        {
            tipoPrestamoList = new ArrayList<>();
        }
        return tipoPrestamoList;
    }

    public void setTipoPrestamoList(List<TipoPrestamo> tipoPrestamoList)
    {
        this.tipoPrestamoList = tipoPrestamoList;
    }

    public TipoPrestamoServicio getTipoPrestamoServicio()
    {
        return tipoPrestamoServicio;
    }

    public void setTipoPrestamoServicio(TipoPrestamoServicio tipoPrestamoServicio)
    {
        this.tipoPrestamoServicio = tipoPrestamoServicio;
    }

    public PrestamoServicio getPrestamoServicio()
    {
        return prestamoServicio;
    }

    public void setPrestamoServicio(PrestamoServicio prestamoServicio)
    {
        this.prestamoServicio = prestamoServicio;
    }

    public List<Prestamo> getPrestamos()
    {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos)
    {
        this.prestamos = prestamos;
    }

    public Renewal getNewRenewal()
    {
        if (newRenewal == null)
        {
            newRenewal = new Renewal();
        }
        return newRenewal;
    }

    public void setNewRenewal(Renewal newRenewal)
    {
        this.newRenewal = newRenewal;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }
}
