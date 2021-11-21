/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author YVAN
 */
@Entity
@Table(name = "empleado_auditoria")
public class EmpleadoAuditoria implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "fechaOperacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOperacion;
    
    @Column(name = "tipoOperacion")
    private String tipoOperacion;
    
    @JoinColumn(name = "usuarioId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    
    
    @Basic(optional = false)
    @Column(name = "idCodigo", unique = true)
    private Integer idCodigo;
    
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
   
    @JoinColumn(name = "id_sede", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sede idSede;
    
    @Column(name = "direccion")
    private String direccion;
    
    @Column(name = "estadoCivil")
    private String estadoCivil;
    
    @Column(name = "gradoInstruccion")
    private String gradoInstruccion;
    
    @Column(name = "conocimientoInformatico")
    private String conocimientoInformatico;
    
    @Transient
    private int first;

    @Transient
    private int pageSize;

    @Transient
    private Date filterStart;

    @Transient
    private Date filterEnd;
    
    @Transient
    private Integer filterSede;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Integer idCodigo) {
        this.idCodigo = idCodigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public TipoDocumentoIdentidad getIdTipoDocumentoIdentidad() {
        return idTipoDocumentoIdentidad;
    }

    public void setIdTipoDocumentoIdentidad(TipoDocumentoIdentidad idTipoDocumentoIdentidad) {
        this.idTipoDocumentoIdentidad = idTipoDocumentoIdentidad;
    }

    public Sede getIdSede() {
        return idSede;
    }

    public void setIdSede(Sede idSede) {
        this.idSede = idSede;
    }

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

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Date getFilterStart() {
        return filterStart;
    }

    public void setFilterStart(Date filterStart) {
        this.filterStart = filterStart;
    }

    public Date getFilterEnd() {
        return filterEnd;
    }

    public void setFilterEnd(Date filterEnd) {
        this.filterEnd = filterEnd;
    }

    public Integer getFilterSede() {
        return filterSede;
    }

    public void setFilterSede(Integer filterSede) {
        this.filterSede = filterSede;
    }

    
    
}
