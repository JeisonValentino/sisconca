package com.scorpio.sisconca.controlador.seguridad;

import com.scorpio.sisconca.entidad.Accion;
import com.scorpio.sisconca.entidad.Perfil;
import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.Permiso;
import com.scorpio.sisconca.entidad.PermisoPorPerfil;
import com.scorpio.sisconca.servicio.seguridad.AccionServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilServicio;
import com.scorpio.sisconca.servicio.seguridad.PermisoPorPerfilServicio;
import com.scorpio.sisconca.utilitario.Constantes;
import com.scorpio.sisconca.utilitario.enums.EnumAccion;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import com.scorpio.sisconca.utilitario.enums.EnumPermiso;
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

@ManagedBean
@SessionScoped
public class PerfilesControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(PerfilesControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{perfilServicio}")
    private PerfilServicio perfilServicio;

    @ManagedProperty("#{permisoPorPerfilServicio}")
    private PermisoPorPerfilServicio permisoPorPerfilServicio;

    @ManagedProperty("#{accionServicio}")
    private AccionServicio accionServicio;
    
    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;

    public PerfilPorUsuarioServicio getPerfilPorUsuarioServicio() {
        return perfilPorUsuarioServicio;
    }

    public void setPerfilPorUsuarioServicio(PerfilPorUsuarioServicio perfilPorUsuarioServicio) {
        this.perfilPorUsuarioServicio = perfilPorUsuarioServicio;
    }

    // Agregar/Modificar Perfil
    private boolean estadoPerfil;

    // Agregar
    private PermisoPorPerfil permisoPorPerfilAgregar;

    //Modificar
    private PermisoPorPerfil permisoPorPerfilModificar;
    private Perfil perfilModificar;
    private boolean mes;
    private boolean periodo;
    private boolean infodaf;
    private boolean presupuesto;
    private boolean cliente;
    private boolean sitio;
    private boolean empleado;
    private boolean actividad;
    private boolean segmento;
    private boolean empresa;
    private boolean usuario;
    private boolean perfil;
    private boolean reporte;
    private List<PermisoPorPerfil> listaPermisoPorPerfil;
    private List<Accion> listaAccionMesModificarSeleccionada;
    private List<Accion> listaAccionPeriodoModificarSeleccionada;
    private List<Accion> listaAccionInfodafModificarSeleccionada;
    private List<Accion> listaAccionPresupuestoModificarSeleccionada;
    private List<Accion> listaAccionClienteModificarSeleccionada;
    private List<Accion> listaAccionSitioModificarSeleccionada;
    private List<Accion> listaAccionEmpleadoModificarSeleccionada;
    private List<Accion> listaAccionActividadModificarSeleccionada;
    private List<Accion> listaAccionSegmentoModificarSeleccionada;
    private List<Accion> listaAccionEmpresaModificarSeleccionada;
    private List<Accion> listaAccionUsuarioModificarSeleccionada;
    private List<Accion> listaAccionPerfilModificarSeleccionada;
    private List<Accion> listaAccionReporteModificarSeleccionada;

    private List<Accion> listaAccion;
    private List<Accion> listaAccionSinAuditar;
    private List<Accion> listaAccionReporteINFODAF;
    private List<Accion> listaAccionReporte;
    private List<Accion> listaAccionMes;

    private List<Perfil> listaPerfiles;
    private String contenidoModalPerfil = "modal-agregar-perfil.xhtml";
    private int activo;
    private int inactivo;

    // Filtrar
    private Perfil perfil1Filtro;

    public void iniciar()
    {
        setPerfil1Filtro(new Perfil());
        setActivo(EnumEstado.ACTIVO.getId());
        setInactivo(EnumEstado.INACTIVO.getId());
        //Faces.getRequestContext().update("menu");
        Faces.getRequestContext().update("contenido");
        Faces.getRequestContext().update("divMenuDerecha");
        iniciarListaPerfiles();
    }
    
    public boolean isSoporte() throws Exception {
        List<PerfilPorUsuario> perfilPorUsuarios = perfilPorUsuarioServicio.getByUserId(Faces.getUserInSession().getId());
        return perfilPorUsuarios.stream().anyMatch((perfilPorUsuario) -> (perfilPorUsuario.getIdPerfil().getId() == EnumPerfil.SOPORTE.getId()));
    }

    public void iniciarListaPerfiles()
    {
        try
        {
            getListaPerfiles().clear();
            Perfil perfilFiltroInicio = new Perfil();
            perfilFiltroInicio.setNombre("");
            perfilFiltroInicio.getIdEstado().setId(EnumEstado.ACTIVO.getId());
            setListaPerfiles(getPerfilServicio()
                    .listarPorNombreYEstado(perfilFiltroInicio));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaPerfiles:dataTablePerfil");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void iniciarListaAcciones()
    {
        try
        {
            getListaAccion().clear();
            setListaAccionMes(getAccionServicio()
                    .listarHQL("from Accion a where a.id not in("
                            + EnumAccion.HABILITAR.getId()
                            + ")", null, null));
            setListaAccion(getAccionServicio()
                    .listarHQL("from Accion a where a.id not in("
                            + EnumAccion.HABILITAR.getId() + ","
                            + EnumAccion.CIERRE.getId()
                            + ")", null, null));
            setListaAccionSinAuditar(getAccionServicio()
                    .listarHQL("from Accion a where a.id not in("
                            + EnumAccion.AUDITAR.getId()
                            + "," + EnumAccion.HABILITAR.getId()
                            + "," + EnumAccion.CIERRE.getId()
                            + ")", null, null));
            setListaAccionReporte(getAccionServicio()
                    .listarHQL("from Accion a where a.id in("
                            + EnumAccion.LISTAR.getId()
                            + ")", null, null));
            setListaAccionReporteINFODAF(getAccionServicio()
                    .listarHQL("from Accion a where a.id in("
                            + EnumAccion.LISTAR.getId() + ","
                            + EnumAccion.ELIMINAR.getId() + ","
                            + EnumAccion.INHABILITAR.getId() + ""
                            + ")", null, null));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
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
            getListaPerfiles().clear();
            setListaPerfiles(getPerfilServicio().listarPorNombreYEstado(getPerfil1Filtro()));
            Faces.getRequestContext().execute("limpiarCSSTablaPrimeFaces();");
            Faces.getRequestContext().update("formListaPerfiles");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalAgregarPerfil()
    {
        try
        {
            setPermisoPorPerfilAgregar(new PermisoPorPerfil());
            setMes(false);
            setPeriodo(false);
            setInfodaf(false);
            setPresupuesto(false);
            setCliente(false);
            setSitio(false);
            setEmpleado(false);
            setActividad(false);
            setSegmento(false);
            setEmpresa(false);
            setUsuario(false);
            setPerfil(false);
            setReporte(false);
            setEstadoPerfil(true);
            // <editor-fold defaultstate="collapsed" desc="Limpiar listas de checkBox">
            getListaAccionMesModificarSeleccionada().clear();
            getListaAccionPeriodoModificarSeleccionada().clear();
            getListaAccionInfodafModificarSeleccionada().clear();
            getListaAccionPresupuestoModificarSeleccionada().clear();
            getListaAccionClienteModificarSeleccionada().clear();
            getListaAccionSitioModificarSeleccionada().clear();
            getListaAccionEmpleadoModificarSeleccionada().clear();
            getListaAccionActividadModificarSeleccionada().clear();
            getListaAccionSegmentoModificarSeleccionada().clear();
            getListaAccionEmpresaModificarSeleccionada().clear();
            getListaAccionUsuarioModificarSeleccionada().clear();
            getListaAccionPerfilModificarSeleccionada().clear();
            getListaAccionReporteModificarSeleccionada().clear();
            iniciarListaAcciones();
            // </editor-fold>
            Faces.getRequestContext().update("divModalPerfil");
            setContenidoModalPerfil("modal-agregar-perfil.xhtml");
            Faces.getRequestContext().update("formPerfil");
            Faces.getRequestContext().execute("show('#modalPerfil');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void agregarPerfil()
    {
        try
        {
            PermisoPorPerfil permisoPorPerfil = new PermisoPorPerfil();
            Permiso permiso = new Permiso();
            if (isEstadoPerfil())
            {
                getPermisoPorPerfilAgregar().getIdPerfil().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getPermisoPorPerfilAgregar().getIdPerfil().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getPerfilServicio().guardar(getPermisoPorPerfilAgregar().getIdPerfil());

            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Mes">
            for (Accion item : getListaAccionMesModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.MES_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.MES_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.MES_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.MES_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.MES_ELIMINAR.getId());
                        break;
                    case Constantes.Accion.AUDITAR:
                        permiso.setId(EnumPermiso.MES_AUDITAR.getId());
                        break;
                    case Constantes.Accion.CIERRE:
                        permiso.setId(EnumPermiso.MES_CIERRE.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Periodo">
            for (Accion item : getListaAccionPeriodoModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.PERIODO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.PERIODO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.PERIODO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.PERIODO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.PERIODO_ELIMINAR.getId());
                        break;
                    case Constantes.Accion.AUDITAR:
                        permiso.setId(EnumPermiso.PERIODO_AUDITAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de INFODAF">
            for (Accion item : getListaAccionInfodafModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.INFODAF_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.INFODAF_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.INFODAF_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Presupuesto">
            for (Accion item : getListaAccionPresupuestoModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_ELIMINAR.getId());
                        break;
                    case Constantes.Accion.AUDITAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_AUDITAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Cliente">
            for (Accion item : getListaAccionClienteModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.CLIENTE_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.CLIENTE_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.CLIENTE_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.CLIENTE_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.CLIENTE_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Sitio">
            for (Accion item : getListaAccionSitioModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.SITIO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.SITIO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.SITIO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.SITIO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.SITIO_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Empleado">
            for (Accion item : getListaAccionEmpleadoModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.EMPLEADO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.EMPLEADO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.EMPLEADO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.EMPLEADO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.EMPLEADO_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Actividad">
            for (Accion item : getListaAccionActividadModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.ACTIVIDAD_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.ACTIVIDAD_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.ACTIVIDAD_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.ACTIVIDAD_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.ACTIVIDAD_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Segmento">
            for (Accion item : getListaAccionSegmentoModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.SEGMENTO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.SEGMENTO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.SEGMENTO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.SEGMENTO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.SEGMENTO_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Empresa">
            for (Accion item : getListaAccionEmpresaModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.EMPRESA_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.EMPRESA_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.EMPRESA_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.EMPRESA_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.EMPRESA_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Usuario">
            for (Accion item : getListaAccionUsuarioModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.USUARIO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.USUARIO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.USUARIO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.USUARIO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.USUARIO_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Perfil">
            for (Accion item : getListaAccionPerfilModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.PERFIL_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.PERFIL_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.PERFIL_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.PERFIL_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.PERFIL_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Reporte">
            for (Accion item : getListaAccionReporteModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.REPORTE_LISTA.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPermisoPorPerfilAgregar().getIdPerfil());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>

            Faces.getRequestContext().execute("hide('#modalPerfil');");
            Faces.addMessage("¡Atención!",
                    "Se agrego correctamente el perfil.", FacesMessage.SEVERITY_INFO);
            iniciarListaPerfiles();
            Faces.getRequestContext().update("formListaPerfiles");
        } catch (ConstraintViolationException e)
        {
            Faces.addMessage("", "Existe un perfil registrado con ese nombre en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);

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
     * @param perfil
     */
    public void mostrarModalModificarPerfil(Perfil perfil)
    {
        getPermisoPorPerfilModificar().setIdPerfil(perfil);
        try
        {
            if (getPermisoPorPerfilModificar().getIdPerfil().getIdEstado().getId().equals(EnumEstado.ACTIVO.getId()))
            {
                setEstadoPerfil(true);
            } else
            {
                setEstadoPerfil(false);
            }
            setMes(false);
            setPeriodo(false);
            setInfodaf(false);
            setPresupuesto(false);
            setCliente(false);
            setSitio(false);
            setEmpleado(false);
            setActividad(false);
            setSegmento(false);
            setSegmento(false);
            setEmpresa(false);
            setUsuario(false);
            setPerfil(false);
            setReporte(false);
            // <editor-fold defaultstate="collapsed" desc="Limpiar listas de checkBox y luego llenarlas">
            getListaAccionMesModificarSeleccionada().clear();
            getListaAccionPeriodoModificarSeleccionada().clear();
            getListaAccionInfodafModificarSeleccionada().clear();
            getListaAccionPresupuestoModificarSeleccionada().clear();
            getListaAccionClienteModificarSeleccionada().clear();
            getListaAccionSitioModificarSeleccionada().clear();
            getListaAccionEmpleadoModificarSeleccionada().clear();
            getListaAccionActividadModificarSeleccionada().clear();
            getListaAccionSegmentoModificarSeleccionada().clear();
            getListaAccionSegmentoModificarSeleccionada().clear();
            getListaAccionEmpresaModificarSeleccionada().clear();
            getListaAccionUsuarioModificarSeleccionada().clear();
            getListaAccionPerfilModificarSeleccionada().clear();
            getListaAccionReporteModificarSeleccionada().clear();
            iniciarListaAcciones();
            setPerfilModificar(perfil);
            setListaPermisoPorPerfil(getPermisoPorPerfilServicio().obtenerTodoPorPerfil(getPermisoPorPerfilModificar()));
            for (PermisoPorPerfil item : getListaPermisoPorPerfil())
            {
                switch (item.getIdPermiso().getIdEntidad().getId())
                {
                    case Constantes.Entidad.EMPLEADO:
                        getListaAccionEmpleadoModificarSeleccionada().add(item.getIdPermiso().getIdAccion());
                        break;
                    case Constantes.Entidad.USUARIO:
                        getListaAccionUsuarioModificarSeleccionada().add(item.getIdPermiso().getIdAccion());
                        break;
                    case Constantes.Entidad.PERFIL:
                        getListaAccionPerfilModificarSeleccionada().add(item.getIdPermiso().getIdAccion());
                        break;
                }
            }
            if (getListaAccionMesModificarSeleccionada().size() == 7)
            {
                setMes(true);
            }
            if (getListaAccionPeriodoModificarSeleccionada().size() == 6)
            {
                setPeriodo(true);
            }
            if (getListaAccionInfodafModificarSeleccionada().size() == 3)
            {
                setInfodaf(true);
            }
            if (getListaAccionPresupuestoModificarSeleccionada().size() == 6)
            {
                setPresupuesto(true);
            }
            if (getListaAccionClienteModificarSeleccionada().size() == 5)
            {
                setCliente(true);
            }
            if (getListaAccionSitioModificarSeleccionada().size() == 5)
            {
                setSitio(true);
            }
            if (getListaAccionEmpleadoModificarSeleccionada().size() == 5)
            {
                setEmpleado(true);
            }
            if (getListaAccionActividadModificarSeleccionada().size() == 5)
            {
                setActividad(true);
            }
            if (getListaAccionSegmentoModificarSeleccionada().size() == 5)
            {
                setSegmento(true);
            }
            if (getListaAccionSegmentoModificarSeleccionada().size() == 5)
            {
                setSegmento(true);
            }
            if (getListaAccionEmpresaModificarSeleccionada().size() == 5)
            {
                setEmpresa(true);
            }
            if (getListaAccionUsuarioModificarSeleccionada().size() == 5)
            {
                setUsuario(true);
            }
            if (getListaAccionPerfilModificarSeleccionada().size() == 5)
            {
                setPerfil(true);
            }
            if (getListaAccionReporteModificarSeleccionada().size() == 1)
            {
                setReporte(true);
            }
            // </editor-fold>

            Faces.getRequestContext().update("divModalPerfil");
            setContenidoModalPerfil("modal-modificar-perfil.xhtml");
            Faces.getRequestContext().update("formPerfil");
            Faces.getRequestContext().execute("show('#modalPerfil');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Eventos de seleccionar los checkbox Entidad">
    public void seleccionarTodoAccionMes()
    {
        if (isMes())
        {
            setListaAccionMesModificarSeleccionada(getListaAccion());
        } else
        {
            setListaAccionMesModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tableMes");
    }

    public void seleccionarTodoAccionPeriodo()
    {
        if (isPeriodo())
        {
            setListaAccionPeriodoModificarSeleccionada(getListaAccion());
        } else
        {
            setListaAccionPeriodoModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tablePeriodo");
    }

    public void seleccionarTodoAccionInfodaf()
    {
        if (isInfodaf())
        {
            setListaAccionInfodafModificarSeleccionada(getListaAccionReporteINFODAF());
        } else
        {
            setListaAccionInfodafModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tableInfodaf");
    }

    public void seleccionarTodoAccionPresupuesto()
    {
        if (isPresupuesto())
        {
            setListaAccionPresupuestoModificarSeleccionada(getListaAccion());
        } else
        {
            setListaAccionPresupuestoModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tablePresupuesto");
    }

    public void seleccionarTodoAccionCliente()
    {
        if (isCliente())
        {
            setListaAccionClienteModificarSeleccionada(getListaAccionSinAuditar());
        } else
        {
            setListaAccionClienteModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tableCliente");
    }

    public void seleccionarTodoAccionSitio()
    {
        if (isSitio())
        {
            setListaAccionSitioModificarSeleccionada(getListaAccionSinAuditar());
        } else
        {
            setListaAccionSitioModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tableSitio");
    }

    public void seleccionarTodoAccionEmpleado()
    {
        if (isEmpleado())
        {
            setListaAccionEmpleadoModificarSeleccionada(getListaAccionSinAuditar());
        } else
        {
            setListaAccionEmpleadoModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tableEmpleado");
    }

    public void seleccionarTodoAccionActividad()
    {
        if (isActividad())
        {
            setListaAccionActividadModificarSeleccionada(getListaAccionSinAuditar());
        } else
        {
            setListaAccionActividadModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tableActividad");
    }

    public void seleccionarTodoAccionSegmento()
    {
        if (isSegmento())
        {
            setListaAccionSegmentoModificarSeleccionada(getListaAccionSinAuditar());
        } else
        {
            setListaAccionSegmentoModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tableSegmento");
    }

    public void seleccionarTodoAccionEmpresa()
    {
        if (isEmpresa())
        {
            setListaAccionEmpresaModificarSeleccionada(getListaAccionSinAuditar());
        } else
        {
            setListaAccionEmpresaModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tableEmpresa");
    }

    public void seleccionarTodoAccionUsuario()
    {
        if (isUsuario())
        {
            setListaAccionUsuarioModificarSeleccionada(getListaAccionSinAuditar());
        } else
        {
            setListaAccionUsuarioModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tableUsuario");
    }

    public void seleccionarTodoAccionPerfil()
    {
        if (isPerfil())
        {
            setListaAccionPerfilModificarSeleccionada(getListaAccionSinAuditar());
        } else
        {
            setListaAccionPerfilModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tablePerfil");
    }

    public void seleccionarTodoAccionReporte()
    {
        if (isReporte())
        {
            setListaAccionReporteModificarSeleccionada(getListaAccionReporte());
        } else
        {
            setListaAccionReporteModificarSeleccionada(new ArrayList<>());
        }
        Faces.getRequestContext().update("formPerfil:tableReporte");
    }
    // </editor-fold>

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizarPerfil()
    {
        try
        {
            PermisoPorPerfil permisoPorPerfil = new PermisoPorPerfil();
            Permiso permiso = new Permiso();
            if (isEstadoPerfil())
            {
                getPermisoPorPerfilModificar().getIdPerfil().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            } else
            {
                getPermisoPorPerfilModificar().getIdPerfil().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            }
            getPerfilServicio().actualizar(getPermisoPorPerfilModificar().getIdPerfil());

            getPermisoPorPerfilServicio()
                    .ejecutarConsultaHQL("delete PermisoPorPerfil pp "
                            + "where pp.idPerfil.id="
                            + getPermisoPorPerfilModificar().getIdPerfil().getId());

            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Mes">
            for (Accion item : getListaAccionMesModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.MES_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.MES_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.MES_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.MES_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.MES_ELIMINAR.getId());
                        break;
                    case Constantes.Accion.AUDITAR:
                        permiso.setId(EnumPermiso.MES_AUDITAR.getId());
                        break;
                    case Constantes.Accion.CIERRE:
                        permiso.setId(EnumPermiso.MES_CIERRE.getId());
                        break;
                    default:
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Periodo">
            for (Accion item : getListaAccionPeriodoModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.PERIODO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.PERIODO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.PERIODO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.PERIODO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.PERIODO_ELIMINAR.getId());
                        break;
                    case Constantes.Accion.AUDITAR:
                        permiso.setId(EnumPermiso.PERIODO_AUDITAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de INFODAF">
            for (Accion item : getListaAccionInfodafModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.INFODAF_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.INFODAF_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.INFODAF_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Presupuesto">
            for (Accion item : getListaAccionPresupuestoModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_ELIMINAR.getId());
                        break;
                    case Constantes.Accion.AUDITAR:
                        permiso.setId(EnumPermiso.PRESUPUESTO_AUDITAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Cliente">
            for (Accion item : getListaAccionClienteModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.CLIENTE_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.CLIENTE_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.CLIENTE_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.CLIENTE_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.CLIENTE_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Sitio">
            for (Accion item : getListaAccionSitioModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.SITIO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.SITIO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.SITIO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.SITIO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.SITIO_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Empleado">
            for (Accion item : getListaAccionEmpleadoModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.EMPLEADO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.EMPLEADO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.EMPLEADO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.EMPLEADO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.EMPLEADO_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Actividad">
            for (Accion item : getListaAccionActividadModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.ACTIVIDAD_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.ACTIVIDAD_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.ACTIVIDAD_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.ACTIVIDAD_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.ACTIVIDAD_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Segmento">
            for (Accion item : getListaAccionSegmentoModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.SEGMENTO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.SEGMENTO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.SEGMENTO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.SEGMENTO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.SEGMENTO_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Empresa">
            for (Accion item : getListaAccionEmpresaModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.EMPRESA_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.EMPRESA_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.EMPRESA_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.EMPRESA_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.EMPRESA_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Usuario">
            for (Accion item : getListaAccionUsuarioModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.USUARIO_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.USUARIO_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.USUARIO_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.USUARIO_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.USUARIO_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Perfil">
            for (Accion item : getListaAccionPerfilModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.REGISTRAR:
                        permiso.setId(EnumPermiso.PERFIL_REGISTRAR.getId());
                        break;
                    case Constantes.Accion.MODIFICAR:
                        permiso.setId(EnumPermiso.PERFIL_MODIFICAR.getId());
                        break;
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.PERFIL_LISTA.getId());
                        break;
                    case Constantes.Accion.INHABILITAR:
                        permiso.setId(EnumPermiso.PERFIL_INHABILITAR.getId());
                        break;
                    case Constantes.Accion.ELIMINAR:
                        permiso.setId(EnumPermiso.PERFIL_ELIMINAR.getId());
                        break;
                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="Registrar Permisos de Reporte">
            for (Accion item : getListaAccionReporteModificarSeleccionada())
            {
                switch (item.getId())
                {
                    case Constantes.Accion.LISTAR:
                        permiso.setId(EnumPermiso.REPORTE_LISTA.getId());
                        break;

                }
                permisoPorPerfil.setIdPerfil(getPerfilModificar());
                permisoPorPerfil.setIdPermiso(permiso);
                getPermisoPorPerfilServicio().guardar(permisoPorPerfil);
            }
            // </editor-fold>

            Faces.getRequestContext().execute("hide('#modalPerfil');");
            Faces.addMessage("¡Atención!",
                    "Se actualizo correctamente el perfil "
                    + getPerfilModificar().getNombre(), FacesMessage.SEVERITY_INFO);
            iniciarListaPerfiles();
            Faces.getRequestContext().update("formListaPerfiles");
        } catch (DataIntegrityViolationException e)
        {
            Faces.addMessage("", "Existe un perfil registrado con ese nombre en el sistema.", FacesMessage.SEVERITY_WARN);
            LOG.error("error: " + e);
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalInhabilitarPerfil(Perfil perfil)
    {
        setPerfilModificar(perfil);
        Faces.getRequestContext().update("divModalPerfil");
        setContenidoModalPerfil("modal-inhabilitar-perfil.xhtml");
        Faces.getRequestContext().update("formPerfil");
        Faces.getRequestContext().execute("show('#modalPerfil');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void inhabilitarPerfil()
    {
        try
        {
            getPerfilModificar().getIdEstado().setId(EnumEstado.INACTIVO.getId());
            getPerfilServicio().actualizar(getPerfilModificar());
            Faces.getRequestContext().execute("hide('#modalPerfil');");
            Faces.addMessage("¡Atención!",
                    "El usuario ha sido inhabilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaPerfiles();
            Faces.getRequestContext().update("formListaPerfiles");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalHabilitarPerfil(Perfil perfil)
    {
        setPerfilModificar(perfil);
        Faces.getRequestContext().update("divModalPerfil");
        setContenidoModalPerfil("modal-habilitar-perfil.xhtml");
        Faces.getRequestContext().update("formPerfil");
        Faces.getRequestContext().execute("show('#modalPerfil');");
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void habilitarPerfil()
    {
        try
        {
            getPerfilModificar().getIdEstado().setId(EnumEstado.ACTIVO.getId());
            getPerfilServicio().actualizar(getPerfilModificar());
            Faces.getRequestContext().execute("hide('#modalPerfil');");
            Faces.addMessage("¡Atención!",
                    "El usuario ha sido habilitado.", FacesMessage.SEVERITY_WARN);
            iniciarListaPerfiles();
            Faces.getRequestContext().update("formListaPerfiles");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    public void mostrarModalEliminarPerfil(Perfil perfil)
    {
        try
        {
            setPerfilModificar(perfil);
            Faces.getRequestContext().update("divModalPerfil");
            setContenidoModalPerfil("modal-eliminar-perfil.xhtml");
            Faces.getRequestContext().update("formPerfil");
            Faces.getRequestContext().execute("show('#modalPerfil');");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void eliminarPerfil()
    {
        try
        {
            getPerfilServicio().eliminar(getPerfilModificar());
            Faces.getRequestContext().execute("hide('#modalPerfil');");
            Faces.addMessage("¡Atención!",
                    "El perfil ha sido eliminado.", FacesMessage.SEVERITY_INFO);
            iniciarListaPerfiles();
            Faces.getRequestContext().update("formListaPerfiles");
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
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

    public boolean isEstadoPerfil()
    {
        return estadoPerfil;
    }

    public void setEstadoPerfil(boolean estadoPerfil)
    {
        this.estadoPerfil = estadoPerfil;
    }

    public String getContenidoModalPerfil()
    {
        return contenidoModalPerfil;
    }

    public void setContenidoModalPerfil(String contenidoModalPerfil)
    {
        this.contenidoModalPerfil = contenidoModalPerfil;
    }

    public Perfil getPerfilModificar()
    {
        return perfilModificar;
    }

    public void setPerfilModificar(Perfil perfilModificar)
    {
        this.perfilModificar = perfilModificar;
    }

    public List<Accion> getListaAccionUsuarioModificarSeleccionada()
    {
        if (listaAccionUsuarioModificarSeleccionada == null)
        {
            listaAccionUsuarioModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionUsuarioModificarSeleccionada;
    }

    public void setListaAccionUsuarioModificarSeleccionada(List<Accion> listaAccionUsuarioModificarSeleccionada)
    {
        this.listaAccionUsuarioModificarSeleccionada = listaAccionUsuarioModificarSeleccionada;
    }

    public List<Accion> getListaAccion()
    {
        if (listaAccion == null)
        {
            listaAccion = new ArrayList<>();
        }
        return listaAccion;
    }

    public void setListaAccion(List<Accion> listaAccion)
    {
        this.listaAccion = listaAccion;
    }

    public AccionServicio getAccionServicio()
    {
        return accionServicio;
    }

    public void setAccionServicio(AccionServicio accionServicio)
    {
        this.accionServicio = accionServicio;
    }

    public List<Accion> getListaAccionMesModificarSeleccionada()
    {
        if (listaAccionMesModificarSeleccionada == null)
        {
            listaAccionMesModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionMesModificarSeleccionada;
    }

    public void setListaAccionMesModificarSeleccionada(List<Accion> listaAccionMesModificarSeleccionada)
    {
        this.listaAccionMesModificarSeleccionada = listaAccionMesModificarSeleccionada;
    }

    public List<Accion> getListaAccionPeriodoModificarSeleccionada()
    {
        if (listaAccionPeriodoModificarSeleccionada == null)
        {
            listaAccionPeriodoModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionPeriodoModificarSeleccionada;
    }

    public void setListaAccionPeriodoModificarSeleccionada(List<Accion> listaAccionPeriodoModificarSeleccionada)
    {
        this.listaAccionPeriodoModificarSeleccionada = listaAccionPeriodoModificarSeleccionada;
    }

    public List<Accion> getListaAccionClienteModificarSeleccionada()
    {
        if (listaAccionClienteModificarSeleccionada == null)
        {
            listaAccionClienteModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionClienteModificarSeleccionada;
    }

    public void setListaAccionClienteModificarSeleccionada(List<Accion> listaAccionClienteModificarSeleccionada)
    {
        this.listaAccionClienteModificarSeleccionada = listaAccionClienteModificarSeleccionada;
    }

    public List<Accion> getListaAccionSitioModificarSeleccionada()
    {
        if (listaAccionSitioModificarSeleccionada == null)
        {
            listaAccionSitioModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionSitioModificarSeleccionada;
    }

    public void setListaAccionSitioModificarSeleccionada(List<Accion> listaAccionSitioModificarSeleccionada)
    {
        this.listaAccionSitioModificarSeleccionada = listaAccionSitioModificarSeleccionada;
    }

    public List<Accion> getListaAccionEmpleadoModificarSeleccionada()
    {
        if (listaAccionEmpleadoModificarSeleccionada == null)
        {
            listaAccionEmpleadoModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionEmpleadoModificarSeleccionada;
    }

    public void setListaAccionEmpleadoModificarSeleccionada(List<Accion> listaAccionEmpleadoModificarSeleccionada)
    {
        this.listaAccionEmpleadoModificarSeleccionada = listaAccionEmpleadoModificarSeleccionada;
    }

    public List<Accion> getListaAccionActividadModificarSeleccionada()
    {
        if (listaAccionActividadModificarSeleccionada == null)
        {
            listaAccionActividadModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionActividadModificarSeleccionada;
    }

    public void setListaAccionActividadModificarSeleccionada(List<Accion> listaAccionActividadModificarSeleccionada)
    {
        this.listaAccionActividadModificarSeleccionada = listaAccionActividadModificarSeleccionada;
    }

    public List<Accion> getListaAccionSegmentoModificarSeleccionada()
    {
        if (listaAccionSegmentoModificarSeleccionada == null)
        {
            listaAccionSegmentoModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionSegmentoModificarSeleccionada;
    }

    public void setListaAccionSegmentoModificarSeleccionada(List<Accion> listaAccionSegmentoModificarSeleccionada)
    {
        this.listaAccionSegmentoModificarSeleccionada = listaAccionSegmentoModificarSeleccionada;
    }

    public List<Accion> getListaAccionEmpresaModificarSeleccionada()
    {
        if (listaAccionEmpresaModificarSeleccionada == null)
        {
            listaAccionEmpresaModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionEmpresaModificarSeleccionada;
    }

    public void setListaAccionEmpresaModificarSeleccionada(List<Accion> listaAccionEmpresaModificarSeleccionada)
    {
        this.listaAccionEmpresaModificarSeleccionada = listaAccionEmpresaModificarSeleccionada;
    }

    public List<Accion> getListaAccionPerfilModificarSeleccionada()
    {
        if (listaAccionPerfilModificarSeleccionada == null)
        {
            listaAccionPerfilModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionPerfilModificarSeleccionada;
    }

    public void setListaAccionPerfilModificarSeleccionada(List<Accion> listaAccionPerfilModificarSeleccionada)
    {
        this.listaAccionPerfilModificarSeleccionada = listaAccionPerfilModificarSeleccionada;
    }

    public PermisoPorPerfilServicio getPermisoPorPerfilServicio()
    {
        return permisoPorPerfilServicio;
    }

    public void setPermisoPorPerfilServicio(PermisoPorPerfilServicio permisoPorPerfilServicio)
    {
        this.permisoPorPerfilServicio = permisoPorPerfilServicio;
    }

    public PermisoPorPerfil getPermisoPorPerfilModificar()
    {
        if (permisoPorPerfilModificar == null)
        {
            permisoPorPerfilModificar = new PermisoPorPerfil();
        }
        return permisoPorPerfilModificar;
    }

    public void setPermisoPorPerfilModificar(PermisoPorPerfil permisoPorPerfilModificar)
    {
        this.permisoPorPerfilModificar = permisoPorPerfilModificar;
    }

    public List<PermisoPorPerfil> getListaPermisoPorPerfil()
    {
        return listaPermisoPorPerfil;
    }

    public void setListaPermisoPorPerfil(List<PermisoPorPerfil> listaPermisoPorPerfil)
    {
        this.listaPermisoPorPerfil = listaPermisoPorPerfil;
    }

    public boolean isMes()
    {
        return mes;
    }

    public void setMes(boolean mes)
    {
        this.mes = mes;
    }

    public boolean isPeriodo()
    {
        return periodo;
    }

    public void setPeriodo(boolean periodo)
    {
        this.periodo = periodo;
    }

    public boolean isCliente()
    {
        return cliente;
    }

    public void setCliente(boolean cliente)
    {
        this.cliente = cliente;
    }

    public boolean isSitio()
    {
        return sitio;
    }

    public void setSitio(boolean sitio)
    {
        this.sitio = sitio;
    }

    public boolean isEmpleado()
    {
        return empleado;
    }

    public void setEmpleado(boolean empleado)
    {
        this.empleado = empleado;
    }

    public boolean isActividad()
    {
        return actividad;
    }

    public void setActividad(boolean actividad)
    {
        this.actividad = actividad;
    }

    public boolean isSegmento()
    {
        return segmento;
    }

    public void setSegmento(boolean segmento)
    {
        this.segmento = segmento;
    }

    public boolean isEmpresa()
    {
        return empresa;
    }

    public void setEmpresa(boolean empresa)
    {
        this.empresa = empresa;
    }

    public boolean isUsuario()
    {
        return usuario;
    }

    public void setUsuario(boolean usuario)
    {
        this.usuario = usuario;
    }

    public boolean isPerfil()
    {
        return perfil;
    }

    public void setPerfil(boolean perfil)
    {
        this.perfil = perfil;
    }

    public List<Accion> getListaAccionSinAuditar()
    {
        return listaAccionSinAuditar;
    }

    public void setListaAccionSinAuditar(List<Accion> listaAccionSinAuditar)
    {
        this.listaAccionSinAuditar = listaAccionSinAuditar;
    }

    public boolean isInfodaf()
    {
        return infodaf;
    }

    public void setInfodaf(boolean infodaf)
    {
        this.infodaf = infodaf;
    }

    public boolean isPresupuesto()
    {
        return presupuesto;
    }

    public void setPresupuesto(boolean presupuesto)
    {
        this.presupuesto = presupuesto;
    }

    public List<Accion> getListaAccionInfodafModificarSeleccionada()
    {
        if (listaAccionInfodafModificarSeleccionada == null)
        {
            listaAccionInfodafModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionInfodafModificarSeleccionada;
    }

    public void setListaAccionInfodafModificarSeleccionada(List<Accion> listaAccionInfodafModificarSeleccionada)
    {
        this.listaAccionInfodafModificarSeleccionada = listaAccionInfodafModificarSeleccionada;
    }

    public List<Accion> getListaAccionPresupuestoModificarSeleccionada()
    {
        if (listaAccionPresupuestoModificarSeleccionada == null)
        {
            listaAccionPresupuestoModificarSeleccionada = new ArrayList<>();
        }
        return listaAccionPresupuestoModificarSeleccionada;
    }

    public void setListaAccionPresupuestoModificarSeleccionada(List<Accion> listaAccionPresupuestoModificarSeleccionada)
    {
        this.listaAccionPresupuestoModificarSeleccionada = listaAccionPresupuestoModificarSeleccionada;
    }

    public PermisoPorPerfil getPermisoPorPerfilAgregar()
    {
        if (permisoPorPerfilAgregar == null)
        {
            permisoPorPerfilAgregar = new PermisoPorPerfil();
        }

        return permisoPorPerfilAgregar;
    }

    public void setPermisoPorPerfilAgregar(PermisoPorPerfil permisoPorPerfilAgregar)
    {
        this.permisoPorPerfilAgregar = permisoPorPerfilAgregar;
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

    public Perfil getPerfil1Filtro()
    {
        return perfil1Filtro == null ? perfil1Filtro = new Perfil() : perfil1Filtro;
    }

    public void setPerfil1Filtro(Perfil perfil1Filtro)
    {
        this.perfil1Filtro = perfil1Filtro;
    }

    public List<Accion> getListaAccionReporteModificarSeleccionada()
    {
        return listaAccionReporteModificarSeleccionada == null
                ? listaAccionReporteModificarSeleccionada = new ArrayList<>() : listaAccionReporteModificarSeleccionada;
    }

    public void setListaAccionReporteModificarSeleccionada(List<Accion> listaAccionReporteModificarSeleccionada)
    {
        this.listaAccionReporteModificarSeleccionada = listaAccionReporteModificarSeleccionada;
    }

    public boolean isReporte()
    {
        return reporte;
    }

    public void setReporte(boolean reporte)
    {
        this.reporte = reporte;
    }

    public List<Accion> getListaAccionReporte()
    {
        return listaAccionReporte;
    }

    public void setListaAccionReporte(List<Accion> listaAccionReporte)
    {
        this.listaAccionReporte = listaAccionReporte;
    }

    public List<Accion> getListaAccionReporteINFODAF()
    {
        return listaAccionReporteINFODAF;
    }

    public void setListaAccionReporteINFODAF(List<Accion> listaAccionReporteINFODAF)
    {
        this.listaAccionReporteINFODAF = listaAccionReporteINFODAF;
    }

    public List<Accion> getListaAccionMes()
    {
        return listaAccionMes;
    }

    public void setListaAccionMes(List<Accion> listaAccionMes)
    {
        this.listaAccionMes = listaAccionMes;
    }

}
    // </editor-fold>
