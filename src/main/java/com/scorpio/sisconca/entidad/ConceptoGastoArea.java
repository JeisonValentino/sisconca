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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Max Dicson
 */
@Entity
@Table(name = "concepto_gasto_area")
public class ConceptoGastoArea implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "id_area", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Area idArea;
    
    @JoinColumn(name = "id_concepto_gasto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ConceptoGasto idConceptoGasto;

    public ConceptoGastoArea(Integer id) {
        this.id = id;
    }

    public ConceptoGastoArea() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    public ConceptoGasto getIdConceptoGasto() {
        return idConceptoGasto;
    }

    public void setIdConceptoGasto(ConceptoGasto idConceptoGasto) {
        this.idConceptoGasto = idConceptoGasto;
    }
    
    
    
}
