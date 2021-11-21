/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.mantenimiento;

import com.scorpio.sisconca.dao.mantenimiento.EmpresaDao;
import com.scorpio.sisconca.dao.transaccion.ConceptoGastoDao;
import com.scorpio.sisconca.dao.transaccion.GastoDao;
import com.scorpio.sisconca.entidad.ConceptoGasto;
import com.scorpio.sisconca.entidad.Area;
import com.scorpio.sisconca.entidad.Gasto;
import com.scorpio.sisconca.utilitario.jsf.Faces;
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
public class AreaController implements Serializable {

    private LazyDataModel<Area> lazyModel;

    @ManagedProperty("#{empresaDao}")
    private EmpresaDao empresaDao;

    private Area empresa;
    private String filtro = "";

    public void init() {
        System.out.println("inicializando GAsto Controller");

        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");

    }

    public void iniciandoLazy() throws Exception {
        if (lazyModel == null) {
            lazyModel = new LazyDataModel<Area>() {
                @Override
                public List<Area> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                    Map<String, Object> param = new HashMap<>();
                    List<Area> lista = new ArrayList<>();
                    try {
                        String sql = "";
                        if (!filtro.isEmpty()) {
                            sql = "from Area e where e.descripcion like :nombre";
                            param.put("nombre", "%" + filtro.replace(" ", "%") + "%");
                        } else {
                            sql = "from Area e";
                        }
                        lista = empresaDao.listarLazyHQL(sql, param, first, pageSize, null);
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
        if (!filtro.isEmpty()) {
            sql = "select count(e.id) from Area e where e.descripcion like :nombre";
            params.put("nombre", "%" + filtro.replace(" ", "%") + "%");
        } else {
            sql = "select count(e.id) from Area e";
        }
        Long conteo = empresaDao.getCountLazy(sql, params);
        lazyModel.setRowCount(conteo.intValue());
    }

    public void removeFilter() {
        filtro = "";
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void saveGasto() throws Exception {
        
        if (empresa.getId() == null) {
            Integer id = (Integer) empresaDao.guardar(empresa);

            Faces.getRequestContext().execute("hide('#modalGasto');");
            Faces.addMessage("Se ha añadido una nueva área", "", FacesMessage.SEVERITY_INFO);
        } else {
            empresaDao.actualizar(empresa);
            Faces.getRequestContext().execute("hide('#modalGasto');");
            Faces.addMessage("Se ha actualizado una área", "", FacesMessage.SEVERITY_INFO);
        }
        contar();
    }

    public void cargarGasto(Integer id) throws Exception {
        empresa = empresaDao.obtenerPorHQL("from Area g where g.id=" + id, null, null);
       
        Faces.getRequestContext().update("modalAgregarId");
        Faces.getRequestContext().execute("show('#modalGasto');");
    }

    public void mostrarModalAgregarGiro() {
        try {
            empresa = new Area();
            
            Faces.getRequestContext().update("modalAgregarId");
            Faces.getRequestContext().execute("show('#modalGasto');");
        } catch (Exception e) {
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public LazyDataModel<Area> getLazyModel() throws Exception{
        if(lazyModel == null) {
            iniciandoLazy();
        }
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Area> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public EmpresaDao getEmpresaDao() {
        return empresaDao;
    }

    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    public Area getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Area empresa) {
        this.empresa = empresa;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    
}
