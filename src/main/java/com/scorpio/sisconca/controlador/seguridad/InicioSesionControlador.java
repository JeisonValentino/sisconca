package com.scorpio.sisconca.controlador.seguridad;

import com.scorpio.sisconca.controlador.navegacion.NavegadorControlador;
import com.scorpio.sisconca.entidad.Auditoria;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.Entidad;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.seguridad.AuditoriaServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEntidadAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumTipoAuditoria;
import com.scorpio.sisconca.utilitario.enums.EnumTipoOperacionAuditoria;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class InicioSesionControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(InicioSesionControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;

    @ManagedProperty("#{navegadorControlador}")
    private NavegadorControlador navegadorControlador;
    
    @ManagedProperty("#{auditoriaServicio}")
    private AuditoriaServicio auditoriaServicio;
    
    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;

    public AuditoriaServicio getAuditoriaServicio() {
        return auditoriaServicio;
    }

    public void setAuditoriaServicio(AuditoriaServicio auditoriaServicio) {
        this.auditoriaServicio = auditoriaServicio;
    }

    public EmpleadoServicio getEmpleadoServicio() {
        return empleadoServicio;
    }

    public void setEmpleadoServicio(EmpleadoServicio empleadoServicio) {
        this.empleadoServicio = empleadoServicio;
    }
    

    private Usuario usuario;

    public void cerrarSesion()
    {
        try
        {
            try {

                /*Registro de auditoria al sistema*/
                {
                    Auditoria auditoria = new Auditoria();
                    auditoria.setTipo(EnumTipoAuditoria.ACCESO.getId());
                    Entidad entidad = new Entidad();
                    entidad.setId(EnumEntidadAuditoria.SISTEMAS.getId());
                    auditoria.setEntidadId(entidad);
                    auditoria.setTipoOperacion(EnumTipoOperacionAuditoria.SALIDA.getId());
                    Date fechaActual = new Date();
                    auditoria.setFechaOperacion(fechaActual);
                    Usuario usuario = usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
                    auditoria.setUsuarioId(usuario);
                    Empleado empleado = empleadoServicio.obtenerPorCodigo(usuario.getIdEmpleado().getId());
                    auditoria.setSedeId(empleado.getIdSede());
                    auditoriaServicio.guardar(auditoria);
                }

            } catch (Exception e) {
                LOG.error("error: " + e);
                Faces.addMessage("¡ERROR! en auditoria cierre de sesion", "No se pudo realizar la operación, póngase en contacto "
                        + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
            }
            
            LOG.info("El usuario : " + Faces.getUserInSession().getNombre() + " esta cerrando sesión.");
            Faces.removeSessionAttribute(Faces.USER);
            String path = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath() + "/login.xhtml";
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect(path);
            

            
        } catch (IOException e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarSesion()
    {
        try
        {
            if (null != getUsuarioServicio().ingresarSistema(getUsuario()))
            {
                setUsuario(getUsuarioServicio().obtenerPorUsuarioYContrasenia(getUsuario()));
                Faces.setSessionAttribute(Faces.USER, getUsuario());
                getNavegadorControlador().init();
                /*Registro de auditoria al sistema*/
                {
                    Auditoria auditoria = new Auditoria();
                    auditoria.setTipo(EnumTipoAuditoria.ACCESO.getId());
                    Entidad entidad = new Entidad();
                    entidad.setId(EnumEntidadAuditoria.SISTEMAS.getId());
                    auditoria.setEntidadId(entidad);
                    auditoria.setTipoOperacion(EnumTipoOperacionAuditoria.INGRESO.getId());
                    Date fechaActual = new Date();
                    auditoria.setFechaOperacion(fechaActual);
                    Usuario usuario=usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
                    auditoria.setUsuarioId(usuario);
                    Empleado empleado=empleadoServicio.obtenerPorCodigo(usuario.getIdEmpleado().getId());            
                    auditoria.setSedeId(empleado.getIdSede());
                    auditoriaServicio.guardar(auditoria);                    
                }
                LOG.info("El usuario : " + Faces.getUserInSession().getNombre() + " esta iniciando sesión.");
                Faces.redirect("index.xhtml");
            } else
            {
                Faces.addMessage("¡Atención!", "El usuario y/o contraseña son incorrectos.", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public Usuario getUsuario()
    {
        if (usuario == null)
        {
            usuario = new Usuario();
        }

        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public UsuarioServicio getUsuarioServicio()
    {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio)
    {
        this.usuarioServicio = usuarioServicio;
    }

    public NavegadorControlador getNavegadorControlador()
    {
        return navegadorControlador;
    }

    public void setNavegadorControlador(NavegadorControlador navegadorControlador)
    {
        this.navegadorControlador = navegadorControlador;
    }

}
    // </editor-fold>
