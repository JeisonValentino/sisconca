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
import javax.persistence.Transient;

/**
 *
 * @author alejandro
 */
@Entity
@Table(name = "renovacion")
public class Renewal implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRenovacion")
    private Integer idRenovacion;

    @Column(name = "saldo")
    private Double saldo;

    @Column(name = "importe")
    private Double importe;

    @Column(name = "estado")
    private Integer estado;

    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    @ManyToOne
    private Cliente cliente;

    @JoinColumn(name = "idPrestamoPorRenovacion", referencedColumnName = "id")
    @ManyToOne
    private Prestamo prestamoPorRenovacion;

    @JoinColumn(name = "idPrestamoOrigenRenovacion", referencedColumnName = "id")
    @ManyToOne
    private Prestamo prestamoOrigenRenovacion;

    @Transient
    private int first;

    @Transient
    private int pageSize;

    @Transient
    private Date start;

    @Transient
    private Date end;

    public Renewal()
    {
    }

    public Renewal(Integer idRenovacion)
    {
        this.idRenovacion = idRenovacion;
    }

    public Integer getIdRenovacion()
    {
        return idRenovacion;
    }

    public void setIdRenovacion(Integer idRenovacion)
    {
        this.idRenovacion = idRenovacion;
    }

    public Double getSaldo()
    {
        return saldo;
    }

    public void setSaldo(Double saldo)
    {
        this.saldo = saldo;
    }

    public Double getImporte()
    {
        return importe;
    }

    public void setImporte(Double importe)
    {
        this.importe = importe;
    }

    public Integer getEstado()
    {
        return estado;
    }

    public void setEstado(Integer estado)
    {
        this.estado = estado;
    }

    public Cliente getCliente()
    {
        if (cliente == null)
        {
            cliente = new Cliente();
        }
        return cliente;
    }

    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }

    public Prestamo getPrestamoPorRenovacion()
    {
        if (prestamoPorRenovacion == null)
        {
            prestamoPorRenovacion = new Prestamo();
        }
        return prestamoPorRenovacion;
    }

    public void setPrestamoPorRenovacion(Prestamo prestamoPorRenovacion)
    {
        this.prestamoPorRenovacion = prestamoPorRenovacion;
    }

    public Prestamo getPrestamoOrigenRenovacion()
    {
        if (prestamoOrigenRenovacion == null)
        {
            prestamoOrigenRenovacion = new Prestamo();
        }
        return prestamoOrigenRenovacion;
    }

    public void setPrestamoOrigenRenovacion(Prestamo prestamoOrigenRenovacion)
    {
        this.prestamoOrigenRenovacion = prestamoOrigenRenovacion;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idRenovacion != null ? idRenovacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Renewal))
        {
            return false;
        }
        Renewal other = (Renewal) object;
        return !((this.idRenovacion == null && other.idRenovacion != null) || (this.idRenovacion != null && !this.idRenovacion.equals(other.idRenovacion)));
    }

    @Override
    public String toString()
    {
        return "com.scorpio.sisconca.entidad.Renovacion[ idRenovacion=" + idRenovacion + " ]";
    }

    public int getFirst()
    {
        return first;
    }

    public void setFirst(int first)
    {
        this.first = first;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public Date getStart()
    {
        return start;
    }

    public void setStart(Date start)
    {
        this.start = start;
    }

    public Date getEnd()
    {
        return end;
    }

    public void setEnd(Date end)
    {
        this.end = end;
    }

}
