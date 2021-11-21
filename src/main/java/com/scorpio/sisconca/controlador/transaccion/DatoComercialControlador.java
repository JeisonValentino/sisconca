package com.scorpio.sisconca.controlador.transaccion;

import com.scorpio.sisconca.entidad.Cliente;
import com.scorpio.sisconca.entidad.DatoComercial;
import com.scorpio.sisconca.entidad.Giro;
import com.scorpio.sisconca.entidad.TipoVivienda;
import com.scorpio.sisconca.servicio.maestros.ClienteServicio;
import com.scorpio.sisconca.servicio.seguridad.TipoViviendaServicio;
import com.scorpio.sisconca.servicio.transaccion.DatoComercialServicio;
import com.scorpio.sisconca.servicio.transaccion.GiroServicio;
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
public class DatoComercialControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(DatoComercialControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{datoComercialServicio}")
    private DatoComercialServicio datoComercialServicio;

    @ManagedProperty("#{giroServicio}")
    private GiroServicio giroServicio;

    @ManagedProperty("#{tipoViviendaServicio}")
    private TipoViviendaServicio tipoViviendaServicio;

    @ManagedProperty("#{clienteServicio}")
    private ClienteServicio clienteServicio;

    private List<DatoComercial> listaDatoComerciales;
    private LazyDataModel<DatoComercial> listaLazyDatoComerciales;
    private List<Giro> listaGiro;
    private List<TipoVivienda> listaTipoVivienda;
    private List<Cliente> listaCliente;

    // Agregar/Modificar DatoComercial
    private boolean estadoDatoComercial;

    // Agregar
    private DatoComercial datoComercialAgregar;

    //Modificar
    private DatoComercial datoComercialModificar;
    private Giro giroModificar;

    private String contenidoModalDatoComerciales = "modal-agregar-datoComercial.xhtml";

    private int activo;
    private int inactivo;

    // Filtro
    private DatoComercial datoComercialFiltro;

    public void iniciar()
    {
        setDatoComercialFiltro(new DatoComercial());
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        iniciarListaDatoComerciales();
        iniciarListaCliente();
        iniciarListaGiro();
        iniciarListaTipoVivienda();
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }

    public void iniciarListaDatoComerciales()
    {
        try
        {
            DatoComercial datoComercialFiltroInicio = new DatoComercial();
            datoComercialFiltroInicio.getIdEstado().setId(EnumEstado.ACTIVO.getId());
            listaLazyDatoComerciales = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<DatoComercial> listaDatoComerciales = new ArrayList<>();
                    try
                    {
                        datoComercialFiltroInicio.setFirst(first);
                        datoComercialFiltroInicio.setPageSize(pageSize);
                        listaDatoComerciales = getDatoComercialServicio().listarPorDescripcionyEstado(datoComercialFiltroInicio);
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaDatoComerciales;
                }
            };
            listaLazyDatoComerciales.setRowCount(getDatoComercialServicio().contarPorDescripcionyEstado(datoComercialFiltroInicio));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaDatoComerciales:dataTableDatoComercial");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaGiro()
    {
        try
        {
            setListaGiro(getGiroServicio().obtenerTodo());
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaTipoVivienda()
    {
        try
        {
            setListaTipoVivienda(getTipoViviendaServicio().obtenerTodo());
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaCliente()
    {
        try
        {
            setListaCliente(getClienteServicio().obtenerTodo());
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

    public void mostrarModalAgregarDatoComercial()
    {
        try
        {
            setEstadoDatoComercial(true);
            setDatoComercialAgregar(new DatoComercial());
            getDatoComercialAgregar().setIdCliente(new Cliente());
            getDatoComercialAgregar().setIdGiro(new Giro());
            getDatoComercialAgregar().setIdTipoVivienda(new TipoVivienda());
            iniciarListaCliente();
            iniciarListaGiro();
            iniciarListaTipoVivienda();
            Faces.getRequestContext().update("divModalDatoComercial");
            setContenidoModalDatoComerciales("modal-agregar-datoComercial.xhtml");
            Faces.getRequestContext().update("formDatoComercial");
            Faces.getRequestContext().execute("show('#modalDatoComercial');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarDatoComercial()
    {
        try
        {
            if (isEstadoDatoComercial())
            {
                getDatoComercialAgregar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getDatoComercialAgregar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getDatoComercialServicio().guardar(getDatoComercialAgregar());
            Faces.getRequestContext().execute("hide('#modalDatoComercial');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente el DatoComercial.", FacesMessage.SEVERITY_INFO);
            iniciarListaDatoComerciales();
            iniciarListaCliente();
            iniciarListaGiro();
            iniciarListaTipoVivienda();
            Faces.getRequestContext().update("formListaDatoComerciales");
            Faces.getRequestContext().update("formListaDatoComerciales:dataTableDatoComercial");
        } catch (ConstraintViolationException e)
        {
            Faces.addMessage("", "Existe un DatoComercial registrado en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalModificarDatoComercial(DatoComercial datoComercial)
    {
        try
        {
            iniciarListaCliente();
            iniciarListaGiro();
            iniciarListaTipoVivienda();
            setDatoComercialModificar(datoComercial);
            if (getDatoComercialModificar().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoDatoComercial(true);
            } else
            {
                setEstadoDatoComercial(false);
            }
            Faces.getRequestContext().update("divModalDatoComercial");
            setContenidoModalDatoComerciales("modal-modificar-datoComercial.xhtml");
            Faces.getRequestContext().update("formDatoComercial");
            Faces.getRequestContext().execute("show('#modalDatoComercial');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarDatoComercial()
    {
        try
        {
            if (isEstadoDatoComercial())
            {
                getDatoComercialModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getDatoComercialModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getDatoComercialServicio().actualizar(getDatoComercialModificar());
            Faces.getRequestContext().execute("hide('#modalDatoComercial');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente los datos del DatoComercial "
                    + getDatoComercialModificar().getRuc(), FacesMessage.SEVERITY_INFO);
            iniciarListaDatoComerciales();
            Faces.getRequestContext().update("formListaDatoComerciales");
            Faces.getRequestContext().update("formListaDatoComerciales:dataTableDatoComercial");
        } catch (DataIntegrityViolationException e)
        {
            Faces.addMessage("", "Existe un DatoComercial registrado en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarDatoComercial(DatoComercial datoComercial)
    {
        setDatoComercialModificar(datoComercial);
        Faces.getRequestContext().update("divModalDatoComercial");
        setContenidoModalDatoComerciales("modal-inhabilitar-datoComercial.xhtml");
        Faces.getRequestContext().update("formDatoComercial");
        Faces.getRequestContext().execute("show('#modalDatoComercial');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarDatoComercial()
    {
        try
        {
            getDatoComercialModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getDatoComercialServicio().actualizar(getDatoComercialModificar());

            Faces.getRequestContext().execute("hide('#modalDatoComercial');");
            Faces.addMessage("¡Atención!",
                    "El datoComercial ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaDatoComerciales();
            Faces.getRequestContext().update("formListaDatoComerciales");
            Faces.getRequestContext().update("formListaDatoComerciales:dataTableDatoComercial");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarDatoComercial(DatoComercial datoComercial)
    {
        setDatoComercialModificar(datoComercial);
        Faces.getRequestContext().update("divModalDatoComercial");
        setContenidoModalDatoComerciales("modal-habilitar-datoComercial.xhtml");
        Faces.getRequestContext().update("formDatoComercial");
        Faces.getRequestContext().execute("show('#modalDatoComercial');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarDatoComercial()
    {
        try
        {
            getDatoComercialModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getDatoComercialServicio().actualizar(getDatoComercialModificar());

            Faces.getRequestContext().execute("hide('#modalDatoComercial');");
            Faces.addMessage("¡Atención!",
                    "El datoComercial ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaDatoComerciales();
            Faces.getRequestContext().update("formListaDatoComerciales");
            Faces.getRequestContext().update("formListaDatoComerciales:dataTableDatoComercial");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarDatoComercial(DatoComercial datoComercial)
    {
        setDatoComercialModificar(datoComercial);
        Faces.getRequestContext().update("divModalDatoComercial");
        setContenidoModalDatoComerciales("modal-eliminar-datoComercial.xhtml");
        Faces.getRequestContext().update("formDatoComercial");
        Faces.getRequestContext().execute("show('#modalDatoComercial');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarDatoComercial()
    {
        try
        {
            getDatoComercialServicio().eliminar(getDatoComercialModificar());
            Faces.getRequestContext().execute("hide('#modalDatoComercial');");
            Faces.addMessage("¡Atención!",
                    "El datoComercial ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaDatoComerciales();
            Faces.getRequestContext().update("formListaDatoComerciales");
            Faces.getRequestContext().update("formListaDatoComerciales:dataTableDatoComercial");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public DatoComercialServicio getDatoComercialServicio()
    {
        return datoComercialServicio;
    }

    public void setDatoComercialServicio(DatoComercialServicio datoComercialServicio)
    {
        this.datoComercialServicio = datoComercialServicio;
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

    public List<DatoComercial> getListaDatoComerciales()
    {
        return listaDatoComerciales;
    }

    public void setListaDatoComerciales(List<DatoComercial> listaDatoComerciales)
    {
        this.listaDatoComerciales = listaDatoComerciales;
    }

    public LazyDataModel<DatoComercial> getListaLazyDatoComerciales()
    {
        return listaLazyDatoComerciales;
    }

    public void setListaLazyDatoComerciales(LazyDataModel<DatoComercial> listaLazyDatoComerciales)
    {
        this.listaLazyDatoComerciales = listaLazyDatoComerciales;
    }

    public boolean isEstadoDatoComercial()
    {
        return estadoDatoComercial;
    }

    public void setEstadoDatoComercial(boolean estadoDatoComercial)
    {
        this.estadoDatoComercial = estadoDatoComercial;
    }

    public String getContenidoModalDatoComerciales()
    {
        return contenidoModalDatoComerciales;
    }

    public void setContenidoModalDatoComerciales(String contenidoModalDatoComerciales)
    {
        this.contenidoModalDatoComerciales = contenidoModalDatoComerciales;
    }

    public DatoComercial getDatoComercialModificar()
    {
        return datoComercialModificar;
    }

    public void setDatoComercialModificar(DatoComercial datoComercialModificar)
    {
        this.datoComercialModificar = datoComercialModificar;
    }

    public DatoComercial getDatoComercialFiltro()
    {
        return datoComercialFiltro;
    }

    public void setDatoComercialFiltro(DatoComercial datoComercialFiltro)
    {
        this.datoComercialFiltro = datoComercialFiltro;
    }

    public DatoComercial getDatoComercialAgregar()
    {
        return datoComercialAgregar;
    }

    public void setDatoComercialAgregar(DatoComercial datoComercialAgregar)
    {
        this.datoComercialAgregar = datoComercialAgregar;
    }

    public GiroServicio getGiroServicio()
    {
        return giroServicio;
    }

    public void setGiroServicio(GiroServicio giroServicio)
    {
        this.giroServicio = giroServicio;
    }

    public Giro getGiroModificar()
    {
        return giroModificar;
    }

    public void setGiroModificar(Giro giroModificar)
    {
        this.giroModificar = giroModificar;
    }

    public List<Giro> getListaGiro()
    {
        return listaGiro;
    }

    public void setListaGiro(List<Giro> listaGiro)
    {
        this.listaGiro = listaGiro;
    }

    public List<TipoVivienda> getListaTipoVivienda()
    {
        return listaTipoVivienda;
    }

    public void setListaTipoVivienda(List<TipoVivienda> listaTipoVivienda)
    {
        this.listaTipoVivienda = listaTipoVivienda;
    }

    public TipoViviendaServicio getTipoViviendaServicio()
    {
        return tipoViviendaServicio;
    }

    public void setTipoViviendaServicio(TipoViviendaServicio tipoViviendaServicio)
    {
        this.tipoViviendaServicio = tipoViviendaServicio;
    }

    public ClienteServicio getClienteServicio()
    {
        return clienteServicio;
    }

    public void setClienteServicio(ClienteServicio clienteServicio)
    {
        this.clienteServicio = clienteServicio;
    }

    public List<Cliente> getListaCliente()
    {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente)
    {
        this.listaCliente = listaCliente;
    }

}
    // </editor-fold>
