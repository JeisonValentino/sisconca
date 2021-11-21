/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Stewen Jordi Mateo Villanueva <smateovj@gmail.com>
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable
{

    //private String aval;
    //@OneToMany(mappedBy = "cliente")
    //private List<Renewal> renovacionList;

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
    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "correo")
    private String correo;

    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "telefono2")
    private String telefono2;

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getOtrosDatos() {
        return otrosDatos;
    }

    public void setOtrosDatos(String otrosDatos) {
        this.otrosDatos = otrosDatos;
    }
    
    @Column(name = "celular")
    private String celular;
    
    @Column(name = "celular2")
    private String celular2;
    
    @Column(name = "referencia")
    private String referencia;
    
    @Column(name = "otrosDatos")
    private String otrosDatos;

    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Column(name = "otro")
    private String otro;
    
    @Column(name = "imagen")
    private Blob imagen;
    
    @Column(name = "formato")
    private String formato;
    
    @Column(name = "id_aval")
    private Integer idAval;

    public Integer getIdAval() {
        return idAval;
    }

    public void setIdAval(Integer idAval) {
        this.idAval = idAval;
    }
    
    @Column(name = "inicio_alquiler")
    @Temporal(TemporalType.DATE)
    private Date inicioAlquiler;
    
    @Column(name = "fin_alquiler")
    @Temporal(TemporalType.DATE)
    private Date finAlquiler;
    
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;
    
    @JoinColumn(name = "estado_solicitud_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstadoSolicitud;

    @JoinColumn(name = "tipo_documento_identidad_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoDocumentoIdentidad idTipoDocumentoIdentidad;

    @JoinColumn(name = "tipo_persona_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoPersona idTipoPersona;

    @JoinColumn(name = "tipo_vivienda_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoVivienda idTipoVivienda;

    @JoinColumn(name = "distrito_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Distrito idDistrito;

    @JoinColumn(name = "zona_cobranza_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ZonaCobranza idZonaCobranza;

    @Transient
    private String nombreCompleto;
    @Transient
    private int first;

    @Transient
    private int pageSize;
            
    @Transient
    private byte[] bytes;
    
    @Transient
    private DefaultStreamedContent dsc;
    
    @Transient
    private StreamedContent sc;

    public Cliente()
    {
    }

    public Cliente(Integer id)
    {
        this.id = id;
    }

    public Cliente(Integer id, String nombre, String apellidoPaterno, String apellidoMaterno, String numeroDocumento)
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
        if (!(object instanceof Cliente))
        {
            return false;
        }
        Cliente other = (Cliente) object;
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

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public Date getFechaRegistro()
    {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro)
    {
        this.fechaRegistro = fechaRegistro;
    }

    public String getOtro()
    {
        return otro;
    }

    public void setOtro(String otro)
    {
        this.otro = otro;
    }

    public TipoPersona getIdTipoPersona()
    {
        return idTipoPersona == null ? idTipoPersona = new TipoPersona() : idTipoPersona;
    }

    public void setIdTipoPersona(TipoPersona idTipoPersona)
    {
        this.idTipoPersona = idTipoPersona;
    }

    public TipoVivienda getIdTipoVivienda()
    {
        return idTipoVivienda == null ? idTipoVivienda = new TipoVivienda() : idTipoVivienda;
    }

    public void setIdTipoVivienda(TipoVivienda idTipoVivienda)
    {
        this.idTipoVivienda = idTipoVivienda;
    }

    public Distrito getIdDistrito()
    {
        return idDistrito == null ? idDistrito = new Distrito() : idDistrito;
    }

    public void setIdDistrito(Distrito idDistrito)
    {
        this.idDistrito = idDistrito;
    }

    public ZonaCobranza getIdZonaCobranza()
    {
        if (idZonaCobranza == null)
        {
            idZonaCobranza = new ZonaCobranza();
        }
        return idZonaCobranza;
    }

    public void setIdZonaCobranza(ZonaCobranza idZonaCobranza)
    {
        this.idZonaCobranza = idZonaCobranza;
    }

//    @XmlTransient
//    public List<Renewal> getRenovacionList()
//    {
//        return renovacionList;
//    }
//
//    public void setRenovacionList(List<Renewal> renovacionList)
//    {
//        this.renovacionList = renovacionList;
//    }

    public Estado getIdEstadoSolicitud() {
        
        if (idEstadoSolicitud == null)
        {
            idEstadoSolicitud = new Estado();
        }
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(Estado idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public DefaultStreamedContent getDsc() {
        return dsc;
    }

    public void setDsc(DefaultStreamedContent dsc) {
        this.dsc = dsc;
    }

    public StreamedContent getSc() { 
        return sc;
    }

    public void setSc(StreamedContent sc) {
        this.sc = sc;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Date getInicioAlquiler() {
        return inicioAlquiler;
    }

    public void setInicioAlquiler(Date inicioAlquiler) {
        this.inicioAlquiler = inicioAlquiler;
    }

    public Date getFinAlquiler() {
        return finAlquiler;
    }

    public void setFinAlquiler(Date finAlquiler) {
        this.finAlquiler = finAlquiler;
    }

    

}
