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
@Table(name = "view_resumen_pagos_supervision")
public class ViewResumenPagosSupervision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "cobrador")
    private String cobrador;
    @Column(name = "idEmpleado")
    private Integer idEmpleado;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "importe")
    private Double importe;
    @Column(name = "indicaGrupoSede")
    private String indicaGrupoSede;
    @Column(name = "sede_id")
    private Integer sede_id;

    public String getIndicaGrupoSede() {
        return indicaGrupoSede;
    }

    public void setIndicaGrupoSede(String indicaGrupoSede) {
        this.indicaGrupoSede = indicaGrupoSede;
    }

    public Integer getSede_id() {
        return sede_id;
    }

    public void setSede_id(Integer sede_id) {
        this.sede_id = sede_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCobrador() {
        return cobrador;
    }

    public void setCobrador(String cobrador) {
        this.cobrador = cobrador;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

}
