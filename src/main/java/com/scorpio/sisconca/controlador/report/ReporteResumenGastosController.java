/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.report;

import com.scorpio.sisconca.dao.GeneralDao;
import com.scorpio.sisconca.entidad.Area;
import com.scorpio.sisconca.entidad.Gasto;
import com.scorpio.sisconca.entidad.vistas.ViewResumenGastos;
import com.scorpio.sisconca.servicio.ReportService;
import com.scorpio.sisconca.utilitario.IReportManager;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.core.env.Environment;

/**
 *
 * @author Max Dicson
 */
@ManagedBean
@SessionScoped
public class ReporteResumenGastosController implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{generalDao}")
    private GeneralDao generalDao;
    @ManagedProperty("#{reportService}")
    private ReportService reportService;

    private List<Area> areas;
    private LazyDataModel<ViewResumenGastos> lazyModel;
    //Filtros
    private Date fechaInicio;
    private Date fechaFin;
    private String concepto;
    private Integer idArea;

    public void init() throws Exception {
        areas = generalDao.listarHQL("from Area a where a.estado=true", null, null);
        fechaFin = new Date();
        fechaInicio = new Date();
        fechaInicio.setMonth(fechaInicio.getMonth() - 1);

        concepto = "";
        idArea = null;
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }

    public void iniciandoLazy() throws Exception {

        if (lazyModel == null) {
            lazyModel = new LazyDataModel<ViewResumenGastos>() {
                @Override
                public List<ViewResumenGastos> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                    System.out.println("idArea: " + idArea);
                    Map<String, Object> param = new HashMap<>();
                    param.put("init", fechaInicio);
                    param.put("end", fechaFin);
                    param.put("concepto", "%" + concepto + "%");

                    List<ViewResumenGastos> lista = new ArrayList<>();
                    try {
                        String sql = "";
                        if (idArea != null) {
                            param.put("idArea", idArea);
                            sql = "select g.id as id, g.idArea as idArea, "
                                    + "g.descripcion as descripcion, "
                                    + "g.idConcepto as idConcepto, "
                                    + "g.nombre as nombre, "
                                    + "sum(g.gastoSoles) as gastoSoles, "
                                    + "sum(g.gastoDolares) as gastoDolares from ViewResumenGastos g where g.fecha>=:init and g.fecha<=:end "
                                    + "and idArea=:idArea and nombre like :concepto";
                        } else {
                            sql = "select g.id as id, g.idArea as idArea, "
                                    + "g.descripcion as descripcion, "
                                    + "g.idConcepto as idConcepto, "
                                    + "g.nombre as nombre, "
                                    + "sum(g.gastoSoles) as gastoSoles, "
                                    + "sum(g.gastoDolares) as gastoDolares from ViewResumenGastos g where g.fecha>=:init and g.fecha<=:end "
                                    + "and nombre like :concepto";
                        }
                        sql = sql + " group by g.idArea, g.idConcepto";
                        lista = generalDao.listarLazyHQL(sql, param, first, pageSize, ViewResumenGastos.class);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return lista;
                }
            };
            contar();
        }
    }

    public void contar() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("init", fechaInicio);
        param.put("end", fechaFin);
        param.put("concepto", "%" + concepto + "%");

        String sql = "";
        if (idArea != null) {
            param.put("idArea", idArea);
            sql = "select count(g.id) from ViewResumenGastos g where g.fecha>=:init and g.fecha<=:end "
                    + "and idArea=:idArea and nombre like :concepto";
        } else {
            sql = "select count(g.id) from ViewResumenGastos g where g.fecha>=:init and g.fecha<=:end "
                    + "and nombre like :concepto";
        }
        sql = sql + " group by idArea, idConcepto";
        int conteo = generalDao.listarHQL(sql, param, null).size();
        lazyModel.setRowCount(conteo);
    }

    public void printPDF() throws Exception {
        /**
         * $P{fecha_inicio} 
            $P{fecha_fin} 
            $P{concepto}  - debe ir con '%%' es like
            $P{id_area}
         */
        Map<String, Object> param = new HashMap<>();
        param.put("fecha_inicio", fechaInicio);
        param.put("fecha_fin", fechaFin);
        param.put("concepto", "%" + concepto + "%");
        param.put("id_area", idArea == null ? 0: idArea);
        ///Connection c = FITDataBaseUtil.getJdbcPostgres().getConnection();
        IReportManager i = new IReportManager(null);
        i.createFilePDF("resumengasto");
        i.setJasperPrint("/resources/reportes/resumen-gastos.jasper", param, reportService.connect());
        i.exportPDF("resumengasto");

    }

    public GeneralDao getGeneralDao() {
        return generalDao;
    }

    public void setGeneralDao(GeneralDao generalDao) {
        this.generalDao = generalDao;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
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

    public String getConcepto() {
        return concepto;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public LazyDataModel<ViewResumenGastos> getLazyModel() throws Exception {
        if (lazyModel == null) {
            iniciandoLazy();
        }
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<ViewResumenGastos> lazyModel) {
        this.lazyModel = lazyModel;
    }

}
