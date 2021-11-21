package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "zona_cobranza")
public class ZonaCobranza implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "nombre", unique = true)
    private String nombre;

    @Basic(optional = false)
    @Column(name = "abreviatura", unique = true)
    private String abreviatura;

    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idZonaCobranza")
    private List<Cliente> clienteList;

    @Transient
    private int first;

    @Transient
    private int pageSize;

    @Transient
    private List<Prestamo> prestamos;

    public ZonaCobranza()
    {
    }

    public ZonaCobranza(Integer id)
    {
        this.id = id;
    }

    public ZonaCobranza(Integer id, String nombre, String abreviatura, Estado idEstado)
    {
        this.id = id;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
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

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getAbreviatura()
    {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura)
    {
        this.abreviatura = abreviatura;
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

    public List<Cliente> getClienteList()
    {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList)
    {
        this.clienteList = clienteList;
    }

    public List<Prestamo> getPrestamos()
    {
        if (prestamos == null)
        {
            prestamos = new ArrayList<>();
        }
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos)
    {
        this.prestamos = prestamos;
    }

}
