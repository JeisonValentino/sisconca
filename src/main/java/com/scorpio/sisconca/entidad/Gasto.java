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
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Max Dicson
 */
@Entity
@Table(name = "gasto")
public class Gasto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tipoComprobante")
    private Integer tipoComprobante;
    @Column(name = "serie")
    private String serie;
    @Column(name = "numero")
    private String numero;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "valorNeto")
    private Double valorNeto;
    @Column(name = "igv")
    private Double igv;
    private Double importe;
    @Column(name = "tipoMoneda")
    private Integer tipoMoneda;
    @Column(name = "tipoCambio")
    private Double tipoCambio;
    private Integer estado;

    @JoinColumn(name = "idConcepto", referencedColumnName = "id")
    @ManyToOne
    private ConceptoGasto idConcepto;
    @JoinColumn(name = "id_area", referencedColumnName = "id")
    @ManyToOne
    private Area idArea;
    
    @Transient
    private Boolean estadoTemp;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(Integer tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getValorNeto() {
        return valorNeto;
    }

    public void setValorNeto(Double valorNeto) {
        this.valorNeto = valorNeto;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Integer getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(Integer tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public Double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public ConceptoGasto getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(ConceptoGasto idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Boolean getEstadoTemp() {
        if(estadoTemp == null) {
            estadoTemp = false;
        }
        return estadoTemp;
    }

    public void setEstadoTemp(Boolean estadoTemp) {
        this.estadoTemp = estadoTemp;
    }
    
    
}
