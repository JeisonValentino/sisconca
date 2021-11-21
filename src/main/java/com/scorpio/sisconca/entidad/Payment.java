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

/**
 *
 * @author alejandro
 */
@Entity
@Table(name = "pago")
public class Payment implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;

    @Column(name = "pago")
    private Double pago;

    @Column(name = "flagSupervisor")
    private Integer flagSupervisor;
    
    @Column(name = "formaPago")
    private String formaPago;

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    @JoinColumn(name = "idPrestamo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prestamo prestamo;

    @JoinColumn(name = "idCobrador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cobrador cobrador;

    @Transient
    private int first;

    @Transient
    private int pageSize;

    @Transient
    private Date start;

    @Transient
    private Date end;

    public Payment()
    {
    }

    public Payment(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Date getFechaPago()
    {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago)
    {
        this.fechaPago = fechaPago;
    }

    public Double getPago()
    {
        if (pago != null)
        {
            return Math.round(this.pago * 100.0) / 100.0;
        }
        return pago;
    }

    public void setPago(Double pago)
    {
        this.pago = Math.round(pago * 100.0) / 100.0;
    }

    public Integer getFlagSupervisor()
    {
        return flagSupervisor;
    }

    public void setFlagSupervisor(Integer flagSupervisor)
    {
        this.flagSupervisor = flagSupervisor;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment))
        {
            return false;
        }
        Payment other = (Payment) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString()
    {
        return "Payment{" + "id=" + id + ", fechaPago=" + fechaPago + ", pago=" + pago + ", flagSupervisor=" + flagSupervisor + ", first=" + first + ", pageSize=" + pageSize + ", start=" + start + ", end=" + end + '}';
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

    public Prestamo getPrestamo()
    {
        if (prestamo == null)
        {
            prestamo = new Prestamo();
        }
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo)
    {
        this.prestamo = prestamo;
    }

    public Cobrador getCobrador()
    {
        return cobrador;
    }

    public void setCobrador(Cobrador cobrador)
    {
        this.cobrador = cobrador;
    }

}
