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
@Table(name = "tipocambio")
public class TipoCambio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(int tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getDescripcionMoneda() {
        return descripcionMoneda;
    }

    public void setDescripcionMoneda(String descripcionMoneda) {
        this.descripcionMoneda = descripcionMoneda;
    }

    public Double getTipoCambioVenta() {
        return tipoCambioVenta;
    }

    public void setTipoCambioVenta(Double tipoCambioVenta) {
        this.tipoCambioVenta = tipoCambioVenta;
    }

    public Double getTipoCambioCompra() {
        return tipoCambioCompra;
    }

    public void setTipoCambioCompra(Double tipoCambioCompra) {
        this.tipoCambioCompra = tipoCambioCompra;
    }

    @Column(name = "tipoMoneda")
    private int tipoMoneda;

    @Basic(optional = false)
    @Column(name = "descripcionMoneda")
    private String descripcionMoneda;

    @Column(name = "tipoCambioVenta")
    private Double tipoCambioVenta;

    @Column(name = "tipoCambioCompra")
    private Double tipoCambioCompra;

    @Transient
    private int first;

    @Transient
    private int pageSize;

    @Transient
    private Date filterStart;

    @Transient
    private Date filterEnd;

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

}
