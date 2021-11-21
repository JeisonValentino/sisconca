/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad.vistas;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Max Dicson
 */
@Entity
@Table(name = "view_resumen_pagos")
public class ViewResumenPago implements Serializable {
    @Id
    @Column(name = "id")
    private Double id;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "cantidad")
    private Long cantidad;
    @Column(name = "pago")
    private Double pago;
    @Column(name = "interes")
    private Double interes;
    @Column(name = "indicaGrupoSede")
    private String indicaGrupoSede;
    @Column(name = "sede_id")
    private Integer sede_id;

    public Integer getSede_id() {
        return sede_id;
    }

    public void setSede_id(Integer sede_id) {
        this.sede_id = sede_id;
    }

    public String getIndicaGrupoSede() {
        return indicaGrupoSede;
    }

    public void setIndicaGrupoSede(String indicaGrupoSede) {
        this.indicaGrupoSede = indicaGrupoSede;
    }

    

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

  
    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }
}
