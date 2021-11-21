/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author YVAN
 */
@Entity
@Table(name = "auditoria")
public class Auditoria implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "tipo")
    private String tipo;
    
    @JoinColumn(name = "entidadId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Entidad entidadId;
    
    @Column(name = "tipoOperacion")
    private String tipoOperacion;
    
    @Column(name = "fechaOperacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOperacion;
    
    @JoinColumn(name = "usuarioId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    
    @JoinColumn(name = "sedeId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sede sedeId;
    
    private Integer registroId;
    
    @Transient
    private int first;

    @Transient
    private int pageSize;

    @Transient
    private Date filterStart;

    @Transient
    private Date filterEnd;
    
    @Transient
    private Integer filterSede;

    public Integer getRegistroId() {
        return registroId;
    }

    public void setRegistroId(Integer registroId) {
        this.registroId = registroId;
    }
    

    public Integer getFilterSede() {
        return filterSede;
    }

    public void setFilterSede(Integer filterSede) {
        this.filterSede = filterSede;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }



    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }



    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Date getFilterStart() {
        return filterStart;
    }

    public void setFilterStart(Date filterStart) {
        this.filterStart = filterStart;
    }

    public Date getFilterEnd() {
        return filterEnd;
    }

    public void setFilterEnd(Date filterEnd) {
        this.filterEnd = filterEnd;
    }

    public Entidad getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(Entidad entidadId) {
        this.entidadId = entidadId;
    }

    public Sede getSedeId() {
        return sedeId;
    }

    public void setSedeId(Sede sedeId) {
        this.sedeId = sedeId;
    }
    
    
    
}
