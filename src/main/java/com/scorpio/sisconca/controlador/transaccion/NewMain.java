/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.controlador.transaccion;

import com.scorpio.sisconca.entidad.Artefacto;
import com.scorpio.sisconca.entidad.Cliente;
import com.scorpio.sisconca.entidad.Empleado;
import com.scorpio.sisconca.entidad.Estado;
import com.scorpio.sisconca.entidad.Prestamo;
import com.scorpio.sisconca.entidad.TipoPrestamo;
import com.scorpio.sisconca.servicio.transaccion.PrestamoServicio;
import com.scorpio.sisconca.servicio.transaccion.PrestamoServicioImpl;
import java.util.Calendar;

/**
 *
 * @author YVAN
 */
public class NewMain {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        Prestamo prestamo = new Prestamo();
        prestamo.setId(47);
        Calendar today = Calendar.getInstance();
        prestamo.setFecha(today.getTime());
        prestamo.setPrestamo(1000.0);
        prestamo.setCuota(100.1);
        prestamo.setIdCliente(new Cliente(4418));
        prestamo.setIdEmpleado(new Empleado(1));
        prestamo.setIdTipoPrestamo( new TipoPrestamo(13));
        prestamo.setIdArtefacto(new Artefacto(1));
        prestamo.setIdEstado(new Estado (6));
        PrestamoServicio servicio = new PrestamoServicioImpl();
        servicio.guardar(prestamo);
        
        
    }
    
}
