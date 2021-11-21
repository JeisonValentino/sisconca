/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.maestros;

import com.scorpio.sisconca.entidad.TipoCambio;
import com.scorpio.sisconca.servicio.maestros.TipoCambioServicio;
import com.scorpio.sisconca.servicio.maestros.TipoCambioServicioImpl;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author YVAN
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        
        List<TipoCambio> listaTC = new ArrayList<>();
        
        TipoCambioServicioImpl servicio=new TipoCambioServicioImpl();
        //TipoCambio tipoCambio=new TipoCambio();
        listaTC=servicio.obtenerTodo();
        
        for(TipoCambio t:listaTC){
        }
        

    }
    
}
