package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Stewen Jordi Mateo Villanueva <smateovj@gmail.com>
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "nombre", unique = true)
    private String nombre;

    @Basic(optional = false)
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Basic(optional = false)
    @Column(name = "contrasenia")
    private String contrasenia;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario", orphanRemoval = true)
    private List<PerfilPorUsuario> perfilPorUsuarioList;

    @JoinColumn(name = "id_empleado", referencedColumnName = "id", unique = true)
    @ManyToOne(optional = false)
    private Empleado idEmpleado;

    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;

    @Transient
    private String p_estado;

    @Transient
    private String p_mensaje;

    @Transient
    private int first;

    @Transient
    private int pageSize;

    @Transient
    private String perfil;

    // <editor-fold defaultstate="collapsed" desc="Atributos para Inicio Informe">
    @Transient
    private Date fechaInicioInforme;

    @Transient
    private Date fechaFinInforme;

    @Transient
    private int diaInicioInforme;

    @Transient
    private int diaFinInforme;

    @Transient
    private int mesInforme;

    @Transient
    private Map parametrosInforme;
    // </editor-fold>

    public Usuario()
    {
    }

    public Usuario(Integer id)
    {
        this.id = id;
    }

    public Usuario(Integer id, String nombre, Date fechaRegistro, String contrasenia)
    {
        this.id = id;
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        this.contrasenia = contrasenia;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Date getFechaRegistro()
    {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro)
    {
        this.fechaRegistro = fechaRegistro;
    }

    public String getContrasenia()
    {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia)
    {
        this.contrasenia = contrasenia;
    }

    public List<PerfilPorUsuario> getPerfilPorUsuarioList()
    {
        return perfilPorUsuarioList;
    }

    public void setPerfilPorUsuarioList(List<PerfilPorUsuario> perfilPorUsuarioList)
    {
        this.perfilPorUsuarioList = perfilPorUsuarioList;
    }

    public Empleado getIdEmpleado()
    {
        return idEmpleado == null ? idEmpleado = new Empleado() : idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado)
    {
        this.idEmpleado = idEmpleado;
    }

    public Estado getIdEstado()
    {
        if (idEstado == null)
        {
            idEstado = new Estado();
        }
        return idEstado;
    }

    public void setIdEstado(Estado idEstado)
    {
        this.idEstado = idEstado;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario))
        {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "model.Usuario[ id=" + id + " ]";
    }

    public String getP_estado()
    {
        return p_estado;
    }

    public void setP_estado(String p_estado)
    {
        this.p_estado = p_estado;
    }

    public String getP_mensaje()
    {
        return p_mensaje;
    }

    public void setP_mensaje(String p_mensaje)
    {
        this.p_mensaje = p_mensaje;
    }

    public int getFirst()
    {
        return first;
    }

    public void setFirst(int first)
    {
        this.first = first;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public Date getFechaInicioInforme()
    {
        return fechaInicioInforme;
    }

    public void setFechaInicioInforme(Date fechaInicioInforme)
    {
        this.fechaInicioInforme = fechaInicioInforme;
    }

    public Date getFechaFinInforme()
    {
        return fechaFinInforme;
    }

    public void setFechaFinInforme(Date fechaFinInforme)
    {
        this.fechaFinInforme = fechaFinInforme;
    }

    public int getDiaInicioInforme()
    {
        return diaInicioInforme;
    }

    public void setDiaInicioInforme(int diaInicioInforme)
    {
        this.diaInicioInforme = diaInicioInforme;
    }

    public int getDiaFinInforme()
    {
        return diaFinInforme;
    }

    public void setDiaFinInforme(int diaFinInforme)
    {
        this.diaFinInforme = diaFinInforme;
    }

    public int getMesInforme()
    {
        return mesInforme;
    }

    public void setMesInforme(int mesInforme)
    {
        this.mesInforme = mesInforme;
    }

    public Map getParametrosInforme()
    {
        return parametrosInforme;
    }

    public void setParametrosInforme(Map parametrosInforme)
    {
        this.parametrosInforme = parametrosInforme;
    }

    public String getPerfil()
    {
        return perfil;
    }

    public void setPerfil(String perfil)
    {
        this.perfil = perfil;
    }

}
