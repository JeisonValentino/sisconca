package com.scorpio.sisconca.controlador.transaccion;

import com.scorpio.sisconca.dao.mantenimiento.EmpresaDao;
import com.scorpio.sisconca.dao.transaccion.ConceptoGastoAreaDao;
import com.scorpio.sisconca.entidad.ConceptoGasto;
import com.scorpio.sisconca.entidad.Area;
import com.scorpio.sisconca.entidad.ConceptoGastoArea;
import com.scorpio.sisconca.servicio.transaccion.ConceptoGastoServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@ManagedBean
@SessionScoped
public class ConceptoGastoControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(ConceptoGastoControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{conceptoGastoServicio}")
    private ConceptoGastoServicio conceptoGastoServicio;
    @ManagedProperty("#{conceptoGastoAreaDao}")
    private ConceptoGastoAreaDao conceptoGastoAreaDao;
    @ManagedProperty("#{empresaDao}")
    private EmpresaDao empresaDao;

    private List<ConceptoGasto> listaConceptoGastos;
    private List<Area> areas;
    private Area area;
    private List<ConceptoGastoArea> conceptoGastoAreas;
    
    private LazyDataModel<ConceptoGasto> listaLazyConceptoGastos;

    // Agregar/Modificar ConceptoGasto
    private boolean estadoConceptoGasto;

    // Agregar
    private ConceptoGasto conceptoGastoAgregar;

    //Modificar
    private ConceptoGasto conceptoGastoModificar;

    //Fltro
    private ConceptoGasto conceptoGastoFiltro;

    private String contenidoModalConceptoGasto = "modal-agregar-conceptoGasto.xhtml";
    private int activo;
    private int inactivo;

    public void iniciar()
    {
        setConceptoGastoFiltro(new ConceptoGasto());
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        iniciarListaConceptoGastos();
        //Faces.getRequestContext().update("menu");
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }

    public void iniciarListaConceptoGastos()
    {
        try
        {
            ConceptoGasto conceptoGastoFiltroInicio = new ConceptoGasto();
            conceptoGastoFiltroInicio.getIdEstado().setId(EnumEstado.ACTIVO.getId());
            conceptoGastoFiltroInicio.setNombre("");
            listaLazyConceptoGastos = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<ConceptoGasto> listaConceptoGastos = new ArrayList<>();
                    try
                    {
                        conceptoGastoFiltroInicio.setFirst(first);
                        conceptoGastoFiltroInicio.setPageSize(pageSize);
                        listaConceptoGastos = getConceptoGastoServicio().listarFiltro(conceptoGastoFiltroInicio);
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaConceptoGastos;
                }
            };
            listaLazyConceptoGastos.setRowCount(getConceptoGastoServicio().contarFiltro(conceptoGastoFiltroInicio));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaConceptoGastos:dataTableConceptoGasto");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void agregarArea() {
        for (ConceptoGastoArea conceptoGastoArea : conceptoGastoAreas) {
            if(conceptoGastoArea.getIdArea().getId().intValue() == area.getId().intValue()) {
                Faces.addMessage("Ya se encuentra el área!", "", FacesMessage.SEVERITY_WARN);
                return;
            }
        }
        ConceptoGastoArea cg = new ConceptoGastoArea();
        cg.setIdArea(area);
        cg.setIdConceptoGasto(conceptoGastoAgregar);
        
        this.conceptoGastoAreas.add(cg);
        
        area = null;
    }
    
    public void agregarAreaGuardado() throws Exception{
        for (ConceptoGastoArea conceptoGastoArea : conceptoGastoAreas) {
            if(conceptoGastoArea.getIdArea().getId().intValue() == area.getId().intValue()) {
                Faces.addMessage("Ya se encuentra el área!", "", FacesMessage.SEVERITY_WARN);
                return;
            }
        }
        ConceptoGastoArea cg = new ConceptoGastoArea();
        cg.setIdArea(area);
        
        ConceptoGasto conceptoGasto = new ConceptoGasto();
        if (conceptoGastoAgregar!=null){
            conceptoGasto=conceptoGastoAgregar;
        }else{
            conceptoGasto=conceptoGastoModificar;
        }
        cg.setIdConceptoGasto(conceptoGasto);
        
        Integer idd = (Integer)conceptoGastoAreaDao.guardar(cg);
        cg.setId(idd);
        
        this.conceptoGastoAreas.add(cg);
        
        area = null;
    }
    
    public void filtrar()
    {
        try
        {
            listaLazyConceptoGastos = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<ConceptoGasto> listaConceptoGastos = new ArrayList<>();
                    try
                    {
                        getConceptoGastoFiltro().setFirst(first);
                        getConceptoGastoFiltro().setPageSize(pageSize);
                        listaConceptoGastos = getConceptoGastoServicio().listarFiltro(getConceptoGastoFiltro());
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaConceptoGastos;
                }
            };
            listaLazyConceptoGastos.setRowCount(getConceptoGastoServicio().contarFiltro(getConceptoGastoFiltro()));
            Faces.getRequestContext().update("formListaConceptoGastos");
            Faces.getRequestContext().update("formListaConceptoGastos:dataTableConceptoGasto");
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarConceptoGasto()
    {
        try
        {
            //empresas = empresaDao.listarHQL("from Empresa e", null, null);
            areas = empresaDao.listarHQL("from Area a where a.estado=true", null, null);
            
            conceptoGastoAreas = new ArrayList<>();
            setEstadoConceptoGasto(true);
            setConceptoGastoAgregar(new ConceptoGasto());
            Faces.getRequestContext().update("divModalConceptoGasto");
            setContenidoModalConceptoGasto("modal-agregar-conceptoGasto.xhtml");
            Faces.getRequestContext().update("formConceptoGasto");
            Faces.getRequestContext().execute("show('#modalConceptoGasto');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void deleteArea(int index) {
        conceptoGastoAreas.remove(index);
        //Faces.getRequestContext().update("formListaConceptoGastos:dataTableConceptoGasto");
    }
    
    public void deleteAreaId(int index, Integer id) throws Exception{
        try {
            conceptoGastoAreaDao.eliminar(new ConceptoGastoArea(id));
            conceptoGastoAreas.remove(index);
        } catch(Exception e) {
            Faces.addMessage("No se puede eliminar, esta siendo usado este registro", "", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarConceptoGasto()
    {
        try
        {
            if (isEstadoConceptoGasto())
            {
                getConceptoGastoAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getConceptoGastoAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            Integer idGast = (Integer)getConceptoGastoServicio().guardar(getConceptoGastoAgregar());
            conceptoGastoAgregar.setId(idGast);
            conceptoGastoAreaDao.guardarColeccion(conceptoGastoAreas);
            
            Faces.getRequestContext().execute("hide('#modalConceptoGasto');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente al conceptoGasto.", FacesMessage.SEVERITY_INFO);
            iniciarListaConceptoGastos();
            Faces.getRequestContext().update("formListaConceptoGastos");
            Faces.getRequestContext().update("formListaConceptoGastos:dataTableConceptoGasto");
        } catch (ConstraintViolationException e)
        {
            Faces.addMessage("", "Existe un Concepto de Gasto registrado en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    /**
     * Cargar Datos del ConceptoGasto
     * <p>
     * @param conceptoGasto
     */
    public void mostrarModalModificarConceptoGasto(ConceptoGasto conceptoGasto)
    {
        try
        {
            //empresas = 
            areas = empresaDao.listarHQL("from Area e where e.estado=true", null, null);
            //conceptoGastoAreas = empresaDao.listarHQL("from ConceptoGastoArea c where c.idConceptoGasto.id=" + conceptoGasto.getId(), null, null);
            conceptoGastoAreas = empresaDao.listarHQL("from ConceptoGastoArea c where c.idConceptoGasto.id=" + conceptoGasto.getId(), null, null);
            setConceptoGastoModificar(conceptoGasto);
            if (getConceptoGastoModificar().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoConceptoGasto(true);
            } else
            {
                setEstadoConceptoGasto(false);
            }
            Faces.getRequestContext().update("divModalConceptoGasto");
            setContenidoModalConceptoGasto("modal-modificar-conceptoGasto.xhtml");
            Faces.getRequestContext().update("formConceptoGasto");
            Faces.getRequestContext().execute("show('#modalConceptoGasto');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarConceptoGasto()
    {
        try
        {
            if (isEstadoConceptoGasto())
            {
                getConceptoGastoModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getConceptoGastoModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getConceptoGastoServicio().actualizar(getConceptoGastoModificar());
            Faces.getRequestContext().execute("hide('#modalConceptoGasto');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente los datos del Concepto Gasto "
                    + getConceptoGastoModificar().getNombre(), FacesMessage.SEVERITY_INFO);
            iniciarListaConceptoGastos();
            Faces.getRequestContext().update("formListaConceptoGastos");
            Faces.getRequestContext().update("formListaConceptoGastos:dataTableConceptoGasto");
        } catch (DataIntegrityViolationException e)
        {
            Faces.addMessage("", "Existe un ConceptoGasto registrado en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarConceptoGasto(ConceptoGasto conceptoGasto)
    {
        setConceptoGastoModificar(conceptoGasto);
        Faces.getRequestContext().update("divModalConceptoGasto");
        setContenidoModalConceptoGasto("modal-inhabilitar-conceptoGasto.xhtml");
        Faces.getRequestContext().update("formConceptoGasto");
        Faces.getRequestContext().execute("show('#modalConceptoGasto');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarConceptoGasto()
    {
        try
        {
            getConceptoGastoModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getConceptoGastoServicio().actualizar(getConceptoGastoModificar());

            Faces.getRequestContext().execute("hide('#modalConceptoGasto');");
            Faces.addMessage("¡Atención!",
                    "El conceptoGasto ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaConceptoGastos();
            Faces.getRequestContext().update("formListaConceptoGastos");
            Faces.getRequestContext().update("formListaConceptoGastos:dataTableConceptoGasto");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarConceptoGasto(ConceptoGasto conceptoGasto)
    {
        setConceptoGastoModificar(conceptoGasto);
        Faces.getRequestContext().update("divModalConceptoGasto");
        setContenidoModalConceptoGasto("modal-habilitar-conceptoGasto.xhtml");
        Faces.getRequestContext().update("formConceptoGasto");
        Faces.getRequestContext().execute("show('#modalConceptoGasto');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarConceptoGasto()
    {
        try
        {
            getConceptoGastoModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getConceptoGastoServicio().actualizar(getConceptoGastoModificar());

            Faces.getRequestContext().execute("hide('#modalConceptoGasto');");
            Faces.addMessage("¡Atención!",
                    "El conceptoGasto ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaConceptoGastos();
            Faces.getRequestContext().update("formListaConceptoGastos");
            Faces.getRequestContext().update("formListaConceptoGastos:dataTableConceptoGasto");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarConceptoGasto(ConceptoGasto conceptoGasto)
    {
        setConceptoGastoModificar(conceptoGasto);
        Faces.getRequestContext().update("divModalConceptoGasto");
        setContenidoModalConceptoGasto("modal-eliminar-conceptoGasto.xhtml");
        Faces.getRequestContext().update("formConceptoGasto");
        Faces.getRequestContext().execute("show('#modalConceptoGasto');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarConceptoGasto()
    {
        try
        {
            getConceptoGastoServicio().eliminar(getConceptoGastoModificar());
            Faces.getRequestContext().execute("hide('#modalConceptoGasto');");
            Faces.addMessage("¡Atención!",
                    "El conceptoGasto ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaConceptoGastos();
            Faces.getRequestContext().update("formListaConceptoGastos");
            Faces.getRequestContext().update("formListaConceptoGastos:dataTableConceptoGasto");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public String getContenidoModalConceptoGasto()
    {
        return contenidoModalConceptoGasto;
    }

    public List<ConceptoGastoArea> getConceptoGastoAreas() {
        return conceptoGastoAreas;
    }

    public void setConceptoGastoAreas(List<ConceptoGastoArea> conceptoGastoAreas) {
        this.conceptoGastoAreas = conceptoGastoAreas;
    }

    public void setContenidoModalConceptoGasto(String contenidoModalConceptoGasto)
    {
        this.contenidoModalConceptoGasto = contenidoModalConceptoGasto;
    }

    public ConceptoGastoServicio getConceptoGastoServicio()
    {
        return conceptoGastoServicio;
    }

    public void setConceptoGastoServicio(ConceptoGastoServicio conceptoGastoServicio)
    {
        this.conceptoGastoServicio = conceptoGastoServicio;
    }

    public boolean isEstadoConceptoGasto()
    {
        return estadoConceptoGasto;
    }

    public void setEstadoConceptoGasto(boolean estadoConceptoGasto)
    {
        this.estadoConceptoGasto = estadoConceptoGasto;
    }

    public List<ConceptoGasto> getListaConceptoGastos()
    {
        return listaConceptoGastos;
    }

    public void setListaConceptoGastos(List<ConceptoGasto> listaConceptoGastos)
    {
        this.listaConceptoGastos = listaConceptoGastos;
    }

    public ConceptoGasto getConceptoGastoAgregar()
    {
        return conceptoGastoAgregar;
    }

    public void setConceptoGastoAgregar(ConceptoGasto conceptoGastoAgregar)
    {
        this.conceptoGastoAgregar = conceptoGastoAgregar;
    }

    public ConceptoGasto getConceptoGastoModificar()
    {
        return conceptoGastoModificar;
    }

    public void setConceptoGastoModificar(ConceptoGasto conceptoGastoModificar)
    {
        this.conceptoGastoModificar = conceptoGastoModificar;
    }

    public int getActivo()
    {
        return activo;
    }

    public void setActivo(int activo)
    {
        this.activo = activo;
    }

    public int getInactivo()
    {
        return inactivo;
    }

    public void setInactivo(int inactivo)
    {
        this.inactivo = inactivo;
    }

    public ConceptoGasto getConceptoGastoFiltro()
    {
        return conceptoGastoFiltro;
    }

    public void setConceptoGastoFiltro(ConceptoGasto conceptoGastoFiltro)
    {
        this.conceptoGastoFiltro = conceptoGastoFiltro;
    }

    public LazyDataModel<ConceptoGasto> getListaLazyConceptoGastos()
    {
        return listaLazyConceptoGastos;
    }

    public void setListaLazyConceptoGastos(LazyDataModel<ConceptoGasto> listaLazyConceptoGastos)
    {
        this.listaLazyConceptoGastos = listaLazyConceptoGastos;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    

    public EmpresaDao getEmpresaDao() {
        return empresaDao;
    }

    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public ConceptoGastoAreaDao getConceptoGastoAreaDao() {
        return conceptoGastoAreaDao;
    }

    public void setConceptoGastoAreaDao(ConceptoGastoAreaDao conceptoGastoAreaDao) {
        this.conceptoGastoAreaDao = conceptoGastoAreaDao;
    }

}
    // </editor-fold>
