/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.transaccion;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.ConceptoGasto;
import com.scorpio.sisconca.entidad.ConceptoGastoArea;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Max Dicson
 */
@Repository("conceptoGastoAreaDao")
public class ConceptoGastoAreaDaoImpl extends GenericEntityDaoImpl<ConceptoGastoArea, Serializable> implements ConceptoGastoAreaDao {
    
}
