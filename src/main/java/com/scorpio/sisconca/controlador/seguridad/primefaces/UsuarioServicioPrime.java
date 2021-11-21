package com.scorpio.sisconca.controlador.seguridad.primefaces;

import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.apache.log4j.Logger;

/**
 *
 * @author smateo
 */
@ManagedBean(name = "usuarioServicioPrime", eager = true)
@ApplicationScoped
public class UsuarioServicioPrime
{

    private static final Logger LOG = Logger.getLogger(UsuarioServicioPrime.class.getName());

    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;

    private List<Usuario> listaUsuario;

    @PostConstruct
    public void iniciar()
    {
        try
        {
            listaUsuario = getUsuarioServicio().obtenerTodo();
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public UsuarioServicio getUsuarioServicio()
    {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio)
    {
        this.usuarioServicio = usuarioServicio;
    }

    public List<Usuario> getListaUsuario()
    {
        iniciar();
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario)
    {
        this.listaUsuario = listaUsuario;
    }

}
