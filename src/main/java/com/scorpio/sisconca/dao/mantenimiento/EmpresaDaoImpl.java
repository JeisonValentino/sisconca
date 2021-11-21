/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.dao.mantenimiento;

import com.scorpio.sisconca.dao.GenericEntityDaoImpl;
import com.scorpio.sisconca.entidad.Area;
import com.scorpio.sisconca.entidad.ZonaCobranza;
import java.io.Serializable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Max Dicson
 */
@Repository("empresaDao")
public class EmpresaDaoImpl extends GenericEntityDaoImpl<Area, Serializable> implements EmpresaDao {
    
}
