package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Arles
 */
@Entity
@Table(name = "giro")
public class Giro implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;

    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGiro")
    private List<DatoComercial> datoComercialList;
    
    @Transient
    private int first;

    @Transient
    private int pageSize;

    public Giro()
    {
    }

    public Giro(Integer id)
    {
        this.id = id;
    }

    public Giro(Integer id, String descripcion, Estado idEstado)
    {
        this.id = id;
        this.descripcion = descripcion;
        this.idEstado = idEstado;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
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

    public List<DatoComercial> getDatoComercialList() {
        return datoComercialList;
    }

    public void setDatoComercialList(List<DatoComercial> datoComercialList) {
        this.datoComercialList = datoComercialList;
    }

    
}
