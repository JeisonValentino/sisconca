/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author YVAN
 */
@Entity
@Table(name = "cliente_auditoria")
public class ClienteAuditoria implements Serializable{
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
    
    @Column(name = "id_aval")
    private Integer idAval;
    
    
    @Column(name = "inicio_alquiler")
    @Temporal(TemporalType.DATE)
    private Date inicioAlquiler;
    
    @Column(name = "fin_alquiler")
    @Temporal(TemporalType.DATE)
    private Date finAlquiler;
    
    @Basic(optional = false)
    @Column(name = "rucDC")
    private BigInteger rucDC;

    @Basic(optional = false)
    @Column(name = "razon_socialDC")
    private String razonSocialDC;

    @Column(name = "direccionDC")
    private String direccionDC;

    @Basic(optional = false)
    @Column(name = "referenciaDC")
    private String referenciaDC;

    @Column(name = "telefonoDC")
    private Integer telefonoDC;
    
    @Column(name = "linea_creditoDC")
    private String lineaCredritoDC;

    @Column(name = "otroDC")
    private String otroDC;
    
    @Column(name = "otrosDatosDC")
    private String otrosDatosDC;
    
    @Column(name = "inicioAlquilerDC")
    @Temporal(TemporalType.DATE)
    private Date inicioAlquilerDC;
    
    @Column(name = "finAlquilerDC")
    @Temporal(TemporalType.DATE)
    private Date finAlquilerDC;
    
    @JoinColumn(name = "giro_idDC", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Giro idGiro;
    
    @JoinColumn(name = "tipo_vivienda_idDC", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoVivienda idTipoViviendaDC;
    
    
    
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
    
    @JoinColumn(name = "sedeId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sede sedeId;
    
      
    
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public Integer getIdAval() {
        return idAval;
    }

    public void setIdAval(Integer idAval) {
        this.idAval = idAval;
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

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Estado getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(Estado idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public TipoDocumentoIdentidad getIdTipoDocumentoIdentidad() {
        return idTipoDocumentoIdentidad;
    }

    public void setIdTipoDocumentoIdentidad(TipoDocumentoIdentidad idTipoDocumentoIdentidad) {
        this.idTipoDocumentoIdentidad = idTipoDocumentoIdentidad;
    }

    public TipoPersona getIdTipoPersona() {
        return idTipoPersona;
    }

    public void setIdTipoPersona(TipoPersona idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }

    public TipoVivienda getIdTipoVivienda() {
        return idTipoVivienda;
    }

    public void setIdTipoVivienda(TipoVivienda idTipoVivienda) {
        this.idTipoVivienda = idTipoVivienda;
    }

    public Distrito getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(Distrito idDistrito) {
        this.idDistrito = idDistrito;
    }

    public ZonaCobranza getIdZonaCobranza() {
        return idZonaCobranza;
    }

    public void setIdZonaCobranza(ZonaCobranza idZonaCobranza) {
        this.idZonaCobranza = idZonaCobranza;
    }

    public BigInteger getRucDC() {
        return rucDC;
    }

    public void setRucDC(BigInteger rucDC) {
        this.rucDC = rucDC;
    }

    public String getRazonSocialDC() {
        return razonSocialDC;
    }

    public void setRazonSocialDC(String razonSocialDC) {
        this.razonSocialDC = razonSocialDC;
    }

    public String getDireccionDC() {
        return direccionDC;
    }

    public void setDireccionDC(String direccionDC) {
        this.direccionDC = direccionDC;
    }

    public String getReferenciaDC() {
        return referenciaDC;
    }

    public void setReferenciaDC(String referenciaDC) {
        this.referenciaDC = referenciaDC;
    }

    public Integer getTelefonoDC() {
        return telefonoDC;
    }

    public void setTelefonoDC(Integer telefonoDC) {
        this.telefonoDC = telefonoDC;
    }

    public String getLineaCredritoDC() {
        return lineaCredritoDC;
    }

    public void setLineaCredritoDC(String lineaCredritoDC) {
        this.lineaCredritoDC = lineaCredritoDC;
    }

    public String getOtroDC() {
        return otroDC;
    }

    public void setOtroDC(String otroDC) {
        this.otroDC = otroDC;
    }

    public String getOtrosDatosDC() {
        return otrosDatosDC;
    }

    public void setOtrosDatosDC(String otrosDatosDC) {
        this.otrosDatosDC = otrosDatosDC;
    }

    public Date getInicioAlquilerDC() {
        return inicioAlquilerDC;
    }

    public void setInicioAlquilerDC(Date inicioAlquilerDC) {
        this.inicioAlquilerDC = inicioAlquilerDC;
    }

    public Date getFinAlquilerDC() {
        return finAlquilerDC;
    }

    public void setFinAlquilerDC(Date finAlquilerDC) {
        this.finAlquilerDC = finAlquilerDC;
    }

    public Giro getIdGiro() {
        return idGiro;
    }

    public void setIdGiro(Giro idGiro) {
        this.idGiro = idGiro;
    }

    public TipoVivienda getIdTipoViviendaDC() {
        return idTipoViviendaDC;
    }

    public void setIdTipoViviendaDC(TipoVivienda idTipoViviendaDC) {
        this.idTipoViviendaDC = idTipoViviendaDC;
    }

    public Sede getSedeId() {
        return sedeId;
    }

    public void setSedeId(Sede sedeId) {
        this.sedeId = sedeId;
    }
    
    
    
    
    
    
}
