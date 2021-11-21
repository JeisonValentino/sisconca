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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stewen Jordi Mateo Villanueva <smateovj@gmail.com>
 */
@Entity
@Table(name = "estado")
public class Estado implements Serializable
{

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<Empleado> empleadoList;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<Perfil> perfilList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<Usuario> usuarioList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<Sede> sedeList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<Artefacto> arfectoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<TipoPrestamo> tipoPrestamoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<Giro> giroList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<Prestamo> prestamoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<DatoComercial> datoComercialList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstadoSolicitud")
    private List<Cliente> clienteList;

    public Estado()
    {
    }

    public Estado(int id)
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

    public List<Perfil> getPerfilList()
    {
        return perfilList;
    }

    public void setPerfilList(List<Perfil> perfilList)
    {
        this.perfilList = perfilList;
    }

    public List<Usuario> getUsuarioList()
    {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList)
    {
        this.usuarioList = usuarioList;
    }

    public List<Sede> getSedeList()
    {
        return sedeList;
    }

    public void setSedeList(List<Sede> sedeList)
    {
        this.sedeList = sedeList;
    }

    public List<Artefacto> getArfectoList()
    {
        return arfectoList;
    }

    public void setArfectoList(List<Artefacto> arfectoList)
    {
        this.arfectoList = arfectoList;
    }

    public List<TipoPrestamo> getTipoPrestamoList()
    {
        return tipoPrestamoList;
    }

    public void setTipoPrestamoList(List<TipoPrestamo> tipoPrestamoList)
    {
        this.tipoPrestamoList = tipoPrestamoList;
    }

    public List<Giro> getGiroList()
    {
        return giroList;
    }

    public void setGiroList(List<Giro> giroList)
    {
        this.giroList = giroList;
    }

    public List<Prestamo> getPrestamoList()
    {
        return prestamoList;
    }

    public void setPrestamoList(List<Prestamo> prestamoList)
    {
        this.prestamoList = prestamoList;
    }

    public List<DatoComercial> getDatoComercialList()
    {
        return datoComercialList;
    }

    public void setDatoComercialList(List<DatoComercial> datoComercialList)
    {
        this.datoComercialList = datoComercialList;
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList()
    {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList)
    {
        this.empleadoList = empleadoList;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }
    
    
}
