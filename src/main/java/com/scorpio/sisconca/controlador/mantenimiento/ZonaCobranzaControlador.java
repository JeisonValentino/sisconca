package com.scorpio.sisconca.controlador.mantenimiento;

import com.scorpio.sisconca.entidad.ZonaCobranza;
import com.scorpio.sisconca.servicio.mantenimiento.ZonaCobranzaServicio;
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
public class ZonaCobranzaControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(ZonaCobranzaControlador.class.getName());
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{zonaCobranzaServicio}")
    private ZonaCobranzaServicio zonaCobranzaServicio;

    private List<ZonaCobranza> listaZonaCobranzas;
    private LazyDataModel<ZonaCobranza> listaLazyZonaCobranzas;

    // Agregar/Modificar ZonaCobranza
    private boolean estadoZonaCobranza;

    // Agregar
    private ZonaCobranza zonaCobranzaAgregar;

    //Modificar
    private ZonaCobranza zonaCobranzaModificar;

    //Fltro
    private ZonaCobranza zonaCobranzaFiltro;

    private String contenidoModalZonaCobranza = "modal-agregar-zonaCobranza.xhtml";
    private int activo;
    private int inactivo;

    public void iniciar()
    {
        setZonaCobranzaFiltro(new ZonaCobranza());
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        iniciarListaZonaCobranzas();
        //Faces.getRequestContext().update("menu");
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }

    public void iniciarListaZonaCobranzas()
    {
        try
        {
            ZonaCobranza zonaCobranzaFiltroInicio = new ZonaCobranza();
            zonaCobranzaFiltroInicio.getIdEstado().setId(EnumEstado.ACTIVO.getId());
            zonaCobranzaFiltroInicio.setNombre("");
            listaLazyZonaCobranzas = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<ZonaCobranza> listaZonaCobranzas = new ArrayList<>();
                    try
                    {
                        zonaCobranzaFiltroInicio.setFirst(first);
                        zonaCobranzaFiltroInicio.setPageSize(pageSize);
                        listaZonaCobranzas = getZonaCobranzaServicio().listarFiltro(zonaCobranzaFiltroInicio);
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaZonaCobranzas;
                }
            };
            listaLazyZonaCobranzas.setRowCount(getZonaCobranzaServicio().contarFiltro(zonaCobranzaFiltroInicio));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaZonaCobranzas:dataTableZonaCobranza");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void filtrar()
    {
        try
        {
            listaLazyZonaCobranzas = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<ZonaCobranza> listaZonaCobranzas = new ArrayList<>();
                    try
                    {
                        getZonaCobranzaFiltro().setFirst(first);
                        getZonaCobranzaFiltro().setPageSize(pageSize);
                        listaZonaCobranzas = getZonaCobranzaServicio().listarFiltro(getZonaCobranzaFiltro());
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaZonaCobranzas;
                }
            };
            listaLazyZonaCobranzas.setRowCount(getZonaCobranzaServicio().contarFiltro(getZonaCobranzaFiltro()));
            Faces.getRequestContext().update("formListaZonaCobranzas");
            Faces.getRequestContext().update("formListaZonaCobranzas:dataTableZonaCobranza");
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarZonaCobranza()
    {
        try
        {
            setEstadoZonaCobranza(true);
            setZonaCobranzaAgregar(new ZonaCobranza());
            Faces.getRequestContext().update("divModalZonaCobranza");
            setContenidoModalZonaCobranza("modal-agregar-zonaCobranza.xhtml");
            Faces.getRequestContext().update("formZonaCobranza");
            Faces.getRequestContext().execute("show('#modalZonaCobranza');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarZonaCobranza()
    {
        try
        {
            if (isEstadoZonaCobranza())
            {
                getZonaCobranzaAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getZonaCobranzaAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getZonaCobranzaServicio().guardar(getZonaCobranzaAgregar());
            Faces.getRequestContext().execute("hide('#modalZonaCobranza');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente la Zona de Cobranza.", FacesMessage.SEVERITY_INFO);
            iniciarListaZonaCobranzas();
            Faces.getRequestContext().update("formListaZonaCobranzas");
            Faces.getRequestContext().update("formListaZonaCobranzas:dataTableZonaCobranza");
        } catch (ConstraintViolationException e)
        {
            Faces.addMessage("", "Existe una Zona de Cobranza registrado en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    /**
     * Cargar Datos del ZonaCobranza
     * <p>
     * @param zonaCobranza
     */
    public void mostrarModalModificarZonaCobranza(ZonaCobranza zonaCobranza)
    {
        try
        {
            setZonaCobranzaModificar(zonaCobranza);
            if (getZonaCobranzaModificar().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoZonaCobranza(true);
            } else
            {
                setEstadoZonaCobranza(false);
            }
            Faces.getRequestContext().update("divModalZonaCobranza");
            setContenidoModalZonaCobranza("modal-modificar-zonaCobranza.xhtml");
            Faces.getRequestContext().update("formZonaCobranza");
            Faces.getRequestContext().execute("show('#modalZonaCobranza');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarZonaCobranza()
    {
        try
        {
            if (isEstadoZonaCobranza())
            {
                getZonaCobranzaModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getZonaCobranzaModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getZonaCobranzaServicio().actualizar(getZonaCobranzaModificar());
            Faces.getRequestContext().execute("hide('#modalZonaCobranza');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente los datos del Concepto Gasto "
                    + getZonaCobranzaModificar().getNombre(), FacesMessage.SEVERITY_INFO);
            iniciarListaZonaCobranzas();
            Faces.getRequestContext().update("formListaZonaCobranzas");
            Faces.getRequestContext().update("formListaZonaCobranzas:dataTableZonaCobranza");
        } catch (DataIntegrityViolationException e)
        {
            Faces.addMessage("", "Existe un Zona de Cobranza registrado en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarZonaCobranza(ZonaCobranza zonaCobranza)
    {
        setZonaCobranzaModificar(zonaCobranza);
        Faces.getRequestContext().update("divModalZonaCobranza");
        setContenidoModalZonaCobranza("modal-inhabilitar-zonaCobranza.xhtml");
        Faces.getRequestContext().update("formZonaCobranza");
        Faces.getRequestContext().execute("show('#modalZonaCobranza');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarZonaCobranza()
    {
        try
        {
            getZonaCobranzaModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getZonaCobranzaServicio().actualizar(getZonaCobranzaModificar());

            Faces.getRequestContext().execute("hide('#modalZonaCobranza');");
            Faces.addMessage("¡Atención!",
                    "El Zona de Cobranza ha sido inhabilitada.", FacesMessage.SEVERITY_WARN);
            iniciarListaZonaCobranzas();
            Faces.getRequestContext().update("formListaZonaCobranzas");
            Faces.getRequestContext().update("formListaZonaCobranzas:dataTableZonaCobranza");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarZonaCobranza(ZonaCobranza zonaCobranza)
    {
        setZonaCobranzaModificar(zonaCobranza);
        Faces.getRequestContext().update("divModalZonaCobranza");
        setContenidoModalZonaCobranza("modal-habilitar-zonaCobranza.xhtml");
        Faces.getRequestContext().update("formZonaCobranza");
        Faces.getRequestContext().execute("show('#modalZonaCobranza');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarZonaCobranza()
    {
        try
        {
            getZonaCobranzaModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getZonaCobranzaServicio().actualizar(getZonaCobranzaModificar());

            Faces.getRequestContext().execute("hide('#modalZonaCobranza');");
            Faces.addMessage("¡Atención!",
                    "La Zona de Cobranza ha sido habilitada.", FacesMessage.SEVERITY_WARN);
            iniciarListaZonaCobranzas();
            Faces.getRequestContext().update("formListaZonaCobranzas");
            Faces.getRequestContext().update("formListaZonaCobranzas:dataTableZonaCobranza");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarZonaCobranza(ZonaCobranza zonaCobranza)
    {
        setZonaCobranzaModificar(zonaCobranza);
        Faces.getRequestContext().update("divModalZonaCobranza");
        setContenidoModalZonaCobranza("modal-eliminar-zonaCobranza.xhtml");
        Faces.getRequestContext().update("formZonaCobranza");
        Faces.getRequestContext().execute("show('#modalZonaCobranza');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarZonaCobranza()
    {
        try
        {
            getZonaCobranzaServicio().eliminar(getZonaCobranzaModificar());
            Faces.getRequestContext().execute("hide('#modalZonaCobranza');");
            Faces.addMessage("¡Atención!",
                    "El zonaCobranza ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaZonaCobranzas();
            Faces.getRequestContext().update("formListaZonaCobranzas");
            Faces.getRequestContext().update("formListaZonaCobranzas:dataTableZonaCobranza");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public String getContenidoModalZonaCobranza()
    {
        return contenidoModalZonaCobranza;
    }

    public void setContenidoModalZonaCobranza(String contenidoModalZonaCobranza)
    {
        this.contenidoModalZonaCobranza = contenidoModalZonaCobranza;
    }

    public ZonaCobranzaServicio getZonaCobranzaServicio()
    {
        return zonaCobranzaServicio;
    }

    public void setZonaCobranzaServicio(ZonaCobranzaServicio zonaCobranzaServicio)
    {
        this.zonaCobranzaServicio = zonaCobranzaServicio;
    }

    public boolean isEstadoZonaCobranza()
    {
        return estadoZonaCobranza;
    }

    public void setEstadoZonaCobranza(boolean estadoZonaCobranza)
    {
        this.estadoZonaCobranza = estadoZonaCobranza;
    }

    public List<ZonaCobranza> getListaZonaCobranzas()
    {
        return listaZonaCobranzas;
    }

    public void setListaZonaCobranzas(List<ZonaCobranza> listaZonaCobranzas)
    {
        this.listaZonaCobranzas = listaZonaCobranzas;
    }

    public ZonaCobranza getZonaCobranzaAgregar()
    {
        return zonaCobranzaAgregar;
    }

    public void setZonaCobranzaAgregar(ZonaCobranza zonaCobranzaAgregar)
    {
        this.zonaCobranzaAgregar = zonaCobranzaAgregar;
    }

    public ZonaCobranza getZonaCobranzaModificar()
    {
        return zonaCobranzaModificar;
    }

    public void setZonaCobranzaModificar(ZonaCobranza zonaCobranzaModificar)
    {
        this.zonaCobranzaModificar = zonaCobranzaModificar;
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

    public ZonaCobranza getZonaCobranzaFiltro()
    {
        return zonaCobranzaFiltro;
    }

    public void setZonaCobranzaFiltro(ZonaCobranza zonaCobranzaFiltro)
    {
        this.zonaCobranzaFiltro = zonaCobranzaFiltro;
    }

    public LazyDataModel<ZonaCobranza> getListaLazyZonaCobranzas()
    {
        return listaLazyZonaCobranzas;
    }

    public void setListaLazyZonaCobranzas(LazyDataModel<ZonaCobranza> listaLazyZonaCobranzas)
    {
        this.listaLazyZonaCobranzas = listaLazyZonaCobranzas;
    }

}
    // </editor-fold>
