package com.scorpio.sisconca.controlador.navegacion;

import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.entidad.PermisoPorPerfil;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.servicio.seguridad.PermisoPorPerfilServicio;
import com.scorpio.sisconca.utilitario.Constantes;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class NavegadorControlador implements Serializable
{

    private static final Logger LOG = Logger.getLogger(NavegadorControlador.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;

    @ManagedProperty("#{permisoPorPerfilServicio}")
    private PermisoPorPerfilServicio permisoPorPerfilServicio;

    private String cabecera = "./view/template/cabecera.xhtml";
    private String menu = "./view/template/menu.xhtml";
    private String contenido = "./view/home.xhtml";
    private String menuDerecha = "./view/template/menu-derecha.xhtml";

    private List<PerfilPorUsuario> listaPerfilPorUsuario;

    // <editor-fold defaultstate="collapsed" desc="boleanos">
    private boolean empleado_Listar;
    private boolean empleado_Agregar;
    private boolean empleado_Modificar;
    private boolean empleado_Inhabilitar;
    private boolean empleado_Eliminar;

    private boolean usuario_Lista;
    private boolean usuario_Agregar;
    private boolean usuario_Modificar;
    private boolean usuario_Inhabilitar;
    private boolean usuario_Eliminar;

    private boolean perfil_Lista;
    private boolean perfil_Agregar;
    private boolean perfil_Modificar;
    private boolean perfil_Inhabilitar;
    private boolean perfil_Eliminar;

    private boolean artefacto_Lista;
    private boolean artefacto_Agregar;
    private boolean artefacto_Modificar;
    private boolean artefacto_Inhabilitar;
    private boolean artefacto_Eliminar;

    private boolean sede_Lista;
    private boolean sede_Agregar;
    private boolean sede_Modificar;
    private boolean sede_Inhabilitar;
    private boolean sede_Eliminar;

    private boolean reporte_Lista;

    private boolean giro_Lista;
    private boolean giro_Agregar;
    private boolean giro_Modificar;
    private boolean giro_Inhabilitar;
    private boolean giro_Eliminar;

    // </editor-fold>
    @PostConstruct
    public void init()
    {
        try
        {
            PerfilPorUsuario perfilPorUsuarioFiltro = new PerfilPorUsuario();
            perfilPorUsuarioFiltro.setIdUsuario(Faces.getUserInSession());
            setListaPerfilPorUsuario(getPerfilPorUsuarioServicio().obtenerTodosPorUsuario(perfilPorUsuarioFiltro));
            for (PerfilPorUsuario perfilPorUsuario : getListaPerfilPorUsuario())
            {
                PermisoPorPerfil permisoPorPerfilFiltro = new PermisoPorPerfil();
                permisoPorPerfilFiltro.setIdPerfil(perfilPorUsuario.getIdPerfil());
                List<PermisoPorPerfil> listaPermisoPorPerfil = getPermisoPorPerfilServicio().obtenerTodoPorPerfil(permisoPorPerfilFiltro);
                for (PermisoPorPerfil permisoPorPerfil : listaPermisoPorPerfil)
                {
                    switch (permisoPorPerfil.getIdPermiso().getId())
                    {
                        // <editor-fold defaultstate="collapsed" desc="Usuario">
                        case Constantes.Permiso.USUARIO_LISTAR:
                            if (!isUsuario_Lista())
                            {
                                setUsuario_Lista(true);
                            }
                            break;
                        case Constantes.Permiso.USUARIO_REGISTRAR:
                            if (!isUsuario_Agregar())
                            {
                                setUsuario_Agregar(true);
                            }
                            break;
                        case Constantes.Permiso.USUARIO_MODIFICAR:
                            if (!isUsuario_Modificar())
                            {
                                setUsuario_Modificar(true);
                            }
                            break;
                        case Constantes.Permiso.USUARIO_INHABILITAR:
                            if (!isUsuario_Inhabilitar())
                            {
                                setUsuario_Inhabilitar(true);
                            }
                            break;
                        case Constantes.Permiso.USUARIO_ELIMINAR:
                            if (!isUsuario_Eliminar())
                            {
                                setUsuario_Eliminar(true);
                            }
                            break;
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="Perfil">
                        case Constantes.Permiso.PERFIL_LISTAR:
                            if (!isPerfil_Lista())
                            {
                                setPerfil_Lista(true);
                            }
                            break;
                        case Constantes.Permiso.PERFIL_REGISTRAR:
                            if (!isPerfil_Agregar())
                            {
                                setPerfil_Agregar(true);
                            }
                            break;
                        case Constantes.Permiso.PERFIL_MODIFICAR:
                            if (!isPerfil_Modificar())
                            {
                                setPerfil_Modificar(true);
                            }
                            break;
                        case Constantes.Permiso.PERFIL_INHABILITAR:
                            if (!isPerfil_Inhabilitar())
                            {
                                setPerfil_Inhabilitar(true);
                            }
                            break;
                        case Constantes.Permiso.PERFIL_ELIMINAR:
                            if (!isPerfil_Eliminar())
                            {
                                setPerfil_Eliminar(true);
                            }
                            break;
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="Empleado">
                        case Constantes.Permiso.EMPLEADO_LISTAR:
                            if (!isEmpleado_Listar())
                            {
                                setEmpleado_Listar(true);
                            }
                            break;
                        case Constantes.Permiso.EMPLEADO_REGISTRAR:
                            if (!isEmpleado_Agregar())
                            {
                                setEmpleado_Agregar(true);
                            }
                            break;
                        case Constantes.Permiso.EMPLEADO_MODIFICAR:
                            if (!isEmpleado_Modificar())
                            {
                                setEmpleado_Modificar(true);
                            }
                            break;
                        case Constantes.Permiso.EMPLEADO_INHABILITAR:
                            if (!isEmpleado_Inhabilitar())
                            {
                                setEmpleado_Inhabilitar(true);
                            }
                            break;
                        case Constantes.Permiso.EMPLEADO_ELIMINAR:
                            if (!isEmpleado_Eliminar())
                            {
                                setEmpleado_Eliminar(true);
                            }
                            break;
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="Artefacto">
                        case Constantes.Permiso.ARTEFACTO_LISTAR:
                            if (!isArtefacto_Lista())
                            {
                                setArtefacto_Lista(true);
                            }
                            break;
                        case Constantes.Permiso.ARTEFACTO_REGISTRAR:
                            if (!isArtefacto_Agregar())
                            {
                                setArtefacto_Agregar(true);
                            }
                            break;
                        case Constantes.Permiso.ARTEFACTO_MODIFICAR:
                            if (!isArtefacto_Modificar())
                            {
                                setArtefacto_Modificar(true);
                            }
                            break;
                        case Constantes.Permiso.ARTEFACTO_INHABILITAR:
                            if (!isArtefacto_Inhabilitar())
                            {
                                setArtefacto_Inhabilitar(true);
                            }
                            break;
                        case Constantes.Permiso.ARTEFACTO_ELIMINAR:
                            if (!isArtefacto_Eliminar())
                            {
                                setArtefacto_Eliminar(true);
                            }
                            break;
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="Sede">
                        case Constantes.Permiso.SEDE_LISTAR:
                            if (!isSede_Lista())
                            {
                                setSede_Lista(true);
                            }
                            break;
                        case Constantes.Permiso.SEDE_REGISTRAR:
                            if (!isSede_Agregar())
                            {
                                setSede_Agregar(true);
                            }
                            break;
                        case Constantes.Permiso.SEDE_MODIFICAR:
                            if (!isSede_Modificar())
                            {
                                setSede_Modificar(true);
                            }
                            break;
                        case Constantes.Permiso.SEDE_INHABILITAR:
                            if (!isSede_Inhabilitar())
                            {
                                setSede_Inhabilitar(true);
                            }
                            break;
                        case Constantes.Permiso.SEDE_ELIMINAR:
                            if (!isSede_Eliminar())
                            {
                                setSede_Eliminar(true);
                            }
                            break;
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="Giro">
                        case Constantes.Permiso.GIRO_LISTAR:
                            if (!isGiro_Lista())
                            {
                                setGiro_Lista(true);
                            }
                            break;
                        case Constantes.Permiso.GIRO_REGISTRAR:
                            if (!isGiro_Agregar())
                            {
                                setGiro_Agregar(true);
                            }
                            break;
                        case Constantes.Permiso.GIRO_MODIFICAR:
                            if (!isGiro_Modificar())
                            {
                                setGiro_Modificar(true);
                            }
                            break;
                        case Constantes.Permiso.GIRO_INHABILITAR:
                            if (!isGiro_Inhabilitar())
                            {
                                setGiro_Inhabilitar(true);
                            }
                            break;
                        case Constantes.Permiso.GIRO_ELIMINAR:
                            if (!isGiro_Eliminar())
                            {
                                setGiro_Eliminar(true);
                            }
                            break;
                        // </editor-fold>
                    }
                }
            }
        } catch (Exception e)
        {
            LOG.error("error: " + e);
            Faces.addMessage("¡ERROR!", "No se pudo realizar la operación, póngase en contacto "
                    + "con el administrador del sistema", FacesMessage.SEVERITY_FATAL);
        }
    }
    
    public boolean isCobrador() throws Exception {
        List<PerfilPorUsuario> perfilPorUsuarios = perfilPorUsuarioServicio.getByUserId(Faces.getUserInSession().getId());
        
        //return perfilPorUsuarios.stream().anyMatch((perfilPorUsuario) -> (perfilPorUsuario.getIdPerfil().getId() == EnumPerfil.COBRADOR.getId()));
        boolean perfilCobrador=false;
        int     numeroPerfiles=0;
        for(PerfilPorUsuario pu :perfilPorUsuarios){
            if(pu.getIdPerfil().getId()==EnumPerfil.COBRADOR.getId()){
                perfilCobrador=true;
                
            }
            numeroPerfiles++;
        }
        if (numeroPerfiles==1 && perfilCobrador){
            return true;
        }else{
            return false;
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public String getCabecera()
    {
        return cabecera;
    }

    public void setCabecera(String cabecera)
    {
        this.cabecera = cabecera;
    }

    public String getMenu()
    {
        return menu;
    }

    public void setMenu(String menu)
    {
        this.menu = menu;
    }

    public String getContenido()
    {
        return contenido;
    }

    public void setContenido(String contenido)
    {
        this.contenido = contenido;
    }

    public PerfilPorUsuarioServicio getPerfilPorUsuarioServicio()
    {
        return perfilPorUsuarioServicio;
    }

    public void setPerfilPorUsuarioServicio(PerfilPorUsuarioServicio perfilPorUsuarioServicio)
    {
        this.perfilPorUsuarioServicio = perfilPorUsuarioServicio;
    }

    public PermisoPorPerfilServicio getPermisoPorPerfilServicio()
    {
        return permisoPorPerfilServicio;
    }

    public void setPermisoPorPerfilServicio(PermisoPorPerfilServicio permisoPorPerfilServicio)
    {
        this.permisoPorPerfilServicio = permisoPorPerfilServicio;
    }

    public boolean isEmpleado_Listar()
    {
        return empleado_Listar;
    }

    public void setEmpleado_Listar(boolean empleado_Listar)
    {
        this.empleado_Listar = empleado_Listar;
    }

    public boolean isEmpleado_Agregar()
    {
        return empleado_Agregar;
    }

    public void setEmpleado_Agregar(boolean empleado_Agregar)
    {
        this.empleado_Agregar = empleado_Agregar;
    }

    public boolean isEmpleado_Modificar()
    {
        return empleado_Modificar;
    }

    public void setEmpleado_Modificar(boolean empleado_Modificar)
    {
        this.empleado_Modificar = empleado_Modificar;
    }

    public boolean isEmpleado_Inhabilitar()
    {
        return empleado_Inhabilitar;
    }

    public void setEmpleado_Inhabilitar(boolean empleado_Inhabilitar)
    {
        this.empleado_Inhabilitar = empleado_Inhabilitar;
    }

    public boolean isEmpleado_Eliminar()
    {
        return empleado_Eliminar;
    }

    public void setEmpleado_Eliminar(boolean empleado_Eliminar)
    {
        this.empleado_Eliminar = empleado_Eliminar;
    }

    public boolean isUsuario_Lista()
    {
        return usuario_Lista;
    }

    public void setUsuario_Lista(boolean usuario_Lista)
    {
        this.usuario_Lista = usuario_Lista;
    }

    public boolean isUsuario_Agregar()
    {
        return usuario_Agregar;
    }

    public void setUsuario_Agregar(boolean usuario_Agregar)
    {
        this.usuario_Agregar = usuario_Agregar;
    }

    public boolean isUsuario_Modificar()
    {
        return usuario_Modificar;
    }

    public void setUsuario_Modificar(boolean usuario_Modificar)
    {
        this.usuario_Modificar = usuario_Modificar;
    }

    public boolean isUsuario_Inhabilitar()
    {
        return usuario_Inhabilitar;
    }

    public void setUsuario_Inhabilitar(boolean usuario_Inhabilitar)
    {
        this.usuario_Inhabilitar = usuario_Inhabilitar;
    }

    public boolean isUsuario_Eliminar()
    {
        return usuario_Eliminar;
    }

    public void setUsuario_Eliminar(boolean usuario_Eliminar)
    {
        this.usuario_Eliminar = usuario_Eliminar;
    }

    public boolean isPerfil_Lista()
    {
        return perfil_Lista;
    }

    public void setPerfil_Lista(boolean perfil_Lista)
    {
        this.perfil_Lista = perfil_Lista;
    }

    public boolean isPerfil_Agregar()
    {
        return perfil_Agregar;
    }

    public void setPerfil_Agregar(boolean perfil_Agregar)
    {
        this.perfil_Agregar = perfil_Agregar;
    }

    public boolean isPerfil_Modificar()
    {
        return perfil_Modificar;
    }

    public void setPerfil_Modificar(boolean perfil_Modificar)
    {
        this.perfil_Modificar = perfil_Modificar;
    }

    public boolean isPerfil_Inhabilitar()
    {
        return perfil_Inhabilitar;
    }

    public void setPerfil_Inhabilitar(boolean perfil_Inhabilitar)
    {
        this.perfil_Inhabilitar = perfil_Inhabilitar;
    }

    public boolean isPerfil_Eliminar()
    {
        return perfil_Eliminar;
    }

    public void setPerfil_Eliminar(boolean perfil_Eliminar)
    {
        this.perfil_Eliminar = perfil_Eliminar;
    }

    public List<PerfilPorUsuario> getListaPerfilPorUsuario()
    {
        return listaPerfilPorUsuario;
    }

    public void setListaPerfilPorUsuario(List<PerfilPorUsuario> listaPerfilPorUsuario)
    {
        this.listaPerfilPorUsuario = listaPerfilPorUsuario;
    }

    public boolean isReporte_Lista()
    {
        return reporte_Lista;
    }

    public void setReporte_Lista(boolean reporte_Lista)
    {
        this.reporte_Lista = reporte_Lista;
    }

    public boolean isArtefacto_Lista()
    {
        return artefacto_Lista;
    }

    public void setArtefacto_Lista(boolean artefacto_Lista)
    {
        this.artefacto_Lista = artefacto_Lista;
    }

    public boolean isArtefacto_Agregar()
    {
        return artefacto_Agregar;
    }

    public void setArtefacto_Agregar(boolean artefacto_Agregar)
    {
        this.artefacto_Agregar = artefacto_Agregar;
    }

    public boolean isArtefacto_Modificar()
    {
        return artefacto_Modificar;
    }

    public void setArtefacto_Modificar(boolean artefacto_Modificar)
    {
        this.artefacto_Modificar = artefacto_Modificar;
    }

    public boolean isArtefacto_Inhabilitar()
    {
        return artefacto_Inhabilitar;
    }

    public void setArtefacto_Inhabilitar(boolean artefacto_Inhabilitar)
    {
        this.artefacto_Inhabilitar = artefacto_Inhabilitar;
    }

    public boolean isArtefacto_Eliminar()
    {
        return artefacto_Eliminar;
    }

    public void setArtefacto_Eliminar(boolean artefacto_Eliminar)
    {
        this.artefacto_Eliminar = artefacto_Eliminar;
    }

    public boolean isSede_Lista()
    {
        return sede_Lista;
    }

    public void setSede_Lista(boolean sede_Lista)
    {
        this.sede_Lista = sede_Lista;
    }

    public boolean isSede_Agregar()
    {
        return sede_Agregar;
    }

    public void setSede_Agregar(boolean sede_Agregar)
    {
        this.sede_Agregar = sede_Agregar;
    }

    public boolean isSede_Modificar()
    {
        return sede_Modificar;
    }

    public void setSede_Modificar(boolean sede_Modificar)
    {
        this.sede_Modificar = sede_Modificar;
    }

    public boolean isSede_Inhabilitar()
    {
        return sede_Inhabilitar;
    }

    public void setSede_Inhabilitar(boolean sede_Inhabilitar)
    {
        this.sede_Inhabilitar = sede_Inhabilitar;
    }

    public boolean isSede_Eliminar()
    {
        return sede_Eliminar;
    }

    public void setSede_Eliminar(boolean sede_Eliminar)
    {
        this.sede_Eliminar = sede_Eliminar;
    }

    public String getMenuDerecha()
    {
        return menuDerecha;
    }

    public void setMenuDerecha(String menuDerecha)
    {
        this.menuDerecha = menuDerecha;
    }

    public boolean isGiro_Lista()
    {
        return giro_Lista;
    }

    public void setGiro_Lista(boolean giro_Lista)
    {
        this.giro_Lista = giro_Lista;
    }

    public boolean isGiro_Agregar()
    {
        return giro_Agregar;
    }

    public void setGiro_Agregar(boolean giro_Agregar)
    {
        this.giro_Agregar = giro_Agregar;
    }

    public boolean isGiro_Modificar()
    {
        return giro_Modificar;
    }

    public void setGiro_Modificar(boolean giro_Modificar)
    {
        this.giro_Modificar = giro_Modificar;
    }

    public boolean isGiro_Inhabilitar()
    {
        return giro_Inhabilitar;
    }

    public void setGiro_Inhabilitar(boolean giro_Inhabilitar)
    {
        this.giro_Inhabilitar = giro_Inhabilitar;
    }

    public boolean isGiro_Eliminar()
    {
        return giro_Eliminar;
    }

    public void setGiro_Eliminar(boolean giro_Eliminar)
    {
        this.giro_Eliminar = giro_Eliminar;
    }

}
// </editor-fold>

