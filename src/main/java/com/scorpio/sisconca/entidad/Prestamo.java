/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Formula;

/**
 *
 * @author fsupo
 */
@Entity
@Table(name = "prestamo")
public class Prestamo implements Serializable
{

    @Column(name = "numeroRenovacion")
    private Integer numeroRenovacion;
    @Column(name = "fechaRenovacion")
    @Temporal(TemporalType.DATE)
    private Date fechaRenovacion;
    @Column(name = "indicadorRenovacion")
    private Character indicadorRenovacion;
    @OneToMany(mappedBy = "prestamoPorRenovacion")
    private List<Renewal> renovacionList;
    @OneToMany(mappedBy = "prestamoOrigenRenovacion")
    private List<Renewal> renovacionList1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)

    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "prestamo")
    private Double prestamo;

    @Column(name = "cuota")
    private Double cuota;

    @Column(name = "monto_pagar")
    private Double montoPagar;

    @Column(name = "garante")
    private Integer garante;

    @JoinColumn(name = "id_empleado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado idEmpleado;

    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente idCliente;
    
    @JoinColumn(name = "sede_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sede idSede;


    
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado idEstado;

    @JoinColumn(name = "artefacto_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Artefacto idArtefacto;

    @JoinColumn(name = "tipo_prestamo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoPrestamo idTipoPrestamo;

    @Column(name = "frecuencia")
    private int frecuencia;

    @Transient
    private int first;

    @Transient
    private int pageSize;

    @Transient
    private Date filterStart;

    @Transient
    private Date filterEnd;
    
    @Transient 
    private String filterNombreCliente;
    
    @Transient 
    private Integer filterEstado;

    public Integer getFilterEstado() {
        return filterEstado;
    }

    public void setFilterEstado(Integer filterEstado) {
        this.filterEstado = filterEstado;
    }


    
    @Column
    private Integer interes;
    
    @Column
    private String lugarEntrega;
    @Column
    private int tipoMoneda;
    
    @Column
    private String tipoGarantia;

    public String getTipoGarantia() {
        return tipoGarantia;
    }

    public void setTipoGarantia(String tipoGarantia) {
        this.tipoGarantia = tipoGarantia;
    }
    

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public int getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(int tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }
            

    public String getFilterNombreCliente() {
        return filterNombreCliente;
    }

    public void setFilterNombreCliente(String filterNombreCliente) {
        this.filterNombreCliente = filterNombreCliente;
    }

    @Formula("(select (case when sum(p.pago) is null then 0 else sum(p.pago) end) from pago p where p.idPrestamo = id)")
    private double pagado;

    public Prestamo()
    {
    }

    public Prestamo(Integer id)
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
        if (!(object instanceof Prestamo))
        {
            return false;
        }
        Prestamo other = (Prestamo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Prestamo{" + "id=" + id + ", fecha=" + fecha + ", prestamo=" + prestamo + ", couta=" + cuota + ", montoPagar=" + montoPagar + ", garante=" + garante + ", idArtefacto=" + idArtefacto + ", frecuencia=" + frecuencia + ", first=" + first + ", pageSize=" + pageSize + ", filterStart=" + filterStart + ", filterEnd=" + filterEnd + ", pagado=" + pagado + '}';
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public Double getPrestamo()
    {
        if (prestamo != null)
        {
            return Math.round(this.prestamo * 100.0) / 100.0;
        }
        return 0.0;
    }

    public void setPrestamo(Double prestamo)
    {
        this.prestamo = Math.round(prestamo * 100.0) / 100.0;
    }

    public Double getCuota()
    {
        if (cuota != null)
        {
            return Math.round(this.cuota * 100.0) / 100.0;
        }
        return cuota;
    }

    public void setCuota(Double cuota)
    {
        this.cuota = Math.round(cuota * 100.0) / 100.0;
    }

    public Double getMontoPagar()
    {
        if (montoPagar != null)
        {
            return Math.round(this.montoPagar * 100.0) / 100.0;
        }
        return montoPagar;
    }

    public void setMontoPagar(Double montoPagar)
    {
        this.montoPagar = Math.round(montoPagar * 100.0) / 100.0;
    }

    public Empleado getIdEmpleado()
    {
        if (idEmpleado == null)
        {
            idEmpleado = new Empleado();
        }
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado)
    {
        this.idEmpleado = idEmpleado;
    }

    public Cliente getIdCliente()
    {
        if (idCliente == null)
        {
            idCliente = new Cliente();
        }
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente)
    {
        this.idCliente = idCliente;
    }
    
    public Sede getIdSede() {
        if(idSede==null){
            idSede = new Sede();
        }
        return idSede;
    }

    public void setIdSede(Sede idSede) {
        this.idSede = idSede;
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

    public Artefacto getIdArtefacto()
    {
        if (idArtefacto == null)
        {
            idArtefacto = new Artefacto();
        }
        return idArtefacto;
    }

    public void setIdArtefacto(Artefacto idArtefacto)
    {
        this.idArtefacto = idArtefacto;
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

    public TipoPrestamo getIdTipoPrestamo()
    {
        if (idTipoPrestamo == null)
        {
            idTipoPrestamo = new TipoPrestamo();
        }
        return idTipoPrestamo;
    }

    public void setIdTipoPrestamo(TipoPrestamo idTipoPrestamo)
    {
        this.idTipoPrestamo = idTipoPrestamo;
    }

    public Integer getGarante()
    {
        return garante;
    }

    public void setGarante(Integer garante)
    {
        this.garante = garante;
    }

    public Date getFilterStart()
    {
        return filterStart;
    }

    public void setFilterStart(Date filterStart)
    {
        this.filterStart = filterStart;
    }

    public Date getFilterEnd()
    {
        return filterEnd;
    }

    public void setFilterEnd(Date filterEnd)
    {
        this.filterEnd = filterEnd;
    }


    public double getPagado()
    {
        return pagado;
    }

    public void setPagado(double pagado)
    {
        this.pagado = pagado;
    }

    public int getFrecuencia()
    {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia)
    {
        this.frecuencia = frecuencia;
    }

    public Integer getNumeroRenovacion()
    {
        return numeroRenovacion;
    }

    public void setNumeroRenovacion(Integer numeroRenovacion)
    {
        this.numeroRenovacion = numeroRenovacion;
    }

    public Date getFechaRenovacion()
    {
        return fechaRenovacion;
    }

    public void setFechaRenovacion(Date fechaRenovacion)
    {
        this.fechaRenovacion = fechaRenovacion;
    }

    public Character getIndicadorRenovacion()
    {
        return indicadorRenovacion;
    }

    public void setIndicadorRenovacion(Character indicadorRenovacion)
    {
        this.indicadorRenovacion = indicadorRenovacion;
    }

    @XmlTransient
    public List<Renewal> getRenovacionList()
    {
        return renovacionList;
    }

    public void setRenovacionList(List<Renewal> renovacionList)
    {
        this.renovacionList = renovacionList;
    }

    @XmlTransient
    public List<Renewal> getRenovacionList1()
    {
        return renovacionList1;
    }

    public void setRenovacionList1(List<Renewal> renovacionList1)
    {
        this.renovacionList1 = renovacionList1;
    }

    public Integer getInteres()
    {
        return interes;
    }

    public void setInteres(Integer interes)
    {
        this.interes = interes;
    }
}
