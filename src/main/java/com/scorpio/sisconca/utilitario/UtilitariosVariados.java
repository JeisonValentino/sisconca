/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.utilitario;

import com.scorpio.sisconca.entidad.PerfilPorUsuario;
import com.scorpio.sisconca.servicio.seguridad.PerfilPorUsuarioServicio;
import com.scorpio.sisconca.utilitario.enums.EnumPerfil;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author lllactahuaman
 */
public class UtilitariosVariados
{

    @ManagedProperty("#{perfilPorUsuarioServicio}")
    private PerfilPorUsuarioServicio perfilPorUsuarioServicio;

    public static double limpiarValorNumerico(String valor)
    {
        return !valor.equalsIgnoreCase("") ? Double.parseDouble(valor.replaceAll("[(|)|.]", "")) : 0;
    }

    public static double limpiarValorPorcentaje(String valor)
    {
        Double valorDouble = Double.parseDouble(valor.replaceAll("[%]", "").replaceAll(",", "."));
        return Math.abs(valorDouble);
    }

    public static double remplazarComaPorPunto(String valor)
    {
        Double valorDouble = Double.parseDouble(valor.replaceAll(",", "."));
        return Math.abs(valorDouble);
    }

    public static String ponerColorSegun(BigDecimal costoAlimentosActual, BigDecimal costoAlimentosObjetivo)
    {
        String colorResultado;
        int res = 0;
        if (costoAlimentosActual.doubleValue() != 0 && costoAlimentosObjetivo.doubleValue() != 0)
        {
            res = costoAlimentosActual
                    .compareTo(costoAlimentosObjetivo);
        }
        if (res == 1)
        {
            colorResultado = "background-color: #00a61c;color: white";
        }
        else if (res == -1)
        {
            colorResultado = "background-color: #F06464;color: white";
        }
        else
        {
            colorResultado = "";
        }
        return colorResultado;
    }

    public static String ponerColorSegunFoodCost(BigDecimal costoAlimentosActual, BigDecimal costoAlimentosObjetivo)
    {
        String colorResultado;
        int res = 0;
        if (costoAlimentosActual.doubleValue() != 0 && costoAlimentosObjetivo.doubleValue() != 0)
        {
            res = costoAlimentosActual
                    .compareTo(costoAlimentosObjetivo);
        }
        if (res == 1)
        {
            colorResultado = "background-color: #00a61c;color: white";
        }
        else if (res == -1)
        {
            colorResultado = "background-color: #F06464;color: white";
        }
        else
        {
            colorResultado = "";
        }
        return colorResultado;
    }

    public static BigDecimal obtenerTerceraParte(BigDecimal entrada)
    {
        BigDecimal salida = entrada;
        if (null != salida)
        {
            salida = salida.divide(new BigDecimal(3), 4, RoundingMode.HALF_UP);
        }
        else
        {
            salida = new BigDecimal(BigInteger.ZERO);
        }
        return salida;
    }

    public static BigDecimal obtenerDosTercios(BigDecimal entrada)
    {
        BigDecimal salida = entrada;
        if (null != salida)
        {
            salida = salida.multiply(new BigDecimal(2)).divide(new BigDecimal(3), 4, RoundingMode.HALF_UP);
        }
        else
        {
            salida = new BigDecimal(BigInteger.ZERO);
        }
        return salida;
    }

    public PerfilPorUsuarioServicio getPerfilPorUsuarioServicio()
    {
        return perfilPorUsuarioServicio;
    }

    public void setPerfilPorUsuarioServicio(PerfilPorUsuarioServicio perfilPorUsuarioServicio)
    {
        this.perfilPorUsuarioServicio = perfilPorUsuarioServicio;
    }

}
