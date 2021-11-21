package com.scorpio.sisconca.utilitario.jsf;

import com.scorpio.sisconca.entidad.Usuario;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;

public class Faces
{
    /**
     * Attributo alternativo para mostrar nombre del usuario logeado
     */
    public static String USER = "userLoged";

    /**
     * Para ejecutar Java Script como tambien poder hacer update a algun
     * elemento de la vista PrimeFaces Util.
     *
     * @return
     */
    public static RequestContext getRequestContext()
    {
        return RequestContext.getCurrentInstance();
    }

    public static ServletContext getContext()
    {
        return (ServletContext) Faces.getExternalContext().getContext();
    }

    public static InputStream getResourceAsStream(String path)
    {
        return FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(path);
    }

    public static FacesContext getCurrentInstance()
    {
        return FacesContext.getCurrentInstance();
    }

    public static String getRealPath(String path)
    {
        return FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);
    }

    public static ExternalContext getExternalContext()
    {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static Object getSessionAttribute(String key)
    {
        return Faces.getRequest().getSession().getAttribute(key);
    }

    /**
     * Devuelve a la persona logueada. No hace falta solicitar al usuario ya que
     * este objeto ya tiene su Usuario registrado en la sesión.
     *
     * @return Persona
     */
//    public static Persona getPersonaInSession()
//    {
//        return (Persona) Faces.getRequest().getSession().getAttribute(PERSONA);
//    }
    /**
     * Devuelve al usuario ya casteado para facilitar
     *
     * @return
     */
    public static Usuario getUserInSession()
    {
        return (Usuario) Faces.getRequest().getSession().getAttribute(USER);
    }

    public static void setSessionAttribute(String key, Object value)
    {
        Faces.getRequest().getSession().setAttribute(key, value);
    }

    public static void removeSessionAttribute(String key)
    {
        Faces.getRequest().getSession().removeAttribute(key);
    }

    public static void invalidateSession()
    {
        Faces.getRequest().getSession().invalidate();
    }

    public static void logout() throws ServletException
    {
        Faces.getRequest().logout();
    }

    public static void redirect(String url) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    public static HttpServletRequest getRequest()
    {
        return (HttpServletRequest) Faces.getExternalContext().getRequest();
    }

    /**
     * Principalmente para redireccionar hacia una pagina con sentRedirect()
     *
     * @return
     */
    public static HttpServletResponse getResponse()
    {
        return ((HttpServletResponse) Faces.getExternalContext().getResponse());
    }

    /**
     * Con esto nos deslogeamos, el siguiente paso que deberia realizar el de
     * Redireccionar.
     *
     * @throws ServletException
     */
    public static void logOut() throws ServletException
    {
        //Faces.logout();
    }

    /**
     * Para Enviar mensajes a la vista
     *
     * @param summary      El contenido que tendra los mensajes
     * @param details      Los detalles que se mostraran despues de summary
     * @param tipo_MESSAGE El tipo de mensaje que mostrara, Alert, warning,
     *                     error, fatal
     */
    public static void addMessage(String summary, String details, FacesMessage.Severity tipo_MESSAGE)
    {
        FacesMessage message = new FacesMessage(tipo_MESSAGE, summary, details);
        FacesContext.getCurrentInstance().addMessage(null, message);
        //Faces.getContext().addMessage(null, message);
        Faces.getRequestContext().update("growl");
    }

    public static int encryptUser(String userName, String password)
    {
        return (userName + password).hashCode() / 2;
    }
}
