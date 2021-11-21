/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad.vistas;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Max Dicson
 */
@Entity
@Table(name = "view_resumen_gastos")
public class ViewResumenGastos implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "id_area")
    private Integer idArea;    
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "id_concepto")
    private Integer idConcepto;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "gasto_soles")
    private Double gastoSoles;
    @Column(name = "gasto_dolares")
    private Double gastoDolares;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getGastoSoles() {
        return gastoSoles;
    }

    public void setGastoSoles(Double gastoSoles) {
        this.gastoSoles = gastoSoles;
    }

    public Double getGastoDolares() {
        return gastoDolares;
    }

    public void setGastoDolares(Double gastoDolares) {
        this.gastoDolares = gastoDolares;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
