/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.transaccion;

import com.scorpio.sisconca.dao.transaccion.ConceptoGastoDao;
import com.scorpio.sisconca.dao.transaccion.GastoDao;
import com.scorpio.sisconca.entidad.Area;
import com.scorpio.sisconca.entidad.ConceptoGasto;
import com.scorpio.sisconca.entidad.Gasto;
import com.scorpio.sisconca.entidad.TipoCambio;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import com.scorpio.sisconca.utilitario.matematica.MathUtil;
import java.io.Serializable;
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Max Dicson
 */
@ManagedBean
@SessionScoped
public class GastoController implements Serializable {

    private LazyDataModel<Gasto> lazyModel;

    @ManagedProperty("#{gastoDao}")
    private GastoDao gastoDao;
    @ManagedProperty("#{conceptoGastoDao}")
    private ConceptoGastoDao conceptoGastoDao;

    private Gasto gasto;
    private List<ConceptoGasto> conceptoGastos;
    private List<TipoCambio> tipoCambios;
    private List<Area> areas;
    private Date fechaInicio;
    private Date fechaFin;

    public void init() {
        System.out.println("inicializando GAsto Controller");

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");

    }
    
    public void onSelectItem() throws Exception {
        System.out.println("Select* " + gasto.getTipoMoneda());
        System.out.println("Select* " + gasto.getFecha());
        if(gasto.getTipoMoneda() != null && gasto.getTipoMoneda() == 1) {
            gasto.setTipoCambio(0.0);
        } else if(gasto.getTipoMoneda() != null && gasto.getFecha() != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("tipoMoneda", gasto.getTipoMoneda());
            List<TipoCambio> lista = gastoDao.listarHQL("from TipoCambio tc where tc.tipoMoneda=:tipoMoneda", params, null);
            if(lista.isEmpty()) {
                gasto.setTipoCambio(null);
                Faces.addMessage("Error, no existe un tipo de cambio para esa fecha ni el tipo de moneda", "", FacesMessage.SEVERITY_ERROR);
            } else {
                gasto.setTipoCambio(lista.get(0).getTipoCambioVenta());
                //gasto.setTipoCambio(lista.get(0).getTipoCambioCompra());
            }
        } else {
            //Faces.addMessage("Error, debe seleccionar una fecha", details, tipo_MESSAGE);
        }
    }

    public void updateImporte() {
        if(gasto != null) {
            System.out.println("IGV: " + gasto.getIgv());
            System.out.println("IGV: " + gasto.getValorNeto());
            if(gasto.getIgv() != null && gasto.getValorNeto() != null) {
                gasto.setImporte(MathUtil.round(gasto.getValorNeto() * (1 + gasto.getIgv() / 100.00), 2));
            }
        }
    }
    
