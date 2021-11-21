package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stewen Jordi Mateo Villanueva <smateovj@gmail.com>
 */
@Entity
@Table(name = "empleado")
public class Empleado implements Serializable
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
    @Column(name = "apellidoPaterno", unique = true)
    private String apellidoPaterno;

    @Column(name = "apellidoMaterno")
    private String apellidoMaterno;

    @Basic(optional = false)
    @Column(name = "numero_documento", unique = true)
    private String numeroDocumento;

    @Column(name = "correo", unique = true)
    private String correo;

    @Column(name = "telefono")
    private String telefono;

    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;

    @JoinColumn(name = "id_tipo_documento_identidad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoDocumentoIdentidad idTipoDocumentoIdentidad;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleado")
    private List<Usuario> usuarioList;

    @JoinColumn(name = "id_sede", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sede idSede;
    
    @Column(name = "direccion")
    private String direccion;
    
    @Column(name = "estadoCivil")
    private String estadoCivil;
    
    @Column(name = "gradoInstruccion")
    private String gradoInstruccion;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getGradoInstruccion() {
        return gradoInstruccion;
    }

    public void setGradoInstruccion(String gradoInstruccion) {
        this.gradoInstruccion = gradoInstruccion;
    }

    public String getConocimientoInformatico() {
        return conocimientoInformatico;
    }

    public void setConocimientoInformatico(String conocimientoInformatico) {
        this.conocimientoInformatico = conocimientoInformatico;
    }
    
    @Column(name = "conocimientoInformatico")
    private String conocimientoInformatico;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleado")
    private List<Prestamo> prestamoList;

    @Transient
    private String nombreCompleto;

    @Transient
    private int first;

    @Transient
    private int pageSize;

    public Empleado()
    {
    }

    public Empleado(Integer id)
    {
        this.id = id;
    }

    public Empleado(Integer id, String nombre, String apellidoPaterno, String apellidoMaterno, String numeroDocumento)
    {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroDocumento = numeroDocumento;
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

    public String getApellidoPaterno()
    {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno)
    {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno()
    {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno)
    {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNumeroDocumento()
    {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento)
    {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCorreo()
    {
        return correo;
    }

    public void setCorreo(String correo)
    {
        this.correo = correo;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
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

    public TipoDocumentoIdentidad getIdTipoDocumentoIdentidad()
    {
        if (idTipoDocumentoIdentidad == null)
        {
            idTipoDocumentoIdentidad = new TipoDocumentoIdentidad();
        }
        return idTipoDocumentoIdentidad;
    }

    public void setIdTipoDocumentoIdentidad(TipoDocumentoIdentidad idTipoDocumentoIdentidad)
    {
        this.idTipoDocumentoIdentidad = idTipoDocumentoIdentidad;
    }

    public List<Usuario> getUsuarioList()
    {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList)
    {
        this.usuarioList = usuarioList;
    }

//    public Sede getIdSede() {
//        return idSede;
//    }
//
//    public void setIdSede(Sede idSede) {
//        this.idSede = idSede;
//    }
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
        if (!(object instanceof Empleado))
        {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "model.Empleado[ id=" + id + " ]";
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

    public String getNombreCompleto()
    {
        nombreCompleto = getNombre() + " " + getApellidoPaterno() + " " + getApellidoMaterno();
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto)
    {
        this.nombreCompleto = nombreCompleto;
    }

    public Sede getIdSede()
    {
        if (idSede == null)
        {
            idSede = new Sede();
        }
        return idSede;
    }

    public void setIdSede(Sede idSede)
    {
        this.idSede = idSede;
    }

    public List<Prestamo> getPrestamoList()
    {
        return prestamoList;
    }

    public void setPrestamoList(List<Prestamo> prestamoList)
    {
        this.prestamoList = prestamoList;
    }
}
