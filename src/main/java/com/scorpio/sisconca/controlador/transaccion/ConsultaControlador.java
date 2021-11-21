/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.transaccion;

import com.scorpio.sisconca.entidad.Artefacto;
import com.scorpio.sisconca.entidad.Cliente;
import com.scorpio.sisconca.entidad.Consulta;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.Estado;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.Prestamo;
import com.scorpio.sisconca.entidad.Sede;
import com.scorpio.sisconca.entidad.TipoPrestamo;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.servicio.maestros.ClienteServicio;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.seguridad.EstadoServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.SedeServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.servicio.transaccion.ConsultaServicio;
import com.scorpio.sisconca.servicio.transaccion.PrestamoServicio;
import com.scorpio.sisconca.servicio.transaccion.TipoPrestamoServicio;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author YVAN
 */
@ManagedBean
@SessionScoped
public class ConsultaControlador implements Serializable {

    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;

    @ManagedProperty("#{clienteServicio}")
    private ClienteServicio clienteServicio;

    @ManagedProperty("#{consultaServicio}")
    private ConsultaServicio consultaServicio;
    
    @ManagedProperty("#{estadoServicio}")
    private EstadoServicio estadoServicio;
    
    @ManagedProperty("#{sedeServicio}")
    private SedeServicio sedeServicio;
    
    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;
    
    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;
    
    @ManagedProperty("#{prestamoServicio}")
    private PrestamoServicio prestamoServicio;

    public PrestamoServicio getPrestamoServicio() {
        return prestamoServicio;
    }

    public void setPrestamoServicio(PrestamoServicio prestamoServicio) {
        this.prestamoServicio = prestamoServicio;
    }

    public UsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
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
    
    private List<Sede> listaSede;

    public List<Sede> getListaSede() {
        return listaSede;
    }

    public void setListaSede(List<Sede> listaSede) {
        this.listaSede = listaSede;
    }

    public EstadoServicio getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public ConsultaServicio getConsultaServicio() {
        return consultaServicio;
    }

    public void setConsultaServicio(ConsultaServicio consultaServicio) {
        this.consultaServicio = consultaServicio;
    }

    public ClienteServicio getClienteServicio() {
        return clienteServicio;
    }

