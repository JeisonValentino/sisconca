/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author YVAN
 */


public class Consulta implements Serializable{
    
    @Transient
    private String filterNombreCliente;
    @Transient
    private String filterZona;
    
    @Transient
    private Integer filterIdSede;

    public Integer getFilterIdSede() {
        return filterIdSede;
    }

    public void setFilterIdSede(Integer filterIdSede) {
        this.filterIdSede = filterIdSede;
    }

    public String getFilterZona() {
        return filterZona;
    }

    public void setFilterZona(String filterZona) {
        this.filterZona = filterZona;
    }
    @Transient
    private Date filterStart;
    @Transient
    private Date filterEnd;
    
    @Transient
    private Integer id;
    @Transient
    private Date fecha;
    @Transient
    private Integer cantidad;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    @Transient
    private Double prestamo;
    @Transient
    private Double cuota;
    @Transient
    private Double montoPagar;
    @Transient
    private Double interes;
    @Transient
    private Double saldo;

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }
    @Transient
    private Double pago;
    @Transient
    private String nombreCobrador;
    @Transient
    private String estadoPrestamo;
    @Transient
    private String tipoPrestamo;
    @Transient
    private String nombreCliente;
    @Transient
    private String zona;
    
    @Transient
    private String nombreSede;

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public String getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(String estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }
    

    public String getNombreCobrador() {
        return nombreCobrador;
    }

    public void setNombreCobrador(String nombreCobrador) {
        this.nombreCobrador = nombreCobrador;
    }
    

    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
    }

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

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
    @Transient
    private Integer garante;
    @Transient
    private int frecuencia;

    public String getFilterNombreCliente() {
        return filterNombreCliente;
    }

    public void setFilterNombreCliente(String filterNombreCliente) {
        this.filterNombreCliente = filterNombreCliente;
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
