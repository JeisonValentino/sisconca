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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Stewen Jordi Mateo Villanueva <smateovj@gmail.com>
 */
@Entity
@Table(name = "permiso")
public class Permiso implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_accion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Accion idAccion;
    @JoinColumn(name = "id_entidad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Entidad idEntidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPermiso")
    private List<PermisoPorPerfil> permisoPorPerfilList;

    public Permiso()
    {
    }

    public Permiso(Integer id)
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

    public Accion getIdAccion()
    {
        return idAccion;
    }

    public void setIdAccion(Accion idAccion)
    {
        this.idAccion = idAccion;
    }

    public Entidad getIdEntidad()
    {
        return idEntidad;
    }

    public void setIdEntidad(Entidad idEntidad)
    {
        this.idEntidad = idEntidad;
    }

    public List<PermisoPorPerfil> getPermisoPorPerfilList()
    {
        return permisoPorPerfilList;
    }

    public void setPermisoPorPerfilList(List<PermisoPorPerfil> permisoPorPerfilList)
    {
        this.permisoPorPerfilList = permisoPorPerfilList;
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
        if (!(object instanceof Permiso))
        {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "model.Permiso[ id=" + id + " ]";
    }

}
