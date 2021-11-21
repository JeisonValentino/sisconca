package com.scorpio.sisconca.controlador.transaccion;

import com.scorpio.sisconca.entidad.TipoPrestamo;
import com.scorpio.sisconca.servicio.transaccion.TipoPrestamoServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fsupo
 */
@ManagedBean
@SessionScoped
public class TipoPrestamoControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(TipoPrestamoControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{tipoPrestamoServicio}")
    private TipoPrestamoServicio tipoPrestamoServicio;

    private int activo;
    private int inactivo;
    private String contenidoModalTipoPrestamo = "modal-agregar-tipoPrestamo.xhtml";
    private List<TipoPrestamo> listaTipoPrestamos;
    private LazyDataModel<TipoPrestamo> listaLazyTipoPrestamos;

    private TipoPrestamo tipoPrestamoFiltro;

    private boolean estadoTipoPrestamo;

    // TODO: se debería utilizar la misma variable para ambos casos...
    private TipoPrestamo tipoPrestamoAgregar;

    //Modificar
    private TipoPrestamo tipoPrestamoModificar;

    public void iniciar()
    {
        setTipoPrestamoFiltro(new TipoPrestamo());
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
        iniciarListaTipoPrestamos();
    }

    public void iniciarListaTipoPrestamos()
    {
        try
        {
            getListaTipoPrestamos().clear();
            TipoPrestamo tipoPrestamoFiltroInicio = new TipoPrestamo();
            tipoPrestamoFiltroInicio.setNombre("");
            setListaTipoPrestamos(getTipoPrestamoServicio().listarPorNombreYEstado(tipoPrestamoFiltroInicio));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaTipoPrestamos:dataTableTipoPrestamo");

        }
        catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistemaa", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void filtrar()
    {
        System.out.println("filtering");
        try
        {
            setListaTipoPrestamos(getTipoPrestamoServicio().listarPorNombreYEstado(getTipoPrestamoFiltro()));
        }
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(TipoPrestamoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            listaLazyTipoPrestamos = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<TipoPrestamo> listaTipoPrestamos = new ArrayList<>();
                    try
                    {
                        getTipoPrestamoFiltro().setFirst(first);
                        getTipoPrestamoFiltro().setPageSize(pageSize);
                        listaTipoPrestamos = getTipoPrestamoServicio().listarPorNombreYEstado(getTipoPrestamoFiltro());
                    }
                    catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaTipoPrestamos;
                }
            };
            listaLazyTipoPrestamos.setRowCount(getTipoPrestamoServicio().contarFiltro(getTipoPrestamoFiltro()));
            Faces.getRequestContext().update("formListaTipoPrestamos");
            Faces.getRequestContext().update("formListaTipoPrestamos:dataTableTipoPrestamo");
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
        }
        catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarTipoPrestamo()
    {
        try
        {
            // TODO: para qué inicias las listas si vas a agregar UN ELEMENTO?
            iniciarListaTipoPrestamos();
            estadoTipoPrestamo = true;
            setTipoPrestamoAgregar(new TipoPrestamo());
            Faces.getRequestContext().update("divModalTipoPrestamo");
            setContenidoModalTipoPrestamo("modal-agregar-tipoPrestamo.xhtml");
            Faces.getRequestContext().update("formTipoPrestamo");
            Faces.getRequestContext().execute("show('#modalTipoPrestamo');");
        }
        catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarTipoPrestamo()
    {
        try
        {
            if (isEstadoTipoPrestamo())
            {
                getTipoPrestamoAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            }
            else
            {
                getTipoPrestamoAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getTipoPrestamoServicio().guardar(getTipoPrestamoAgregar());
            Faces.getRequestContext().execute("hide('#modalTipoPrestamo');");
            Faces.addMessage("¡Atención!",
                    "Se agregó correctamente al tipo de préstamo.", FacesMessage.SEVERITY_INFO);
            iniciarListaTipoPrestamos();
            Faces.getRequestContext().update("formListaTipoPrestamos");
            Faces.getRequestContext().update("formListaTipoPrestamos:dataTableTipoPrestamo");
        }
        catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalModificarTipoPrestamo(TipoPrestamo tipoPrestamo)
    {
        try
        {
            setTipoPrestamoModificar(tipoPrestamo);
            if (getTipoPrestamoModificar().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoTipoPrestamo(true);
            }
            else
            {
                setEstadoTipoPrestamo(false);
            }
            Faces.getRequestContext().update("divModalTipoPrestamo");
            setContenidoModalTipoPrestamo("modal-modificar-tipoPrestamo.xhtml");
            Faces.getRequestContext().update("formTipoPrestamo");
            Faces.getRequestContext().execute("show('#modalTipoPrestamo');");
        }
        catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarTipoPrestamo()
    {
        try
        {
            if (isEstadoTipoPrestamo())
            {
                getTipoPrestamoModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            }
            else
            {
                getTipoPrestamoModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getTipoPrestamoServicio().actualizar(getTipoPrestamoModificar());
            Faces.getRequestContext().execute("hide('#modalTipoPrestamo');");
            Faces.addMessage("¡Atención!",
                    "Se actualizó correctamente el tipo de préstamo"
                    + getTipoPrestamoModificar().getNombre(), FacesMessage.SEVERITY_INFO);
            iniciarListaTipoPrestamos();
            Faces.getRequestContext().update("formListaTipoPrestamos");
            Faces.getRequestContext().update("formListaTipoPrestamos:dataTableTipoPrestamo");
        }
        catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarTipoPrestamo(TipoPrestamo tipoPrestamo)
    {
        try
        {
            setTipoPrestamoModificar(tipoPrestamo);
            Faces.getRequestContext().update("divModalTipoPrestamo");
            setContenidoModalTipoPrestamo("modal-eliminar-tipoPrestamo.xhtml");
            Faces.getRequestContext().update("formTipoPrestamo");
            Faces.getRequestContext().execute("show('#modalTipoPrestamo');");
        }
        catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarTipoPrestamo()
    {
        try
        {
            getTipoPrestamoServicio().eliminar(getTipoPrestamoModificar());
            Faces.getRequestContext().execute("hide('#modalTipoPrestamo');");
            Faces.addMessage("¡Atención!", "El tpo de Préstamo ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaTipoPrestamos();
            Faces.getRequestContext().update("formListaTipoPrestamos");
        }
        catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarTipoPrestamo(TipoPrestamo tipoPrestamo)
    {
        setTipoPrestamoModificar(tipoPrestamo);
        Faces.getRequestContext().update("divModalTipoPrestamo");
        setContenidoModalTipoPrestamo("modal-habilitar-tipoPrestamo.xhtml");
        Faces.getRequestContext().update("formTipoPrestamo");
        Faces.getRequestContext().execute("show('#modalTipoPrestamo');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarTipoPrestamo()
    {
        try
        {
            getTipoPrestamoModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getTipoPrestamoServicio().actualizar(getTipoPrestamoModificar());
            Faces.getRequestContext().execute("hide('#modalTipoPrestamo');");
            Faces.addMessage("¡Atención!", "El tipo de préstamo ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaTipoPrestamos();
            Faces.getRequestContext().update("formListaTipoPrestamoes");
        }
        catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarTipoPrestamo(TipoPrestamo tipoPrestamo)
    {
        setTipoPrestamoModificar(tipoPrestamo);
        Faces.getRequestContext().update("divModalTipoPrestamo");
        setContenidoModalTipoPrestamo("modal-inhabilitar-tipoPrestamo.xhtml");
        Faces.getRequestContext().update("formTipoPrestamo");
        Faces.getRequestContext().execute("show('#modalTipoPrestamo');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarTipoPrestamo()
    {
        try
        {
            getTipoPrestamoModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getTipoPrestamoServicio().actualizar(getTipoPrestamoModificar());

            Faces.getRequestContext().execute("hide('#modalTipoPrestamo');");
            Faces.addMessage("¡Atención!", "El tipo de préstamo ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaTipoPrestamos();
            Faces.getRequestContext().update("formListaTipoPrestamos");
        }
        catch (Exception e)
        {
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }
    
    public void validateName(){
        
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public List<TipoPrestamo> getListaTipoPrestamos()
    {
        if (listaTipoPrestamos == null)
        {
            listaTipoPrestamos = new ArrayList<>();
        }
        return listaTipoPrestamos;
    }

    public void setListaTipoPrestamos(List<TipoPrestamo> listaTipoPrestamos)
    {
        this.listaTipoPrestamos = listaTipoPrestamos;
    }

    public TipoPrestamoServicio getTipoPrestamoServicio()
    {
        return tipoPrestamoServicio;
    }

    public void setTipoPrestamoServicio(TipoPrestamoServicio tipoPrestamoServicio)
    {
        this.tipoPrestamoServicio = tipoPrestamoServicio;
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

    public TipoPrestamo getTipoPrestamoFiltro()
    {
        return tipoPrestamoFiltro == null ? tipoPrestamoFiltro = new TipoPrestamo() : tipoPrestamoFiltro;
    }

    public void setTipoPrestamoFiltro(TipoPrestamo tipoPrestamoFiltro)
    {
        this.tipoPrestamoFiltro = tipoPrestamoFiltro;
    }

    public String getContenidoModalTipoPrestamo()
    {
        return contenidoModalTipoPrestamo;
    }

    public void setContenidoModalTipoPrestamo(String contenidoModalTipoPrestamo)
    {
        this.contenidoModalTipoPrestamo = contenidoModalTipoPrestamo;
    }

    public boolean isEstadoTipoPrestamo()
    {
        return estadoTipoPrestamo;
    }

    public void setEstadoTipoPrestamo(boolean estadoTipoPrestamo)
    {
        this.estadoTipoPrestamo = estadoTipoPrestamo;
    }

    public TipoPrestamo getTipoPrestamoAgregar()
    {
        return tipoPrestamoAgregar;
    }

    public void setTipoPrestamoAgregar(TipoPrestamo tipoPrestamoAgregar)
    {
        this.tipoPrestamoAgregar = tipoPrestamoAgregar;
    }

    public TipoPrestamo getTipoPrestamoModificar()
    {
        return tipoPrestamoModificar;
    }

    public void setTipoPrestamoModificar(TipoPrestamo tipoPrestamoModificar)
    {
        this.tipoPrestamoModificar = tipoPrestamoModificar;
    }

}
    // </editor-fold>
