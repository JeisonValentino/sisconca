/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.servicio;

import java.sql.Connection;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Max Dicson
 */
@Service("reportService")
public class ReportService {
    
    @Autowired
    DataSource dataSource;
    
    public Connection connect() throws Exception {
        return dataSource.getConnection();
                
    }
    
}
