package com.scorpio.sisconca.controlador.transaccion;

import com.scorpio.sisconca.entidad.Giro;
import com.scorpio.sisconca.servicio.transaccion.GiroServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@ManagedBean
@SessionScoped
public class GiroControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(GiroControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{giroServicio}")
    private GiroServicio giroServicio;

    private List<Giro> listaGiros;
    private LazyDataModel<Giro> listaLazyGiros;

    // Agregar/Modificar Giro
    private boolean estadoGiro;

    // Agregar
    private Giro giroAgregar;

    //Modificar
    private Giro giroModificar;

    private String contenidoModalGiros = "modal-agregar-giro.xhtml";

    private int activo;
    private int inactivo;

    // Filtro
    private Giro giroFiltro;

    public void iniciar()
    {
        setGiroFiltro(new Giro());
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        iniciarListaGiros();
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }

    public void iniciarListaGiros()
    {
        try
        {
            Giro giroFiltroInicio = new Giro();
            giroFiltroInicio.getIdEstado().setId(EnumEstado.ACTIVO.getId());
            giroFiltroInicio.setDescripcion("");
            listaLazyGiros = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<Giro> listaGiros = new ArrayList<>();
                    try
                    {
                        giroFiltroInicio.setFirst(first);
                        giroFiltroInicio.setPageSize(pageSize);
                        listaGiros = getGiroServicio().listarPorDescripcionyEstado(giroFiltroInicio);
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaGiros;
                }
            };
            listaLazyGiros.setRowCount(getGiroServicio().contarPorDescripcionyEstado(giroFiltroInicio));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaGiros:dataTableGiro");
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
            listaLazyGiros = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<Giro> listaGiros = new ArrayList<>();
                    try
                    {
                        getGiroFiltro().setFirst(first);
                        getGiroFiltro().setPageSize(pageSize);
                        listaGiros = getGiroServicio().listarPorDescripcionyEstado(getGiroFiltro());
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaGiros;
                }
            };
            listaLazyGiros.setRowCount(getGiroServicio().contarPorDescripcionyEstado(getGiroFiltro()));
            Faces.getRequestContext().update("formListaGiros");
            Faces.getRequestContext().update("formListaGiros:dataTableGiro");
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");

        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    /**
     * Metodo para personalizar el excel que exporta el dataTable de primefaces.
     * <p>
     * @param document
     */
    public void personalizarExcel(Object document)
    {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFPalette palette = wb.getCustomPalette();
        palette.setColorAtIndex(HSSFColor.RED.index,
                (byte) 238, //RGB red (0-255)
                (byte) 107, //RGB green
                (byte) 107 //RGB blue
        );

        HSSFFont my_font = wb.createFont();
        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.RED.index);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFont(my_font);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++)
        {
            HSSFCell cell = header.getCell(i);
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn((short) i);
        }
    }

    public void mostrarModalAgregarGiro()
    {
        try
        {
            setEstadoGiro(true);
            setGiroAgregar(new Giro());
            //getListaPerfilesSeleccionadosAgregar().clear();
            //iniciarListaEmpleados();
            Faces.getRequestContext().update("divModalGiro");
            setContenidoModalGiros("modal-agregar-giro.xhtml");
            Faces.getRequestContext().update("formGiro");
            Faces.getRequestContext().execute("show('#modalGiro');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarGiro()
    {
        try
        {
            if (isEstadoGiro())
            {
                getGiroAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getGiroAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getGiroServicio().guardar(getGiroAgregar());
            Faces.getRequestContext().execute("hide('#modalGiro');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente el Giro.", FacesMessage.SEVERITY_INFO);
            iniciarListaGiros();
            Faces.getRequestContext().update("formListaGiros");
            Faces.getRequestContext().update("formListaGiros:dataTableGiro");
        } catch (ConstraintViolationException e)
        {
            Faces.addMessage("", "Existe un Giro registrado en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalModificarGiro(Giro giro)
    {
        try
        {
            setGiroModificar(giro);
            if (getGiroModificar().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoGiro(true);
            } else
            {
                setEstadoGiro(false);
            }
            Faces.getRequestContext().update("divModalGiro");
            setContenidoModalGiros("modal-modificar-giro.xhtml");
            Faces.getRequestContext().update("formGiro");
            Faces.getRequestContext().execute("show('#modalGiro');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarGiro()
    {
        try
        {
            if (isEstadoGiro())
            {
                getGiroModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getGiroModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getGiroServicio().actualizar(getGiroModificar());
            Faces.getRequestContext().execute("hide('#modalGiro');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente los datos del Giro "
                    + getGiroModificar().getDescripcion(), FacesMessage.SEVERITY_INFO);
            iniciarListaGiros();
            Faces.getRequestContext().update("formListaGiros");
            Faces.getRequestContext().update("formListaGiros:dataTableGiro");
        } catch (DataIntegrityViolationException e)
        {
            Faces.addMessage("", "Existe un Giro registrado en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarGiro(Giro giro)
    {
        setGiroModificar(giro);
        Faces.getRequestContext().update("divModalGiro");
        setContenidoModalGiros("modal-inhabilitar-giro.xhtml");
        Faces.getRequestContext().update("formGiro");
        Faces.getRequestContext().execute("show('#modalGiro');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarGiro()
    {
        try
        {
            getGiroModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getGiroServicio().actualizar(getGiroModificar());

            Faces.getRequestContext().execute("hide('#modalGiro');");
            Faces.addMessage("¡Atención!",
                    "El giro ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaGiros();
            Faces.getRequestContext().update("formListaGiros");
            Faces.getRequestContext().update("formListaGiros:dataTableGiro");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarGiro(Giro giro)
    {
        setGiroModificar(giro);
        Faces.getRequestContext().update("divModalGiro");
        setContenidoModalGiros("modal-habilitar-giro.xhtml");
        Faces.getRequestContext().update("formGiro");
        Faces.getRequestContext().execute("show('#modalGiro');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarGiro()
    {
        try
        {
            getGiroModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getGiroServicio().actualizar(getGiroModificar());

            Faces.getRequestContext().execute("hide('#modalGiro');");
            Faces.addMessage("¡Atención!",
                    "El giro ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaGiros();
            Faces.getRequestContext().update("formListaGiros");
            Faces.getRequestContext().update("formListaGiros:dataTableGiro");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarGiro(Giro giro)
    {
        setGiroModificar(giro);
        Faces.getRequestContext().update("divModalGiro");
        setContenidoModalGiros("modal-eliminar-giro.xhtml");
        Faces.getRequestContext().update("formGiro");
        Faces.getRequestContext().execute("show('#modalGiro');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarGiro()
    {
        try
        {
            getGiroServicio().eliminar(getGiroModificar());
            Faces.getRequestContext().execute("hide('#modalGiro');");
            Faces.addMessage("¡Atención!",
                    "El giro ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaGiros();
            Faces.getRequestContext().update("formListaGiros");
            Faces.getRequestContext().update("formListaGiros:dataTableGiro");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public GiroServicio getGiroServicio()
    {
        return giroServicio;
    }

    public void setGiroServicio(GiroServicio giroServicio)
    {
        this.giroServicio = giroServicio;
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

    public List<Giro> getListaGiros()
    {
        return listaGiros;
    }

    public void setListaGiros(List<Giro> listaGiros)
    {
        this.listaGiros = listaGiros;
    }

    public LazyDataModel<Giro> getListaLazyGiros()
    {
        return listaLazyGiros;
    }

    public void setListaLazyGiros(LazyDataModel<Giro> listaLazyGiros)
    {
        this.listaLazyGiros = listaLazyGiros;
    }

    public boolean isEstadoGiro()
    {
        return estadoGiro;
    }

    public void setEstadoGiro(boolean estadoGiro)
    {
        this.estadoGiro = estadoGiro;
    }

    public String getContenidoModalGiros()
    {
        return contenidoModalGiros;
    }

    public void setContenidoModalGiros(String contenidoModalGiros)
    {
        this.contenidoModalGiros = contenidoModalGiros;
    }

    public Giro getGiroModificar()
    {
        return giroModificar;
    }

    public void setGiroModificar(Giro giroModificar)
    {
        this.giroModificar = giroModificar;
    }

    public Giro getGiroFiltro()
    {
        return giroFiltro;
    }

    public void setGiroFiltro(Giro giroFiltro)
    {
        this.giroFiltro = giroFiltro;
    }

    public Giro getGiroAgregar()
    {
        return giroAgregar;
    }

    public void setGiroAgregar(Giro giroAgregar)
    {
        this.giroAgregar = giroAgregar;
    }

}
    // </editor-fold>
