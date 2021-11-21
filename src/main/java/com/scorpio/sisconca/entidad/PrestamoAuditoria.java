/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad;

import java.io.Serializable;
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
@Table(name = "prestamo_auditoria")
public class PrestamoAuditoria implements Serializable {

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
    
    @Column(name = "numeroRenovacion")
    private Integer numeroRenovacion;
    @Column(name = "fechaRenovacion")
    @Temporal(TemporalType.DATE)
    private Date fechaRenovacion;
    @Column(name = "indicadorRenovacion")
    private Character indicadorRenovacion;
    
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "prestamo")
    private Double prestamo;

    @Column(name = "cuota")
    private Double cuota;

    @Column(name = "monto_pagar")
    private Double montoPagar;

    @Column(name = "garante")
    private Integer garante;

    @JoinColumn(name = "id_empleado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado idEmpleado;

    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    
    @JoinColumn(name = "sede_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sede idSede;


    
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;

    @JoinColumn(name = "artefacto_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Artefacto idArtefacto;

    @JoinColumn(name = "tipo_prestamo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoPrestamo idTipoPrestamo;

    @Column(name = "frecuencia")
    private int frecuencia;

    
    @Column
    private Integer interes;
    
    @Column
    private String lugarEntrega;
    @Column
    private int tipoMoneda;
    
    @Column
    private String tipoGarantia;

    

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

    public Integer getNumeroRenovacion() {
        return numeroRenovacion;
    }

    public void setNumeroRenovacion(Integer numeroRenovacion) {
        this.numeroRenovacion = numeroRenovacion;
    }

    public Date getFechaRenovacion() {
        return fechaRenovacion;
    }

    public void setFechaRenovacion(Date fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public Character getIndicadorRenovacion() {
        return indicadorRenovacion;
    }

    public void setIndicadorRenovacion(Character indicadorRenovacion) {
        this.indicadorRenovacion = indicadorRenovacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Double prestamo) {
        this.prestamo = prestamo;
    }

    public Double getCuota() {
        return cuota;
    }

    public void setCuota(Double cuota) {
        this.cuota = cuota;
    }

    public Double getMontoPagar() {
        return montoPagar;
    }

    public void setMontoPagar(Double montoPagar) {
        this.montoPagar = montoPagar;
    }

    public Integer getGarante() {
        return garante;
    }

    public void setGarante(Integer garante) {
        this.garante = garante;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Sede getIdSede() {
        return idSede;
    }

    public void setIdSede(Sede idSede) {
        this.idSede = idSede;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Artefacto getIdArtefacto() {
        return idArtefacto;
    }

    public void setIdArtefacto(Artefacto idArtefacto) {
        this.idArtefacto = idArtefacto;
    }

    public TipoPrestamo getIdTipoPrestamo() {
        return idTipoPrestamo;
    }

    public void setIdTipoPrestamo(TipoPrestamo idTipoPrestamo) {
        this.idTipoPrestamo = idTipoPrestamo;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getInteres() {
        return interes;
    }

    public void setInteres(Integer interes) {
        this.interes = interes;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public int getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(int tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getTipoGarantia() {
        return tipoGarantia;
    }

    public void setTipoGarantia(String tipoGarantia) {
        this.tipoGarantia = tipoGarantia;
    }

    
    
}