    public void iniciandoLazy() throws Exception {
        if (lazyModel == null) {
            lazyModel = new LazyDataModel<Gasto>() {
                @Override
                public List<Gasto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                    Map<String, Object> param = new HashMap<>();
                    List<Gasto> lista = new ArrayList<>();
                    try {
                        String sql = "";
                        if (fechaInicio != null && fechaFin != null) {
                            sql = "from Gasto g where g.fecha>=:fechaI and g.fecha<=:fechaF";
                            param.put("fechaI", fechaInicio);
                            param.put("fechaF", fechaFin);
                        } else if (fechaInicio != null && fechaFin == null) {
                            sql = "from Gasto g where g.fecha>=:fechaI";
                            param.put("fechaI", fechaInicio);
                        } else if (fechaInicio == null && fechaFin != null) {
                            sql = "from Gasto g where g.fecha<=:fechaF";
                            param.put("fechaF", fechaFin);
                        } else {
                            sql = "from Gasto g";
                        }
                        lista = gastoDao.listarLazyHQL(sql, param, first, pageSize, null);
                        System.out.println("Lista: " + lista.size());
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
        Map<String, Object> params = new HashMap<>();
        String sql = "";
        if (fechaInicio != null && fechaFin != null) {
            sql = "select count(g.id) from Gasto g where g.fecha>=:fechaI and g.fecha<=:fechaF";
            params.put("fechaI", fechaInicio);
            params.put("fechaF", fechaFin);
        } else if (fechaInicio != null && fechaFin == null) {
            sql = "select count(g.id) from Gasto g where g.fecha>=:fechaI";
            params.put("fechaI", fechaInicio);
        } else if (fechaInicio == null && fechaFin != null) {
            sql = "select count(g.id) from Gasto g where g.fecha<=:fechaF";
            params.put("fechaF", fechaFin);
        } else {
            sql = "select count(g.id) from Gasto g";
        }
        Long conteo = gastoDao.getCountLazy(sql, params);
        lazyModel.setRowCount(conteo.intValue());
    }
    
    public void removeFilter() {
        fechaFin = null;
        fechaInicio = null;
        
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void saveGasto() throws Exception {
        if(gasto.getIgv() != null && gasto.getIgv() > 99.99) {
            Faces.addMessage("El IGV debe ser menor a 99.99", "", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if(gasto.getImporte() == null || gasto.getTipoCambio() == null) {
            Faces.addMessage("Error, Debe ingresar todos los registros!", "", FacesMessage.SEVERITY_ERROR);
            return;
        }
        System.out.println("IGV: " + gasto.getIgv());
        if (gasto.getEstadoTemp()) {
            gasto.setEstado(1);
        } else {
            gasto.setEstado(0);
        }

        if (gasto.getId() == null) {
            Integer id = (Integer) gastoDao.guardar(gasto);

            Faces.getRequestContext().execute("hide('#modalGasto');");
            Faces.addMessage("Se ha añadido un nuevo gasto", "", FacesMessage.SEVERITY_INFO);
        } else {
            gastoDao.actualizar(gasto);
            Faces.getRequestContext().execute("hide('#modalGasto');");
            Faces.addMessage("Se ha actualizado un nuevo gasto", "", FacesMessage.SEVERITY_INFO);
        }
    }

    public void cargarGasto(Integer id) throws Exception {
        gasto = gastoDao.obtenerPorHQL("from Gasto g where g.id=" + id, null, null);
        System.out.println("Area: " + gasto.getIdArea().getDescripcion());
        System.out.println("Concepto: " + gasto.getIdConcepto().getNombre());
        
        if (gasto.getEstado() == 1) {
            gasto.setEstadoTemp(Boolean.TRUE);
        } else {
            gasto.setEstadoTemp(Boolean.FALSE);
        }

        conceptoGastos = conceptoGastoDao.listarHQL("from ConceptoGasto c", null, null);
        areas = conceptoGastoDao.listarHQL("from Area a where a.estado=true", null, null);
        ///tipoCambios = conceptoGastoDao.listarHQL("from TipoCambio t", null, null);
        
        Faces.getRequestContext().update("modalAgregarId");
        Faces.getRequestContext().execute("show('#modalGasto');");
    }

    public void mostrarModalAgregarGiro() {
        try {
            gasto = new Gasto();
            gasto.setIdArea(new Area());
            gasto.setIdConcepto(new ConceptoGasto());
            
            conceptoGastos = conceptoGastoDao.listarHQL("from ConceptoGasto c", null, null);
            areas = conceptoGastoDao.listarHQL("from Area a where a.estado=true", null, null);
            //tipoCambios = conceptoGastoDao.listarHQL("from TipoCambio t", null, null);
            
            Faces.getRequestContext().update("modalAgregarId");
            Faces.getRequestContext().execute("show('#modalGasto');");
        } catch (Exception e) {
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void update() {
        Map<String, Object> param = new HashMap<>();
        //gastoDao.
        lazyModel.setRowCount(0);
    }

    public LazyDataModel<Gasto> getLazyModel() throws Exception {
        if (lazyModel == null) {

            iniciandoLazy();
        }
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Gasto> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public GastoDao getGastoDao() {
        return gastoDao;
    }

    public void setGastoDao(GastoDao gastoDao) {
        this.gastoDao = gastoDao;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }

    public ConceptoGastoDao getConceptoGastoDao() {
        return conceptoGastoDao;
    }

    public void setConceptoGastoDao(ConceptoGastoDao conceptoGastoDao) {
        this.conceptoGastoDao = conceptoGastoDao;
    }

    public List<ConceptoGasto> getConceptoGastos() {
        return conceptoGastos;
    }

    public void setConceptoGastos(List<ConceptoGasto> conceptoGastos) {
        this.conceptoGastos = conceptoGastos;
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

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public List<TipoCambio> getTipoCambios() {
        return tipoCambios;
    }

    public void setTipoCambios(List<TipoCambio> tipoCambios) {
        this.tipoCambios = tipoCambios;
    }

}
