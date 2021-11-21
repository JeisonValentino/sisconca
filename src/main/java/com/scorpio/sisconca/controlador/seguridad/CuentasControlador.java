package com.scorpio.sisconca.controlador.seguridad;

import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.Perfil;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.Usuario;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
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
public class CuentasControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(CuentasControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;

    @ManagedProperty("#{perfilServicio}")
    private PerfilServicio perfilServicio;

    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;

    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;

    private List<Usuario> listaUsuarios;
    private LazyDataModel<Usuario> listaLazyUsuarios;

    // Agregar/Modificar Usuario
    private boolean estadoUsuario;

    // Agregar
    private PerfilPorUsuario perfilPorUsuarioAgregar;
    private List<Perfil> listaPerfilesSeleccionadosAgregar;
    private Empleado empleadoBuscado;
    private List<Empleado> listaEmpleados;

    //Modificar
    private PerfilPorUsuario perfilPorUsuarioModificar;
    private List<PerfilPorUsuario> listaPerfilPorUsuario;
    private List<Perfil> listaPerfilesSeleccionadosModificar;

    private List<Perfil> listaPerfiles;
    private String contenidoModalUsuario = "modal-agregar-usuario.xhtml";

    private int activo;
    private int inactivo;

    private Usuario usuarioModificar;

    // Filtro
    private Usuario usuarioFiltro;

    public void iniciar()
    {
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        iniciarListaUsuarios();
        getUsuarioFiltro().setNombre("");
        getUsuarioFiltro().getIdEmpleado().setNombre("");
        getUsuarioFiltro().getIdEstado().setId(EnumEstado.ACTIVO.getId());
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
    }
    
   public boolean isSoporte() throws Exception {
        List<PerfilPorUsuario> perfilPorUsuarios = perfilPorUsuarioServicio.getByUserId(Faces.getUserInSession().getId());
        System.out.println("perfilPorUsuarios " + perfilPorUsuarios);
        return perfilPorUsuarios.stream().anyMatch((perfilPorUsuario) -> (perfilPorUsuario.getIdPerfil().getId() == EnumPerfil.SOPORTE.getId()));
    }
	

    public void iniciarListaUsuarios()
    {
        try
        {
            Usuario usuarioFIltroInicio = new Usuario();
            usuarioFIltroInicio.getIdEstado().setId(EnumEstado.ACTIVO.getId());;
            usuarioFIltroInicio.setNombre("");
            usuarioFIltroInicio.getIdEmpleado().setNombre("");
            
            Usuario usuario=usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            Empleado empleado=empleadoServicio.obtenerPorCodigo((usuario).getIdEmpleado().getId());
            
            if(!isSoporte()){
                usuarioFIltroInicio.getIdEmpleado().setIdSede(empleado.getIdSede());
            }
            
            
            listaLazyUsuarios = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<Usuario> listaUsuarios = new ArrayList<>();
                    try
                    {
                        usuarioFIltroInicio.setFirst(first);
                        usuarioFIltroInicio.setPageSize(pageSize);
                        listaUsuarios = getUsuarioServicio().listarPorNombreYNombreEmpleadoyEstado(usuarioFIltroInicio);

                        for (Usuario u : listaUsuarios)
                        {
                            PerfilPorUsuario filtro = new PerfilPorUsuario();
                            filtro.setIdUsuario(u);
                            List<PerfilPorUsuario> ppu = perfilPorUsuarioServicio.obtenerTodosPorUsuario(filtro);
                            String perfiles = "";
                            if (null != ppu && ppu.size() > 0)
                            {
                                for (PerfilPorUsuario p : ppu)
                                {
                                    perfiles += p.getIdPerfil().getNombre() + ", ";
                                }
                                perfiles = perfiles.substring(0, perfiles.length() - 2);

                            }
                            u.setPerfil(perfiles);
                        }
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaUsuarios;
                }
            };
            listaLazyUsuarios.setRowCount(getUsuarioServicio().contarPorNombreYNombreEmpleadoyEstado(usuarioFIltroInicio));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaUsuarios:dataTableUsuarios");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaPerfiles()
    {
        try
        {
            getListaPerfiles().clear();
            setListaPerfiles(getPerfilServicio().obtenerTodo());
            Collections.sort(getListaPerfiles(), (a, b) -> a.compareTo(b));
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

    public void filtrar()
    {
        try
        {
            listaLazyUsuarios = new LazyDataModel()
            {
                @Override
                public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters)
                {
                    List<Usuario> listaUsuarios = new ArrayList<>();
                    try
                    {
                        getUsuarioFiltro().setFirst(first);
                        getUsuarioFiltro().setPageSize(pageSize);
                        /*Si el usuario de sesión es distinto a soporte filtro por sede*/
                        if(!isSoporte()){                            
                            Usuario usuario=usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
                            Empleado empleado=empleadoServicio.obtenerPorCodigo((usuario).getIdEmpleado().getId());
                            getUsuarioFiltro().getIdEmpleado().setIdSede(empleado.getIdSede());            
                        }
                        listaUsuarios = getUsuarioServicio().listarPorNombreYNombreEmpleadoyEstado(getUsuarioFiltro());
                        for (Usuario u : listaUsuarios)
                        {
                            PerfilPorUsuario filtro = new PerfilPorUsuario();
                            filtro.setIdUsuario(u);
                            List<PerfilPorUsuario> ppu = perfilPorUsuarioServicio.obtenerTodosPorUsuario(filtro);
                            String perfiles = "";
                            if (null != ppu && ppu.size() > 0)
                            {
                                for (PerfilPorUsuario p : ppu)
                                {
                                    perfiles += p.getIdPerfil().getNombre() + ", ";
                                }
                                perfiles = perfiles.substring(0, perfiles.length() - 2);

                            }
                            u.setPerfil(perfiles);
                        }
                        
                        
                    } catch (Exception e)
                    {
                        LOG.error("error: " + e);
                    }
                    return listaUsuarios;
                }
            };
            listaLazyUsuarios.setRowCount(getUsuarioServicio().contarPorNombreYNombreEmpleadoyEstado(usuarioFiltro));
            Faces.getRequestContext().update("formListaUsuarios:dataTableUsuarios");
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarUsuario()
    {
        try
        {
            setEmpleadoBuscado(new Empleado());
            iniciarListaPerfiles();
            setEstadoUsuario(true);
            setPerfilPorUsuarioAgregar(new PerfilPorUsuario());
            getListaPerfilesSeleccionadosAgregar().clear();
            iniciarListaEmpleados();
            Faces.getRequestContext().update("divModalUsuario");
            setContenidoModalUsuario("modal-agregar-usuario.xhtml");
            Faces.getRequestContext().update("formUsuario");
            Faces.getRequestContext().execute("show('#modalUsuario');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaEmpleados()
    {
        try
        {
            Usuario usuario=usuarioServicio.obtenerPorCodigo(Faces.getUserInSession().getId());
            Empleado empleado=empleadoServicio.obtenerPorCodigo(usuario.getIdEmpleado().getId());
            /*Si no es un usuario soporte filtrar empleado por sede*/
            if(!isSoporte()){
                setListaEmpleados(getEmpleadoServicio().listarSinUsuario(empleado));
            }else{
                setListaEmpleados(getEmpleadoServicio().listarSinUsuario());
            }
            
            
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public static String generateNameUser(String nombre, String apePaterno)
    {
        return (nombre.substring(0, 1) + "." + apePaterno).toLowerCase();
    }

    public void cambiarEmpleado()
    {
        try
        {
            if (null != getEmpleadoBuscado().getId())
            {
                setEmpleadoBuscado(getEmpleadoServicio().obtenerPorCodigo(getEmpleadoBuscado().getId()));
                getPerfilPorUsuarioAgregar().getIdUsuario()
                        .setNombre(generateNameUser(getEmpleadoBuscado().getNombre(), getEmpleadoBuscado().getApellidoPaterno()));
                getPerfilPorUsuarioAgregar().getIdUsuario()
                        .setContrasenia(getEmpleadoBuscado().getNumeroDocumento());
                Faces.getRequestContext().update("formUsuario");
            } else
            {
                Faces.addMessage("¡Atención!", "Debe seleccionar un empleado", FacesMessage.SEVERITY_FATAL);
            }
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarUsuario()
    {
        try
        {
            getPerfilPorUsuarioAgregar().getIdUsuario().setFechaRegistro(new Date());
            getPerfilPorUsuarioAgregar().getIdUsuario().setIdEmpleado(getEmpleadoBuscado());
            if (isEstadoUsuario())
            {
                getPerfilPorUsuarioAgregar().getIdUsuario().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getPerfilPorUsuarioAgregar().getIdUsuario().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }

            getUsuarioServicio().guardar(getPerfilPorUsuarioAgregar().getIdUsuario());

            for (Perfil item : getListaPerfilesSeleccionadosAgregar())
            {
                PerfilPorUsuario perfilPorUsuario = new PerfilPorUsuario();
                perfilPorUsuario.setIdPerfil(item);
                perfilPorUsuario.setIdUsuario(getPerfilPorUsuarioAgregar().getIdUsuario());
                getPerfilPorUsuarioServicio().guardar(perfilPorUsuario);
            }
            Faces.getRequestContext().execute("hide('#modalUsuario');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente al usuario.", FacesMessage.SEVERITY_INFO);
            iniciarListaUsuarios();
            Faces.getRequestContext().update("formListaUsuarios");
        } catch (ConstraintViolationException e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("", "El nombre de Usuario ya se encuentra"
                    + " registrado en el sistema.", FacesMessage.SEVERITY_WARN);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    /**
     * Cargar Datos del Usuario
     * <p>
     * @param usuario
     */
    public void mostrarModalModificarUsuario(Usuario usuario)
    {
        try
        {
            getListaPerfilesSeleccionadosModificar().clear();
            iniciarListaPerfiles();
            getPerfilPorUsuarioModificar().setIdUsuario(usuario);
            if (getPerfilPorUsuarioModificar().getIdUsuario().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoUsuario(true);
            } else
            {
                setEstadoUsuario(false);
            }
            for (PerfilPorUsuario item : getPerfilPorUsuarioServicio()
                    .obtenerTodosPorUsuario(getPerfilPorUsuarioModificar()))
            {
                getListaPerfilesSeleccionadosModificar().add(item.getIdPerfil());
            }

            Faces.getRequestContext().update("divModalUsuario");
            setContenidoModalUsuario("modal-modificar-usuario.xhtml");
            Faces.getRequestContext().update("formUsuario");
            Faces.getRequestContext().execute("show('#modalUsuario');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarUsuario()
    {
        try
        {
            getPerfilPorUsuarioServicio()
                    .ejecutarConsultaHQL("delete PerfilPorUsuario p where p.idUsuario.id="
                            + getPerfilPorUsuarioModificar().getIdUsuario().getId());

            for (Perfil item : getListaPerfilesSeleccionadosModificar())
            {
                PerfilPorUsuario perfilPorUsuario = new PerfilPorUsuario();
                perfilPorUsuario.setIdPerfil(item);
                perfilPorUsuario.setIdUsuario(getPerfilPorUsuarioModificar().getIdUsuario());
                getPerfilPorUsuarioServicio().guardar(perfilPorUsuario);
            }
            if (isEstadoUsuario())
            {
                getPerfilPorUsuarioModificar().getIdUsuario().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getPerfilPorUsuarioModificar().getIdUsuario().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getEmpleadoServicio().actualizar(getPerfilPorUsuarioModificar().getIdUsuario().getIdEmpleado());
            getUsuarioServicio().actualizar(getPerfilPorUsuarioModificar().getIdUsuario());

            Faces.getRequestContext().execute("hide('#modalUsuario');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente los datos del usuario "
                    + getPerfilPorUsuarioModificar().getIdUsuario().getNombre(), FacesMessage.SEVERITY_INFO);
            iniciarListaUsuarios();
            Faces.getRequestContext().update("formListaUsuarios");
        } catch (DataIntegrityViolationException e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("", "El nombre de Usuario ya se encuentra"
                    + " registrado en el sistema.", FacesMessage.SEVERITY_WARN);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarUsuario(Usuario usuario)
    {
        getPerfilPorUsuarioModificar().setIdUsuario(usuario);
        Faces.getRequestContext().update("divModalUsuario");
        setContenidoModalUsuario("modal-inhabilitar-usuario.xhtml");
        Faces.getRequestContext().update("formUsuario");
        Faces.getRequestContext().execute("show('#modalUsuario');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarUsuario()
    {
        try
        {
            getPerfilPorUsuarioModificar().getIdUsuario().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getUsuarioServicio().actualizar(getPerfilPorUsuarioModificar().getIdUsuario());

            Faces.getRequestContext().execute("hide('#modalUsuario');");
            Faces.addMessage("¡Atención!",
                    "El usuario ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaUsuarios();
            Faces.getRequestContext().update("formListaUsuarios");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarUsuario(Usuario usuario)
    {
        getPerfilPorUsuarioModificar().setIdUsuario(usuario);
        Faces.getRequestContext().update("divModalUsuario");
        setContenidoModalUsuario("modal-habilitar-usuario.xhtml");
        Faces.getRequestContext().update("formUsuario");
        Faces.getRequestContext().execute("show('#modalUsuario');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarUsuario()
    {
        try
        {
            getPerfilPorUsuarioModificar().getIdUsuario().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getUsuarioServicio().actualizar(getPerfilPorUsuarioModificar().getIdUsuario());

            Faces.getRequestContext().execute("hide('#modalUsuario');");
            Faces.addMessage("¡Atención!",
                    "El usuario ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaUsuarios();
            Faces.getRequestContext().update("formListaUsuarios");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarUsuario(Usuario usuario)
    {
        setUsuarioModificar(usuario);
        Faces.getRequestContext().update("divModalUsuario");
        setContenidoModalUsuario("modal-eliminar-usuario.xhtml");
        Faces.getRequestContext().update("formUsuario");
        Faces.getRequestContext().execute("show('#modalUsuario');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarUsuario()
    {
        try
        {
            getUsuarioServicio().eliminar(getUsuarioModificar());
            Faces.getRequestContext().execute("hide('#modalUsuario');");
            Faces.addMessage("¡Atención!",
                    "El usuario ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaUsuarios();
            Faces.getRequestContext().update("formListaUsuarios");
        } catch (DataIntegrityViolationException dv)
        {
            Faces.addMessage("", "No se ha podido eliminar al usuario debido a sus registros en Mes.", FacesMessage.SEVERITY_ERROR);
            Faces.getRequestContext().execute("hide('#modalUsuario');");
            dv.printStackTrace();
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public UsuarioServicio getUsuarioServicio()
    {
        return usuarioServicio;
    }

    public void setUsuarioServicio(UsuarioServicio usuarioServicio)
    {
        this.usuarioServicio = usuarioServicio;
    }

    public String getContenidoModalUsuario()
    {
        return contenidoModalUsuario;
    }

    public void setContenidoModalUsuario(String contenidoModalUsuario)
    {
        this.contenidoModalUsuario = contenidoModalUsuario;
    }

    public PerfilServicio getPerfilServicio()
    {
        return perfilServicio;
    }

    public void setPerfilServicio(PerfilServicio perfilServicio)
    {
        this.perfilServicio = perfilServicio;
    }

    public List<Perfil> getListaPerfiles()
    {
        if (listaPerfiles == null)
        {
            listaPerfiles = new ArrayList<>();
        }
        return listaPerfiles;
    }

    public void setListaPerfiles(List<Perfil> listaPerfiles)
    {
        this.listaPerfiles = listaPerfiles;
    }

    public List<Usuario> getListaUsuarios()
    {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios)
    {
        this.listaUsuarios = listaUsuarios;
    }

    public PerfilPorUsuarioServicio getPerfilPorUsuarioServicio()
    {
        return perfilPorUsuarioServicio;
    }

    public void setPerfilPorUsuarioServicio(PerfilPorUsuarioServicio perfilPorUsuarioServicio)
    {
        this.perfilPorUsuarioServicio = perfilPorUsuarioServicio;
    }

    public PerfilPorUsuario getPerfilPorUsuarioModificar()
    {
        if (perfilPorUsuarioModificar == null)
        {
            perfilPorUsuarioModificar = new PerfilPorUsuario();
        }
        return perfilPorUsuarioModificar;
    }

    public void setPerfilPorUsuarioModificar(PerfilPorUsuario perfilPorUsuarioModificar)
    {
        this.perfilPorUsuarioModificar = perfilPorUsuarioModificar;
    }

    public List<PerfilPorUsuario> getListaPerfilPorUsuario()
    {
        return listaPerfilPorUsuario;
    }

    public void setListaPerfilPorUsuario(List<PerfilPorUsuario> listaPerfilPorUsuario)
    {
        this.listaPerfilPorUsuario = listaPerfilPorUsuario;
    }

    public List<Perfil> getListaPerfilesSeleccionadosModificar()
    {
        if (listaPerfilesSeleccionadosModificar == null)
        {
            listaPerfilesSeleccionadosModificar = new ArrayList<>();
        }

        return listaPerfilesSeleccionadosModificar;
    }

    public void setListaPerfilesSeleccionadosModificar(List<Perfil> listaPerfilesSeleccionadosModificar)
    {
        this.listaPerfilesSeleccionadosModificar = listaPerfilesSeleccionadosModificar;
    }

    public EmpleadoServicio getEmpleadoServicio()
    {
        return empleadoServicio;
    }

    public void setEmpleadoServicio(EmpleadoServicio empleadoServicio)
    {
        this.empleadoServicio = empleadoServicio;
    }

    public boolean isEstadoUsuario()
    {
        return estadoUsuario;
    }

    public void setEstadoUsuario(boolean estadoUsuario)
    {
        this.estadoUsuario = estadoUsuario;
    }

    public PerfilPorUsuario getPerfilPorUsuarioAgregar()
    {
        return perfilPorUsuarioAgregar;
    }

    public void setPerfilPorUsuarioAgregar(PerfilPorUsuario perfilPorUsuarioAgregar)
    {
        this.perfilPorUsuarioAgregar = perfilPorUsuarioAgregar;
    }

    public List<Perfil> getListaPerfilesSeleccionadosAgregar()
    {
        if (listaPerfilesSeleccionadosAgregar == null)
        {
            listaPerfilesSeleccionadosAgregar = new ArrayList<>();
        }
        return listaPerfilesSeleccionadosAgregar;
    }

    public void setListaPerfilesSeleccionadosAgregar(List<Perfil> listaPerfilesSeleccionadosAgregar)
    {
        this.listaPerfilesSeleccionadosAgregar = listaPerfilesSeleccionadosAgregar;
    }

    public Empleado getEmpleadoBuscado()
    {
        if (empleadoBuscado == null)
        {
            empleadoBuscado = new Empleado();
        }
        return empleadoBuscado;
    }

    public void setEmpleadoBuscado(Empleado empleadoBuscado)
    {
        this.empleadoBuscado = empleadoBuscado;
    }

    public List<Empleado> getListaEmpleados()
    {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados)
    {
        this.listaEmpleados = listaEmpleados;
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

    public Usuario getUsuarioModificar()
    {
        return ((usuarioModificar == null) ? (usuarioModificar = new Usuario()) : usuarioModificar);
    }

    public void setUsuarioModificar(Usuario usuarioModificar)
    {
        this.usuarioModificar = usuarioModificar;
    }

    public Usuario getUsuarioFiltro()
    {
        return usuarioFiltro == null ? usuarioFiltro = new Usuario() : usuarioFiltro;
    }

    public void setUsuarioFiltro(Usuario usuarioFiltro)
    {
        this.usuarioFiltro = usuarioFiltro;
    }

    public LazyDataModel<Usuario> getListaLazyUsuarios()
    {
        return listaLazyUsuarios;
    }

    public void setListaLazyUsuarios(LazyDataModel<Usuario> listaLazyUsuarios)
    {
        this.listaLazyUsuarios = listaLazyUsuarios;
    }

}
    // </editor-fold>