    public void setClienteServicio(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    public EmpleadoServicio getEmpleadoServicio() {
        return empleadoServicio;
    }

    public void setEmpleadoServicio(EmpleadoServicio empleadoServicio) {
        this.empleadoServicio = empleadoServicio;
    }

    public TipoPrestamoServicio getTipoPrestamoServicio() {
        return tipoPrestamoServicio;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void setTipoPrestamoServicio(TipoPrestamoServicio tipoPrestamoServicio) {
        this.tipoPrestamoServicio = tipoPrestamoServicio;
    }

    @ManagedProperty("#{tipoPrestamoServicio}")
    private TipoPrestamoServicio tipoPrestamoServicio;

    private List<Prestamo> prestamos;

    private List<Consulta> consulta;

    public List<Consulta> getConsulta() {
        return consulta;
    }

    public void setConsulta(List<Consulta> consulta) {
        this.consulta = consulta;
    }

    //private Prestamo prestamoFiltro;
    private Consulta consultaFiltro;

    public Consulta getConsultaFiltro() {
        return consultaFiltro;
    }

    public void setConsultaFiltro(Consulta consultaFiltro) {
        this.consultaFiltro = consultaFiltro;
    }

    private List<Cliente> clientes;
    
    
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
    
    public void asignarSedeFiltro(){
        
        try {
            if (!isSoporte()) {
                /*Para sede en el menú derecho*/
                Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
                Empleado empleado = empleadoServicio.obtenerPorCodigo((usuario).getIdEmpleado().getId());
                
                getConsultaFiltro().setFilterIdSede(empleado.getIdSede().getId());
                
                
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciarConsultaPrestamoPorCobrar() {
        
        iniciarListaSede();
        setConsultaFiltro(new Consulta());
        asignarSedeFiltro();

        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, 1);

        getConsultaFiltro().setFilterStart(today.getTime());
        getConsultaFiltro().setFilterEnd(Calendar.getInstance().getTime());

        consultaPrestamosPorCobrar();

        Faces.getRequestContext().update("formListaPrestamos");
        Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }

    public void actualizarConsultaPrestamosPorCobrar() {

        consultaPrestamosPorCobrar();

        Faces.getRequestContext().update("formListaPrestamos");
        Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

    }

    public void consultaPrestamosPorCobrar() {
        try {

            clientes = clienteServicio.obtenerTodo();
            prestamos = new ArrayList<>();

            List<Object> result = (List<Object>) consultaServicio.getListaPrestamosPorCobrar(consultaFiltro);

            Iterator itr = result.iterator();
            int i = 0;
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                for (Object object : obj) {
                    System.out.println("R: " + object);
                }
                
                Prestamo prestamo = prestamoServicio.obtenerPorCodigo((int) obj[0]);

                Prestamo pp = new Prestamo();
                pp.setIdSede(prestamo.getIdSede());
                pp.setId((int) obj[0]);
                pp.setFecha((Date) obj[1]);
                pp.setPrestamo((double) obj[2]);
                pp.setCuota((double) obj[3]);
                pp.setMontoPagar((double) obj[4]);
                Empleado e = empleadoServicio.obtenerPorCodigo((int) obj[5]);
                pp.setIdEmpleado(e);
                pp.setIdEstado(new Estado((int) obj[6]));
                pp.setIdArtefacto(new Artefacto((int) obj[7]));
                TipoPrestamo tp = new TipoPrestamo();
                tp = tipoPrestamoServicio.obtenerPorCodigo((int) obj[8]);
                pp.setIdTipoPrestamo(tp);
                Cliente c = new Cliente();
                c = clienteServicio.obtenerPorCodigo((int) obj[9]);
                pp.setIdCliente(c);
                pp.setGarante(c.getId());
                pp.setFrecuencia((int) obj[11]);
                pp.setPagado((double) obj[16]);
                prestamos.add(pp);
            }

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConsultaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void reportePdfPrestamosPorCobrar() throws JRException, IOException {
        try {

            consultaPrestamosPorCobrar();

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("txtUsuario", "Scorpio");
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("view/pages/reporte/prestamoPorCobrar/reporteListaPrestamos.jasper"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(prestamos));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            response.addHeader("Content-disposition", "attachment;filename=Reporteppc.pdf");

            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConsultaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void iniciarConsultaRecuperacionUtilidades() {
        
        iniciarListaSede();

        setConsultaFiltro(new Consulta());
        
        asignarSedeFiltro();

        Calendar startDay = Calendar.getInstance();
        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);
        startDay.set(Calendar.SECOND, 0);
        startDay.set(Calendar.DAY_OF_MONTH, 1);
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        
        
        getConsultaFiltro().setFilterStart(startDay.getTime());        
        getConsultaFiltro().setFilterEnd(today.getTime());
        
        getConsultaFiltro().setFilterNombreCliente("");

        consultaRecuperacionUtilidades();

        Faces.getRequestContext().update("formListaPrestamos");
        Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }

    public void actualizarConsultaRecuperacionUtilidades() {

        consultaRecuperacionUtilidades();

        Faces.getRequestContext().update("formListaPrestamos");
        Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

    }

    public void consultaRecuperacionUtilidades() {
        try {

            clientes = clienteServicio.obtenerTodo();
            consulta = new ArrayList<>();

            List<Object> result = (List<Object>) consultaServicio.getListaRecuperacionUtilidades(consultaFiltro);

            Iterator itr = result.iterator();
            int i = 0;
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                for (Object object : obj) {
                    System.out.println("R: " + object);
                }
                
                Prestamo prestamo = prestamoServicio.obtenerPorCodigo((int) obj[0]);

                Consulta obtenerConsulta = new Consulta();
                obtenerConsulta.setNombreSede(prestamo.getIdSede().getNombre());
                obtenerConsulta.setId((int) obj[0]);
                obtenerConsulta.setFecha((Date) obj[1]);
                obtenerConsulta.setPrestamo((double) obj[2]);
                obtenerConsulta.setCuota((double) obj[3]);
                obtenerConsulta.setMontoPagar((double) obj[4]);
                Empleado e = empleadoServicio.obtenerPorCodigo((int) obj[5]);
                obtenerConsulta.setNombreCobrador(e.getNombreCompleto());
                Estado est = new Estado();
                est = estadoServicio.obtenerPorCodigo((int) obj[6]);
                obtenerConsulta.setEstadoPrestamo(est.getNombre());
                TipoPrestamo tp = new TipoPrestamo();
                tp = tipoPrestamoServicio.obtenerPorCodigo((int) obj[8]);
                obtenerConsulta.setTipoPrestamo(tp.getNombre());
                Cliente c = new Cliente();
                c = clienteServicio.obtenerPorCodigo((int) obj[9]);
                obtenerConsulta.setNombreCliente(c.getNombreCompleto());
                obtenerConsulta.setPago((double) obj[11]);

                consulta.add(obtenerConsulta);
            }

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConsultaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void reportePdfRecuperacionUtilidades() throws JRException, IOException {
        try {

            consultaRecuperacionUtilidades();

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("txtUsuario", "Scorpio");
            parametros.put("start", consultaFiltro.getFilterStart());
            parametros.put("end", consultaFiltro.getFilterEnd());
            
            if(consultaFiltro.getFilterIdSede()!=null){
                Sede sede = sedeServicio.obtenerPorCodigo(consultaFiltro.getFilterIdSede());
                parametros.put("nombreSede", sede.getNombre());
            }else{
                parametros.put("nombreSede", "TODOS");
            }
            
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("view/pages/reporte/recuperacionUtilidades/reporteRecuperacionUtilidades.jasper"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(consulta));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            response.addHeader("Content-disposition", "attachment;filename=Reporteru.pdf");

            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConsultaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void consultaResumenPrestamos() {
        try {

            getConsultaFiltro().setFilterNombreCliente("");
            clientes = clienteServicio.obtenerTodo();
            
            consulta = new ArrayList<>();
            

            List<Object> result = (List<Object>) consultaServicio.getListaResumenPrestamos(consultaFiltro);
            

            Iterator itr = result.iterator();
            int i = 0;
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                for (Object object : obj) {
                    System.out.println("R: " + object);
                }

                Consulta obtenerConsulta = new Consulta();
                
                obtenerConsulta.setFecha((Date) obj[0]);
                obtenerConsulta.setCantidad(((BigInteger) obj[1]).intValue());                
                obtenerConsulta.setPrestamo((double) obj[2]);                
                obtenerConsulta.setMontoPagar((double) obj[3]);
                obtenerConsulta.setInteres((double) obj[4]);
                
                consulta.add(obtenerConsulta);
            }
            

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConsultaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void iniciarConsultaResumenPrestamos() {
        
        iniciarListaSede();

        setConsultaFiltro(new Consulta());
        
        asignarSedeFiltro();

        Calendar startDay = Calendar.getInstance();
        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);
        startDay.set(Calendar.SECOND, 0);
        startDay.set(Calendar.DAY_OF_MONTH, 1);
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        
        
        getConsultaFiltro().setFilterStart(startDay.getTime());        
        getConsultaFiltro().setFilterEnd(today.getTime());
        
        
        

        consultaResumenPrestamos();

        Faces.getRequestContext().update("formListaPrestamos");
        Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }
    public void actualizarConsultaResumenPrestamos() {

        consultaResumenPrestamos();

        Faces.getRequestContext().update("formListaPrestamos");
        Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

    }
    
    public void reportePdfResumenPrestamos() throws JRException, IOException {
        try {

            consultaResumenPrestamos();

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("txtUsuario", "Scorpio");
            if(consultaFiltro.getFilterIdSede()!=null){
                Sede sede = sedeServicio.obtenerPorCodigo(consultaFiltro.getFilterIdSede());
                parametros.put("txtSede", sede.getNombre());
            }else{
                parametros.put("txtSede", "TODOS");
            }
            
            
            parametros.put("start", consultaFiltro.getFilterStart());
            parametros.put("end", consultaFiltro.getFilterEnd());
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("view/pages/reporte/resumenPrestamos/reporteResumenPrestamos.jasper"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(consulta));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            response.addHeader("Content-disposition", "attachment;filename=Reporterp.pdf");

            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConsultaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void consultaPrestamosVencidos() {
        try {

            
            clientes = clienteServicio.obtenerTodo();
            
            consulta = new ArrayList<>();
            

            List<Object> result = (List<Object>) consultaServicio.getListaPrestamosVencidos(consultaFiltro);
            

            Iterator itr = result.iterator();
            int i = 0;
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                for (Object object : obj) {
                    System.out.println("R: " + object);
                }
                Prestamo prestamo = prestamoServicio.obtenerPorCodigo((int) obj[0]);

                Consulta obtenerConsulta = new Consulta();
                obtenerConsulta.setNombreSede(prestamo.getIdSede().getNombre());
                obtenerConsulta.setId((int) obj[0]);
                obtenerConsulta.setFecha((Date) obj[1]);
                obtenerConsulta.setNombreCliente((String) obj[2]);
                obtenerConsulta.setTipoPrestamo((String) obj[3]);
                
                obtenerConsulta.setCuota((double) obj[4]);                
                obtenerConsulta.setMontoPagar((double) obj[5]);
                obtenerConsulta.setPago((double) obj[6]);
                obtenerConsulta.setSaldo((double) obj[7]);
                obtenerConsulta.setZona((String) obj[8]);
                consulta.add(obtenerConsulta);
            }
            

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConsultaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void iniciarConsultaPrestamosVencidos() {
        
        iniciarListaSede();

        setConsultaFiltro(new Consulta());
        
        asignarSedeFiltro();

        Calendar startDay = Calendar.getInstance();
        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);
        startDay.set(Calendar.SECOND, 0);
        startDay.set(Calendar.DAY_OF_MONTH, 1);
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        
        
                
        getConsultaFiltro().setFilterEnd(today.getTime());
        getConsultaFiltro().setFilterNombreCliente("");
        getConsultaFiltro().setFilterZona("");
        
        

        consultaPrestamosVencidos();

        Faces.getRequestContext().update("formListaPrestamos");
        Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }
    public void actualizarConsultaPrestamosVencidos() {

        consultaPrestamosVencidos();

        Faces.getRequestContext().update("formListaPrestamos");
        Faces.getRequestContext().update("formListaPrestamos:dataTablePrestamo");
        Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

    }
    
    public void reportePdfPrestamosVencidos() throws JRException, IOException {
        try {

            consultaPrestamosVencidos();

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("txtUsuario", "Scorpio");
            parametros.put("start", consultaFiltro.getFilterStart());
            parametros.put("end", consultaFiltro.getFilterEnd());
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("view/pages/reporte/prestamosVencidos/reportePrestamosVencidos.jasper"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(consulta));

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            response.addHeader("Content-disposition", "attachment;filename=Reportepv.pdf");

            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConsultaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    

}
