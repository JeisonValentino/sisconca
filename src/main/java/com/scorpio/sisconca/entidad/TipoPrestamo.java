
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
import javax.persistence.Transient;

@Entity
@Table(name = "tipo_prestamo")
public class TipoPrestamo implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tiempo")
    private Integer tiempo;

    @Column(name = "interes")
    private Integer interes;

    @Column(name = "cobra")
    private Integer cobra;

    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoPrestamo")
    private List<Prestamo> tipoPrestamoList;

    @Transient
    private int first;

    @Transient
    private int pageSize;

    public TipoPrestamo()
    {
    }

    public TipoPrestamo(Integer id)
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
        if (!(object instanceof TipoPrestamo))
        {
            return false;
        }
        TipoPrestamo other = (TipoPrestamo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "model.TipoPrestamo[ id=" + id + " ]";
    }

    public Integer getTiempo()
    {
        return tiempo;
    }

    public void setTiempo(Integer tiempo)
    {
        this.tiempo = tiempo;
    }

    public Integer getInteres()
    {
        return interes;
    }

    public void setInteres(Integer interes)
    {
        this.interes = interes;
    }

    public Integer getCobra()
    {
        return cobra;
    }

    public void setCobra(Integer cobra)
    {
        this.cobra = cobra;
    }

    public Estado getIdEstado()
    {
        return idEstado == null ? idEstado = new Estado() : idEstado;
    }

    public void setIdEstado(Estado idEstado)
    {
        this.idEstado = idEstado;
    }

    public List<Prestamo> getTipoPrestamoList()
    {
        return tipoPrestamoList;
    }

    public void setTipoPrestamoList(List<Prestamo> tipoPrestamoList)
    {
        this.tipoPrestamoList = tipoPrestamoList;
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
}
