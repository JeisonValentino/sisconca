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
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Arles
 */
@Entity
@Table(name = "concepto_gasto")
public class ConceptoGasto implements Serializable
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

    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;
    
    //@JoinColumn(name = "empresa_id", referencedColumnName = "id")
    //@ManyToOne(optional = false)
    //private Area idEmpresa;

    @Transient
    private int first;

    @Transient
    private int pageSize;

    public ConceptoGasto()
    {
    }

    public ConceptoGasto(Integer id)
    {
        this.id = id;
    }

    public ConceptoGasto(Integer id, String nombre, Estado idEstado)
    {
        this.id = id;
        this.nombre = nombre;
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

//    public Area getIdEmpresa() {
 //       return idEmpresa;
 //   }

  //  public void setIdEmpresa(Area idEmpresa) {
   //     this.idEmpresa = idEmpresa;
    //}

}
