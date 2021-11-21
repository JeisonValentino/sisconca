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
@Table(name = "perfil")
public class Perfil implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerfil", orphanRemoval = true)
    private List<PerfilPorUsuario> perfilPorUsuarioList;
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerfil", orphanRemoval = true)
    private List<PermisoPorPerfil> permisoPorPerfilList;

    public Perfil()
    {
    }

    public Perfil(Integer id)
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

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public List<PerfilPorUsuario> getPerfilPorUsuarioList()
    {
        return perfilPorUsuarioList;
    }

    public void setPerfilPorUsuarioList(List<PerfilPorUsuario> perfilPorUsuarioList)
    {
        this.perfilPorUsuarioList = perfilPorUsuarioList;
    }

    public Estado getIdEstado()
    {
        if (idEstado == null)
        {
            idEstado = new Estado();
        }
        return idEstado;
    }

    public void setIdEstado(Estado idEstado)
    {
        this.idEstado = idEstado;
    }

    public List<PermisoPorPerfil> getPermisoPorPerfilList()
    {
        return permisoPorPerfilList;
    }

    public void setPermisoPorPerfilList(List<PermisoPorPerfil> permisoPorPerfilList)
    {
        this.permisoPorPerfilList = permisoPorPerfilList;
    }

    public int compareTo(Perfil perfil)
    {
        String nombre = ((Perfil) perfil).getNombre();
        //ascending order
        return this.nombre.compareTo(nombre);
        //descending order
        //return compareQuantity - this.quantity;
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
        if (!(object instanceof Perfil))
        {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "model.Perfil[ id=" + id + " ]";
    }

}
