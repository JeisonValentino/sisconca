package com.scorpio.sisconca.controlador.seguridad;

import com.scorpio.sisconca.entidad.Artefacto;
import com.scorpio.sisconca.servicio.seguridad.ArtefactoServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fsupo
 */
@ManagedBean
@SessionScoped
public class ArtefactoControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(ArtefactoControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{artefactoServicio}")
    private ArtefactoServicio artefactoServicio;

    private int activo;
    private int inactivo;
    private String contenidoModalArtefacto = "modal-agregar-artefacto.xhtml";
    private List<Artefacto> listaArtefactos;
    private Artefacto artefactoFiltro;

    private boolean estadoArtefacto;

    // Agregar
    private Artefacto artefactoAgregar;

    //Modificar
    private Artefacto artefactoModificar;

    public void iniciar()
    {

        setArtefactoFiltro(new Artefacto());
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        //Faces.getRequestContext().update("menu");
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
        iniciarListaArtefactos();
    }

    public void iniciarListaArtefactos()
    {
        try
        {
            getListaArtefactos().clear();
            Artefacto artefactoFiltroInicio = new Artefacto();
            artefactoFiltroInicio.setNombre("");
            setListaArtefactos(getArtefactoServicio().listarPorNombreYEstado(artefactoFiltroInicio));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaArtefactos:dataTableArtefacto");
        } catch (Exception e)
        {
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarArtefacto()
    {
        try
        {
            iniciarListaArtefactos();
            setArtefactoAgregar(new Artefacto());
            Faces.getRequestContext().update("divModalArtefacto");
            setContenidoModalArtefacto("modal-agregar-artefacto.xhtml");
            Faces.getRequestContext().update("formArtefacto");
            Faces.getRequestContext().execute("show('#modalArtefacto');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarArtefacto()
    {
        try
        {
            if (isEstadoArtefacto())
            {
                getArtefactoAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getArtefactoAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getArtefactoServicio().guardar(getArtefactoAgregar());
            Faces.getRequestContext().execute("hide('#modalArtefacto');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente al artefacto.", FacesMessage.SEVERITY_INFO);
            iniciarListaArtefactos();
            Faces.getRequestContext().update("formListaArtefactos");
            Faces.getRequestContext().update("formListaArtefactos:dataTableArtefacto");
        } catch (ConstraintViolationException e)
        {
            Faces.addMessage("", "Existe un Artefacto registrado con esa número de documento en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalModificarArtefacto(Artefacto artefacto)
    {
        try
        {
            setArtefactoModificar(artefacto);
            if (getArtefactoModificar().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoArtefacto(true);
            } else
            {
                setEstadoArtefacto(false);
            }
            Faces.getRequestContext().update("divModalArtefacto");
            setContenidoModalArtefacto("modal-modificar-artefacto.xhtml");
            Faces.getRequestContext().update("formArtefacto");
            Faces.getRequestContext().execute("show('#modalArtefacto');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarArtefacto()
    {
        try
        {
            if (isEstadoArtefacto())
            {
                getArtefactoModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getArtefactoModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getArtefactoServicio().actualizar(getArtefactoModificar());
            Faces.getRequestContext().execute("hide('#modalArtefacto');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente los datos del empleado "
                    + getArtefactoModificar().getNombre(), FacesMessage.SEVERITY_INFO);
            iniciarListaArtefactos();
            Faces.getRequestContext().update("formListaArtefactos");
            Faces.getRequestContext().update("formListaArtefactos:dataTableArtefacto");
        } catch (DataIntegrityViolationException e)
        {
            Faces.addMessage("", "Existe un Artefacto registrado con esa número de documento en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarArtefacto(Artefacto artefacto)
    {
        try
        {
            setArtefactoModificar(artefacto);
            Faces.getRequestContext().update("divModalArtefacto");
            setContenidoModalArtefacto("modal-eliminar-artefacto.xhtml");
            Faces.getRequestContext().update("formArtefacto");
            Faces.getRequestContext().execute("show('#modalArtefacto');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarArtefacto()
    {
        try
        {
            getArtefactoServicio().eliminar(getArtefactoModificar());
            Faces.getRequestContext().execute("hide('#modalArtefacto');");
            Faces.addMessage("¡Atención!",
                    "La Artefacto ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaArtefactos();
            Faces.getRequestContext().update("formListaArtefactos");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarArtefacto(Artefacto artefacto)
    {
        setArtefactoModificar(artefacto);
        Faces.getRequestContext().update("divModalArtefacto");
        setContenidoModalArtefacto("modal-habilitar-artefacto.xhtml");
        Faces.getRequestContext().update("formArtefacto");
        Faces.getRequestContext().execute("show('#modalArtefacto');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarArtefacto()
    {
        try
        {
            getArtefactoModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getArtefactoServicio().actualizar(getArtefactoModificar());
            Faces.getRequestContext().execute("hide('#modalArtefacto');");
            Faces.addMessage("¡Atención!",
                    "El usuario ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaArtefactos();
            Faces.getRequestContext().update("formListaArtefactoes");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarArtefacto(Artefacto artefacto)
    {
        setArtefactoModificar(artefacto);
        Faces.getRequestContext().update("divModalArtefacto");
        setContenidoModalArtefacto("modal-inhabilitar-artefacto.xhtml");
        Faces.getRequestContext().update("formArtefacto");
        Faces.getRequestContext().execute("show('#modalArtefacto');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarArtefacto()
    {
        try
        {
            getArtefactoModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getArtefactoServicio().actualizar(getArtefactoModificar());

            Faces.getRequestContext().execute("hide('#modalArtefacto');");
            Faces.addMessage("¡Atención!",
                    "El usuario ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaArtefactos();
            Faces.getRequestContext().update("formListaArtefactos");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public List<Artefacto> getListaArtefactos()
    {
        if (listaArtefactos == null)
        {
            listaArtefactos = new ArrayList<>();
        }
        return listaArtefactos;
    }

    public void setListaArtefactos(List<Artefacto> listaArtefactos)
    {
        this.listaArtefactos = listaArtefactos;
    }

    public ArtefactoServicio getArtefactoServicio()
    {
        return artefactoServicio;
    }

    public void setArtefactoServicio(ArtefactoServicio artefactoServicio)
    {
        this.artefactoServicio = artefactoServicio;
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

    public Artefacto getArtefactoFiltro()
    {
        return artefactoFiltro == null ? artefactoFiltro = new Artefacto() : artefactoFiltro;
    }

    public void setArtefactoFiltro(Artefacto artefactoFiltro)
    {
        this.artefactoFiltro = artefactoFiltro;
    }

    public String getContenidoModalArtefacto()
    {
        return contenidoModalArtefacto;
    }

    public void setContenidoModalArtefacto(String contenidoModalArtefacto)
    {
        this.contenidoModalArtefacto = contenidoModalArtefacto;
    }

    public boolean isEstadoArtefacto()
    {
        return estadoArtefacto;
    }

    public void setEstadoArtefacto(boolean estadoArtefacto)
    {
        this.estadoArtefacto = estadoArtefacto;
    }

    public Artefacto getArtefactoAgregar()
    {
        return artefactoAgregar;
    }

    public void setArtefactoAgregar(Artefacto artefactoAgregar)
    {
        this.artefactoAgregar = artefactoAgregar;
    }

    public Artefacto getArtefactoModificar()
    {
        return artefactoModificar;
    }

    public void setArtefactoModificar(Artefacto artefactoModificar)
    {
        this.artefactoModificar = artefactoModificar;
    }

}
    // </editor-fold>
