package com.scorpio.sisconca.utilitario.converter;

import com.scorpio.sisconca.controlador.seguridad.primefaces.UsuarioServicioPrime;
import com.scorpio.sisconca.entidad.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.apache.log4j.Logger;

@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter
{

    private static final Logger LOG = Logger.getLogger(UsuarioConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value)
    {
        if (value != null && value.trim().length() > 0)
        {
            try
            {
                Usuario actividad = new Usuario();
                UsuarioServicioPrime service = (UsuarioServicioPrime) fc.getExternalContext()
                        .getApplicationMap().get("usuarioServicioPrime");
                for (Usuario item : service.getListaUsuario())
                {
                    if (item.getId() == Integer.parseInt(value))
                    {
                        actividad = item;
                    }
                }
                return actividad;
            } catch (Exception e)
            {
                LOG.error("error: " + e);
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else
        {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object)
    {
        if (object != null)
        {
            return String.valueOf(((Usuario) object).getId());
        } else
        {
            return null;
        }
    }

}
