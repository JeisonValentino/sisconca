/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Stewen Jordi Mateo Villanueva <smateovj@gmail.com>
 */
@Entity
@Table(name = "dato_comercial")
public class DatoComercial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "ruc")
    private BigInteger ruc;

    @Basic(optional = false)
    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "direccion")
    private String direccion;

    @Basic(optional = false)
    @Column(name = "referencia")
    private String referencia;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "linea_credito")
    private String lineaCredrito;

    @Column(name = "otro")
    private String otro;
    
    @Column(name = "otrosDatos")
    private String otrosDatosDC;

    public String getOtrosDatosDC() {
        return otrosDatosDC;
    }

    public void setOtrosDatosDC(String otrosDatosDC) {
        this.otrosDatosDC = otrosDatosDC;
    }
    
    @Column(name = "aval")
    private Integer aval;
    
    @Column(name = "inicioAlquiler")
    @Temporal(TemporalType.DATE)
    private Date inicioAlquilerDC;

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
    
    @Column(name = "finAlquiler")
    @Temporal(TemporalType.DATE)
    private Date finAlquilerDC;
    
    @JoinColumn(name = "giro_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Giro idGiro;
    
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;
    
    @JoinColumn(name = "tipo_vivienda_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoVivienda idTipoVivienda;
    
   
   
    @Transient
    private int first;

    @Transient
    private int pageSize;

    public DatoComercial() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatoComercial)) {
            return false;
        }
        DatoComercial other = (DatoComercial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.DatoComercial[ id=" + id + " ]";
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

   
    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public BigInteger getRuc() {
        return ruc;
    }

    public void setRuc(BigInteger ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getLineaCredrito() {
        return lineaCredrito;
    }

    public void setLineaCredrito(String lineaCredrito) {
        this.lineaCredrito = lineaCredrito;
    }


    public Giro getIdGiro() {
         if (idGiro == null)
        {
            idGiro = new Giro();
        }
        return idGiro;
    }

    public void setIdGiro(Giro idGiro) {
        this.idGiro = idGiro;
    }

    public Cliente getIdCliente() {
        if (idCliente == null)
        {
            idCliente = new Cliente();
        }
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Estado getIdEstado() {
        if (idEstado == null)
        {
            idEstado = new Estado();
        }
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public TipoVivienda getIdTipoVivienda() {
        if (idTipoVivienda == null)
        {
            idTipoVivienda = new TipoVivienda();
        }
        return idTipoVivienda;
    }

    public void setIdTipoVivienda(TipoVivienda idTipoVivienda) {
        this.idTipoVivienda = idTipoVivienda;
    }

    public Integer getAval() {
        return aval;
    }

    public void setAval(Integer aval) {
        this.aval = aval;
    }

    
}
