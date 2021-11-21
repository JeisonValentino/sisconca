/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.seguridad;

import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.Sede;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.SedeServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
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
public class SedeControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(SedeControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{sedeServicio}")
    private SedeServicio sedeServicio;
    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;

    public PerfilPorUsuarioServicio getPerfilPorUsuarioServicio() {
        return perfilPorUsuarioServicio;
    }

    public void setPerfilPorUsuarioServicio(PerfilPorUsuarioServicio perfilPorUsuarioServicio) {
        this.perfilPorUsuarioServicio = perfilPorUsuarioServicio;
    }

    private int activo;
    private int inactivo;
    private String contenidoModalSede = "modal-agregar-sede.xhtml";
    private List<Sede> listaSedes;
    private Sede sedeFiltro;

    private boolean estadoSede;

    // Agregar
    private Sede sedeAgregar;

    //Modificar
    private Sede sedeModificar;

    public void iniciar()
    {

        setSedeFiltro(new Sede());
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        //Faces.getRequestContext().update("menu");
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
        iniciarListaSedes();
    }
    
    public boolean isSoporte() throws Exception {
        List<PerfilPorUsuario> perfilPorUsuarios = perfilPorUsuarioServicio.getByUserId(Faces.getUserInSession().getId());
        return perfilPorUsuarios.stream().anyMatch((perfilPorUsuario) -> (perfilPorUsuario.getIdPerfil().getId() == EnumPerfil.SOPORTE.getId()));
    }

    public void iniciarListaSedes()
    {
        try
        {
            getListaSedes().clear();
            Sede sedeFiltroInicio = new Sede();
            sedeFiltroInicio.setNombre("");
            setListaSedes(getSedeServicio().listarPorNombreYEstado(sedeFiltroInicio));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaSedes:dataTableSede");

        } catch (Exception e)
        {
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarSede()
    {
        try
        {
            iniciarListaSedes();
            setSedeAgregar(new Sede());
            Faces.getRequestContext().update("divModalSede");
            setContenidoModalSede("modal-agregar-sede.xhtml");
            Faces.getRequestContext().update("formSede");
            Faces.getRequestContext().execute("show('#modalSede');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarSede()
    {
        try
        {
            if (isEstadoSede())
            {
                getSedeAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getSedeAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getSedeServicio().guardar(getSedeAgregar());
            Faces.getRequestContext().execute("hide('#modalSede');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente al sede.", FacesMessage.SEVERITY_INFO);
            iniciarListaSedes();
            Faces.getRequestContext().update("formListaSedes");
            Faces.getRequestContext().update("formListaSedes:dataTableSede");
        } catch (ConstraintViolationException e)
        {
            Faces.addMessage("", "Existe un Sede registrado con esa número de documento en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalModificarSede(Sede sede)
    {
        try
        {
            setSedeModificar(sede);
            if (getSedeModificar().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoSede(true);
            } else
            {
                setEstadoSede(false);
            }
            Faces.getRequestContext().update("divModalSede");
            setContenidoModalSede("modal-modificar-sede.xhtml");
            Faces.getRequestContext().update("formSede");
            Faces.getRequestContext().execute("show('#modalSede');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarSede()
    {
        try
        {
            if (isEstadoSede())
            {
                getSedeModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getSedeModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            LOG.error("info: " + getSedeModificar());
            getSedeServicio().actualizar(getSedeModificar());
            Faces.getRequestContext().execute("hide('#modalSede');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente los datos del empleado "
                    + getSedeModificar().getNombre(), FacesMessage.SEVERITY_INFO);
            iniciarListaSedes();
            Faces.getRequestContext().update("formListaSedes");
            Faces.getRequestContext().update("formListaSedes:dataTableSede");
        } catch (DataIntegrityViolationException e)
        {
            Faces.addMessage("", "Existe un Sede registrado con esa número de documento en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarSede(Sede sede)
    {
        try
        {
            setSedeModificar(sede);
            Faces.getRequestContext().update("divModalSede");
            setContenidoModalSede("modal-eliminar-sede.xhtml");
            Faces.getRequestContext().update("formSede");
            Faces.getRequestContext().execute("show('#modalSede');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarSede()
    {
        try
        {
            getSedeServicio().eliminar(getSedeModificar());
            Faces.getRequestContext().execute("hide('#modalSede');");
            Faces.addMessage("¡Atención!",
                    "La Sede ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaSedes();
            Faces.getRequestContext().update("formListaSedes");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarSede(Sede sede)
    {
        setSedeModificar(sede);
        Faces.getRequestContext().update("divModalSede");
        setContenidoModalSede("modal-habilitar-sede.xhtml");
        Faces.getRequestContext().update("formSede");
        Faces.getRequestContext().execute("show('#modalSede');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarSede()
    {
        try
        {
            getSedeModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getSedeServicio().actualizar(getSedeModificar());
            Faces.getRequestContext().execute("hide('#modalSede');");
            Faces.addMessage("¡Atención!",
                    "El usuario ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaSedes();
            Faces.getRequestContext().update("formListaSedees");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarSede(Sede sede)
    {
        setSedeModificar(sede);
        Faces.getRequestContext().update("divModalSede");
        setContenidoModalSede("modal-inhabilitar-sede.xhtml");
        Faces.getRequestContext().update("formSede");
        Faces.getRequestContext().execute("show('#modalSede');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarSede()
    {
        try
        {
            getSedeModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getSedeServicio().actualizar(getSedeModificar());

            Faces.getRequestContext().execute("hide('#modalSede');");
            Faces.addMessage("¡Atención!",
                    "El usuario ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaSedes();
            Faces.getRequestContext().update("formListaSedes");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public List<Sede> getListaSedes()
    {
        if (listaSedes == null)
        {
            listaSedes = new ArrayList<>();
        }
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes)
    {
        this.listaSedes = listaSedes;
    }

    public SedeServicio getSedeServicio()
    {
        return sedeServicio;
    }

    public void setSedeServicio(SedeServicio sedeServicio)
    {
        this.sedeServicio = sedeServicio;
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

    public Sede getSedeFiltro()
    {
        return sedeFiltro == null ? sedeFiltro = new Sede() : sedeFiltro;
    }

    public void setSedeFiltro(Sede sedeFiltro)
    {
        this.sedeFiltro = sedeFiltro;
    }

    public String getContenidoModalSede()
    {
        return contenidoModalSede;
    }

    public void setContenidoModalSede(String contenidoModalSede)
    {
        this.contenidoModalSede = contenidoModalSede;
    }

    public boolean isEstadoSede()
    {
        return estadoSede;
    }

    public void setEstadoSede(boolean estadoSede)
    {
        this.estadoSede = estadoSede;
    }

    public Sede getSedeAgregar()
    {
        return sedeAgregar;
    }

    public void setSedeAgregar(Sede sedeAgregar)
    {
        this.sedeAgregar = sedeAgregar;
    }

    public Sede getSedeModificar()
    {
        return sedeModificar;
    }

    public void setSedeModificar(Sede sedeModificar)
    {
        this.sedeModificar = sedeModificar;
    }

}
    // </editor-fold>
