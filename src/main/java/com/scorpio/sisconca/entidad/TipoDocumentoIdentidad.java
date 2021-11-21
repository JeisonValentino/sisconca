/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Stewen Jordi Mateo Villanueva <smateovj@gmail.com>
 */
@Entity
@Table(name = "tipo_documento_identidad")
@NamedQueries(
        {
            @NamedQuery(name = "TipoDocumentoIdentidad.findAll", query = "SELECT t FROM TipoDocumentoIdentidad t")
        })
public class TipoDocumentoIdentidad implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nombre_documento")
    private String nombreDocumento;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoDocumentoIdentidad")
    private List<Empleado> empleadoList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoDocumentoIdentidad")
    private List<Cliente> clienteList;

    public TipoDocumentoIdentidad()
    {
    }

    public TipoDocumentoIdentidad(Integer id)
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

    public String getNombreDocumento()
    {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento)
    {
        this.nombreDocumento = nombreDocumento;
    }

    public List<Empleado> getEmpleadoList()
    {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList)
    {
        this.empleadoList = empleadoList;
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
        if (!(object instanceof TipoDocumentoIdentidad))
        {
            return false;
        }
        TipoDocumentoIdentidad other = (TipoDocumentoIdentidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "model.TipoDocumentoIdentidad[ id=" + id + " ]";
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    
}
