/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.report;

import com.scorpio.sisconca.dao.GeneralDao;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.Sede;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.entidad.vistas.ViewResumenGastos;
import com.scorpio.sisconca.entidad.vistas.ViewResumenPagosSupervision;
import com.scorpio.sisconca.servicio.ReportService;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.SedeServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.utilitario.IReportManager;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
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
public class ReporteResumenPagosSupervisionController implements Serializable {

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

    public List<Sede> getListaSede() {
        return listaSede;
    }

    public void setListaSede(List<Sede> listaSede) {
        this.listaSede = listaSede;
    }

    private LazyDataModel<ViewResumenPagosSupervision> lazyModel;
    private List<Sede> listaSede;
    
    //Filtros
    private Date fechaInicio;
    private Date fechaFin;
    private String cobrador;
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
        cobrador = "";
        iniciarListaSede();
        asignarSedeFiltro();
        
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }

    public void iniciandoLazy() throws Exception {

        if (lazyModel == null) {
            lazyModel = new LazyDataModel<ViewResumenPagosSupervision>() {
                @Override
                public List<ViewResumenPagosSupervision> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("init", fechaInicio);
                    param.put("end", fechaFin);
                    param.put("cobrador", "%" + cobrador + "%");
                    param.put("sedeId", sedeId == null ? 0 : sedeId);

                    List<ViewResumenPagosSupervision> lista = new ArrayList<>();
                    try {
                        String sql = "select ps.id as id, ps.idEmpleado as idEmpleado, ps.cobrador as cobrador, sum(ps.importe) as importe, ps.fechaPago as fechaPago "
                                + "from ViewResumenPagosSupervision ps "
                                + "where ps.fechaPago>=:init and ps.fechaPago<=:end "
                                + " AND sede_id=:sedeId "
                                + "and ps.cobrador like :cobrador "
                                + "group by ps.idEmpleado";
                        System.out.println("sql = "+sql);
                        lista = generalDao.listarLazyHQL(sql, param, first, pageSize, ViewResumenPagosSupervision.class);

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
        param.put("init", fechaInicio);
        param.put("end", fechaFin);
        param.put("cobrador", "%" + cobrador + "%");
        param.put("sedeId", sedeId == null ? 0 : sedeId);

        String sql = "select count(ps.id) from ViewResumenPagosSupervision ps "
                + "where ps.fechaPago>=:init and ps.fechaPago<=:end "
                + " AND sede_id=:sedeId "
                + "and ps.cobrador like :cobrador "
                + "group by ps.idEmpleado";

        int conteo = generalDao.listarHQL(sql, param, null).size();
        lazyModel.setRowCount(conteo);
    }

    public void printPDF() throws Exception {
        /**
         * $P{fecha_inicio} $P{fecha_fin} $P{concepto} - debe ir con '%%' es
         * like $P{id_area}
         */
        Map<String, Object> param = new HashMap<>();
        param.put("fecha_inicio", fechaInicio);
        param.put("fecha_fin", fechaFin);
        param.put("cobrador", "%" + cobrador + "%");
        param.put("sedeId", sedeId == null ? 0 : sedeId);
        if(sedeId!=null){
                Sede sede = sedeServicio.obtenerPorCodigo(sedeId);                
                param.put("nombreSede", sede.getNombre());
            }else{
                param.put("nombreSede", "TODOS");
        }
        
        ///Connection c = FITDataBaseUtil.getJdbcPostgres().getConnection();
        IReportManager i = new IReportManager(null);
        i.createFilePDF("resumenpagosupervision");
        i.setJasperPrint("/resources/reportes/resumen-pagos-supervision.jasper", param, reportService.connect());
        i.exportPDF("resumengasto");

    }

    public GeneralDao getGeneralDao() {
        return generalDao;
    }

    public void setGeneralDao(GeneralDao generalDao) {
        this.generalDao = generalDao;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public LazyDataModel<ViewResumenPagosSupervision> getLazyModel() throws Exception {
        if (lazyModel == null) {
            iniciandoLazy();
        }
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ViewResumenPagosSupervision> lazyModel) {
        this.lazyModel = lazyModel;
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

    public String getCobrador() {
        return cobrador;
    }

    public void setCobrador(String cobrador) {
        this.cobrador = cobrador;
    }

}
