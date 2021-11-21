/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.report;

import com.scorpio.sisconca.controlador.transaccion.PaymentController;
import com.scorpio.sisconca.controlador.transaccion.RenewalController;
import com.scorpio.sisconca.dao.GeneralDao;
import com.scorpio.sisconca.entidad.Area;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.Gasto;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.Sede;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.entidad.vistas.ViewResumenGastos;
import com.scorpio.sisconca.entidad.vistas.ViewResumenPago;
import com.scorpio.sisconca.servicio.ReportService;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.SedeServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.utilitario.IReportManager;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
 *
 * @author Max Dicson
 */
@ManagedBean
@SessionScoped
public class ReporteResumenPagosController implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{generalDao}")
    private GeneralDao generalDao;
    @ManagedProperty("#{reportService}")
    private ReportService reportService;
    
    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;
    
    @ManagedProperty("#{sedeServicio}")
    private SedeServicio sedeServicio;
    
    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;
    
    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;

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
    
    
    private List<Sede> listaSede;

    public List<Sede> getListaSede() {
        return listaSede;
    }

    public void setListaSede(List<Sede> listaSede) {
        this.listaSede = listaSede;
    }
    

    public PerfilPorUsuarioServicio getPerfilPorUsuarioServicio() {
        return perfilPorUsuarioServicio;
    }

    public void setPerfilPorUsuarioServicio(PerfilPorUsuarioServicio perfilPorUsuarioServicio) {
        this.perfilPorUsuarioServicio = perfilPorUsuarioServicio;
    }

    private LazyDataModel<ViewResumenPago> lazyModel;
    //Filtros
    private Date fechaInicio;
    private Date fechaFin;
    private String indicaGrupoSede;
    private Integer sedeId;

    public String getIndicaGrupoSede() {
        return indicaGrupoSede;
    }

    public void setIndicaGrupoSede(String indicaGrupoSede) {
        this.indicaGrupoSede = indicaGrupoSede;
    }

    public Integer getSedeId() {
        return sedeId;
    }

    public void setSedeId(Integer sedeId) {
        this.sedeId = sedeId;
    }
    

    public void init() throws Exception {
        fechaFin = new Date();
        fechaInicio = new Date();
        fechaInicio.setMonth(fechaInicio.getMonth() - 1);
        
        iniciarListaSede();
        asignarSedeFiltro();
        

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }

    public void iniciandoLazy() throws Exception {

        if (lazyModel == null) {
            lazyModel = new LazyDataModel<ViewResumenPago>() {
                @Override
                public List<ViewResumenPago> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("initi", fechaInicio);
                    param.put("endi", fechaFin);
                    param.put("sedeId", sedeId);
                    
                    List<ViewResumenPago> lista = new ArrayList<>();
                    try {
                        String sql = "from ViewResumenPago g where g.fechaPago>=:initi and g.fechaPago<=:endi "
                                + " AND sede_id = :sedeId"
                                + " order by g.fechaPago desc";
                        
                        System.out.println("GeneralDao: " + generalDao);
                        lista = generalDao.listarLazyHQL(sql, param, first, pageSize, null);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return lista;
                }
            };
            contar();
        }
    }
    
    public boolean isSoporte() throws Exception {
        List<PerfilPorUsuario> perfilPorUsuarios = perfilPorUsuarioServicio.getByUserId(Faces.getUserInSession().getId());
        return perfilPorUsuarios.stream().anyMatch((perfilPorUsuario) -> (perfilPorUsuario.getIdPerfil().getId() == EnumPerfil.SOPORTE.getId()));
    }
    
    public void asignarSedeFiltro(){
        
        try {
            if (!isSoporte()) {
                /*Para sede en el menú derecho*/
                Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
                Empleado empleado = empleadoServicio.obtenerPorCodigo((usuario).getIdEmpleado().getId());
                
                setSedeId(empleado.getIdSede().getId());
                
                
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ReporteResumenPagosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void iniciarListaSede()
    {
        try
        {
            setListaSede(getSedeServicio().obtenerTodo());
            
        } catch (Exception e)
        {
            
            Logger.getLogger(ReporteResumenPagosController.class.getName()).log(Level.SEVERE, null, e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void contar() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("initi", fechaInicio);
        param.put("endi", fechaFin);
        param.put("sedeId", sedeId == null ? 0 : sedeId);

        String sql = "select (g.id) from ViewResumenPago g "
                + "where date(g.fechaPago)>=date(:initi) and date(g.fechaPago)<=date(:endi) "
                + " AND sede_id = :sedeId"
                +" order by g.fechaPago desc";
        int conteo = generalDao.listarHQL(sql, param, null).size();
        //Long conteo = generalDao.getCountLazy(sql, param);
        //if(conteo == null)
        //    conteo = (long)0;
        lazyModel.setRowCount(conteo);
    }

    public void printPDF() throws Exception {
        /**
         * $P{fecha_inicio} 
            $P{fecha_fin} 
         */
        Map<String, Object> param = new HashMap<>();
        param.put("fecha_inicio", fechaInicio);
        param.put("fecha_fin", fechaFin);
        param.put("sedeId", sedeId == null ? 0 : sedeId);
        
        if(sedeId!=null){
                Sede sede = sedeServicio.obtenerPorCodigo(sedeId);                
                param.put("nombreSede", sede.getNombre());
            }else{
                param.put("nombreSede", "TODOS");
        }
        
        ///Connection c = FITDataBaseUtil.getJdbcPostgres().getConnection();
        IReportManager i = new IReportManager(null);
        i.createFilePDF("resumenpatos");
        i.setJasperPrint("/resources/reportes/resumen-pagos.jasper", param, reportService.connect());
        i.exportPDF("resumenpatos");

    }

    public GeneralDao getGeneralDao() {
        return generalDao;
    }

    public void setGeneralDao(GeneralDao generalDao) {
        this.generalDao = generalDao;
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

    public ReportService getReportService() {
        return reportService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public LazyDataModel<ViewResumenPago> getLazyModel() throws Exception {
        if (lazyModel == null) {
            iniciandoLazy();
        }
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ViewResumenPago> lazyModel) {
        this.lazyModel = lazyModel;
    }

}
