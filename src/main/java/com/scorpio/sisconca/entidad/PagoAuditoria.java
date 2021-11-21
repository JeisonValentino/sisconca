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
@Table(name = "pago_auditoria")
public class PagoAuditoria implements Serializable{
    
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
    
    
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;

    @Column(name = "pago")
    private Double pago;

    @Column(name = "flagSupervisor")
    private Integer flagSupervisor;
    
    @Column(name = "formaPago")
    private String formaPago;

    
    @JoinColumn(name = "idPrestamo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prestamo prestamo;

    @JoinColumn(name = "idCobrador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cobrador cobrador;

    
    

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

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
    }

    public Integer getFlagSupervisor() {
        return flagSupervisor;
    }

    public void setFlagSupervisor(Integer flagSupervisor) {
        this.flagSupervisor = flagSupervisor;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Cobrador getCobrador() {
        return cobrador;
    }

    public void setCobrador(Cobrador cobrador) {
        this.cobrador = cobrador;
    }
    
    
    
    
}
