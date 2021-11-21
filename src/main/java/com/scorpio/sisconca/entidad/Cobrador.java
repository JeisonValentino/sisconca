/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alejandro
 */
@Entity
@Table(name = "cobrador")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Cobrador.findAll", query = "SELECT c FROM Cobrador c")
})
public class Cobrador implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Integer estado;
    
    @JoinColumn(name = "idEmpleado", referencedColumnName = "id")
    @OneToOne
    private Empleado idEmpleado;

    public Cobrador()
    {
    }

    public Cobrador(Integer id)
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

    public Integer getEstado()
    {
        return estado;
    }

    public void setEstado(Integer estado)
    {
        this.estado = estado;
    }

    public Empleado getIdEmpleado()
    {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado empleado)
    {
        this.idEmpleado = empleado;
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
        if (!(object instanceof Cobrador))
        {
            return false;
        }
        Cobrador other = (Cobrador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.scorpio.sisconca.entidad.Cobrador[ id=" + id + " ]";
    }
    
}
