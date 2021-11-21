/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.maestros;

import com.scorpio.sisconca.entidad.TipoCambio;
import com.scorpio.sisconca.servicio.maestros.TipoCambioServicio;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.LazyDataModel;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author YVAN
 */
@ManagedBean
@SessionScoped
public class TipoCambioControlador implements Serializable {

    private static final Logger LOG = Logger.getLogger(TipoCambioControlador.class.getName());
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{tipoCambioServicio}")
    private TipoCambioServicio tipoCambioServicio;

    private String contenidoModalTipoCambio = "modal-agregar-tipoCambio.xhtml";
    private List<TipoCambio> listaTipoCambio;
    private LazyDataModel<TipoCambio> listaLazyTipoCambio;

    public LazyDataModel<TipoCambio> getListaLazyTipoCambio() {
        return listaLazyTipoCambio;
    }

    public void setListaLazyTipoCambio(LazyDataModel<TipoCambio> listaLazyTipoCambio) {
        this.listaLazyTipoCambio = listaLazyTipoCambio;
    }
    private TipoCambio tipoCambioFiltro;

    //agregar
    private TipoCambio tipoCambioAgregar;
    //modificar
    private TipoCambio tipoCambioModificar;

    public void iniciar() throws Exception {

        setTipoCambioFiltro(new TipoCambio());
        iniciarListaTipoCambio();
        lazyListTable();
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");

    }

    public void iniciarListaTipoCambio() {

        try {
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaTipoCambio");

        } catch (Exception e) {
            LOG.error("Error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo ___realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarTipoCambio() {
        try {
            iniciarListaTipoCambio();
            TipoCambio tipoCambioAdd = new TipoCambio();
            tipoCambioAdd.setFecha(new Date());
            setTipoCambioAgregar(tipoCambioAdd);
            Faces.getRequestContext().update("divModalTipoCambio");
            setContenidoModalTipoCambio("modal-agregar-tipoCambio.xhtml");
            Faces.getRequestContext().update("formTipoCambio");
            Faces.getRequestContext().execute("show('#modalTipoCambio');");
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }
    
    /**
     * CARGA DE DATOS A LA TABLA PAGINADA
     * @throws Exception 
     */
    public void lazyListTable() throws Exception {
        if (listaLazyTipoCambio == null) {
            listaLazyTipoCambio = new LazyDataModel<TipoCambio>() {
                private static final long serialVersionUID = 1L;

                @Override
                public List<TipoCambio> load(int first, int pageSize, String sortField, org.primefaces.model.SortOrder sortOrder, Map<String, Object> filters) {
                    List<TipoCambio> listData = null;
                    try{
                        getTipoCambioFiltro().setFirst(first);
                        getTipoCambioFiltro().setPageSize(pageSize);
                        listData = getTipoCambioServicio().listarPorFecha(tipoCambioFiltro);
                    }catch (Exception e) {LOG.error("error: " + e);}
                    
                    return listData;
                }
            };
            listaLazyTipoCambio.setRowCount(getTipoCambioServicio().getCountLazy(tipoCambioFiltro).intValue());
        }
    }    

    public void deleteFiltro() throws Exception {
        tipoCambioFiltro.setFilterEnd(null);
        tipoCambioFiltro.setFilterStart(null);
        listaLazyTipoCambio.setRowCount(getTipoCambioServicio().getCountLazy(tipoCambioFiltro).intValue());
        Faces.getRequestContext().update("formListaTipoCambio:dataTableTipoCambio");
    }
    
    public void filtrar() throws Exception{
        listaLazyTipoCambio.setRowCount(getTipoCambioServicio().getCountLazy(tipoCambioFiltro).intValue());
        Faces.getRequestContext().update("formListaTipoCambio:dataTableTipoCambio");
    }
    
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarTipoCambio() {
        try {
            getTipoCambioServicio().guardar(getTipoCambioAgregar());
            Faces.getRequestContext().execute("hide('#modalTipoCambio');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente al tipo de cambio.", FacesMessage.SEVERITY_INFO);
            iniciarListaTipoCambio();
            Faces.getRequestContext().update("formListaTipoCambio");
            Faces.getRequestContext().update("formListaTipoCambio:dataTableTipoCambio");

        } catch (ConstraintViolationException e) {
            Faces.addMessage("", "Existe un tipo de cambio registrado.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }

    }

    public void mostrarModalModificarTipoCambio(TipoCambio tipoCambio) {
        try {
            setTipoCambioModificar(tipoCambio);

            Faces.getRequestContext().update("divModalTipoCambio");
            setContenidoModalTipoCambio("modal-modificar-tipoCambio.xhtml");
            Faces.getRequestContext().update("formTipoCambio");
            Faces.getRequestContext().execute("show('#modalTipoCambio');");
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarTipoCambio() {
        try {

            getTipoCambioServicio().actualizar(getTipoCambioModificar());
            Faces.getRequestContext().execute("hide('#modalTipoCambio');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente el tipo de cambio para la fecha "
                    + getTipoCambioModificar().getFecha(), FacesMessage.SEVERITY_INFO);
            iniciarListaTipoCambio();
            Faces.getRequestContext().update("formListaTipoCambio");
            Faces.getRequestContext().update("formListaTipoCambio:dataTableTipoCambio");

        } catch (DataIntegrityViolationException e) {
            Faces.addMessage("", "Existe un tipo de cambio registrado en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e) {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public TipoCambioServicio getTipoCambioServicio() {
        return tipoCambioServicio;
    }

    public void setTipoCambioServicio(TipoCambioServicio tipoCambioServicio) {
        this.tipoCambioServicio = tipoCambioServicio;
    }

    public String getContenidoModalTipoCambio() {
        return contenidoModalTipoCambio;
    }

    public void setContenidoModalTipoCambio(String contenidoModalTipoCambio) {
        this.contenidoModalTipoCambio = contenidoModalTipoCambio;
    }

    public List<TipoCambio> getListaTipoCambio() {
        return listaTipoCambio;
    }

    public void setListaTipoCambio(List<TipoCambio> listaTipoCambio) {
        this.listaTipoCambio = listaTipoCambio;
    }

    public TipoCambio getTipoCambioFiltro() {
        return tipoCambioFiltro;
    }

    public void setTipoCambioFiltro(TipoCambio tipoCambioFiltro) {
        this.tipoCambioFiltro = tipoCambioFiltro;
    }

    public TipoCambio getTipoCambioAgregar() {
        return tipoCambioAgregar;
    }

    public void setTipoCambioAgregar(TipoCambio tipoCambioAgregar) {
        this.tipoCambioAgregar = tipoCambioAgregar;
    }

    public TipoCambio getTipoCambioModificar() {
        return tipoCambioModificar;
    }

    public void setTipoCambioModificar(TipoCambio tipoCambioModificar) {
        this.tipoCambioModificar = tipoCambioModificar;
    }

}
