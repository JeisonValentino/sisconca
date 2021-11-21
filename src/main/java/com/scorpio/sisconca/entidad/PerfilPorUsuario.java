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
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Stewen Jordi Mateo Villanueva <smateovj@gmail.com>
 */
@Entity
@Table(name = "perfil_por_usuario")
@NamedQueries(
        {
            @NamedQuery(name = "PerfilPorUsuario.findAll", query = "SELECT p FROM PerfilPorUsuario p")
        })
public class PerfilPorUsuario implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Perfil idPerfil;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario idUsuario;

    public PerfilPorUsuario()
    {
    }

    public PerfilPorUsuario(Integer id)
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

    public Perfil getIdPerfil()
    {
        return idPerfil;
    }

    public void setIdPerfil(Perfil idPerfil)
    {
        this.idPerfil = idPerfil;
    }

    public Usuario getIdUsuario()
    {
        if (idUsuario == null)
        {
            idUsuario = new Usuario();
        }
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario)
    {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof PerfilPorUsuario))
        {
            return false;
        }
        PerfilPorUsuario other = (PerfilPorUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "model.PerfilPorUsuario[ id=" + id + " ]";
    }

}
