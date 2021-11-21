/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.utilitario.matematica;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import static javafx.scene.paint.Color.rgb;

/**
 *
 * @author lllactahuaman
 */
public class Formulas
{

    public static BigDecimal reglaTresSimple(BigDecimal total, BigDecimal valorHallar)
    {
        if ((double) 0.0 != valorHallar.doubleValue())
        {
            return (total.multiply(BigDecimal.valueOf(100)).divide(valorHallar, 4, RoundingMode.HALF_UP));

        } else
        {
            return BigDecimal.ZERO;
        }
    }

    public static int randomColorFactor()
    {
        return (int) Math.round(Math.random() * 500);
    }

    public static List<String> obtenerListaColores()
    {
        List<String> listaColorore = new ArrayList<>();

        listaColorore.add("'rgb(51, 102, 204)'");
        listaColorore.add("'rgb(204, 0, 0)'");
        listaColorore.add("'rgb(0, 204, 204)'");
        listaColorore.add("'rgb(255, 128, 0)'");
        listaColorore.add("'rgb(128, 255, 0)'");
        listaColorore.add("'rgb(153, 153, 0)'");
        listaColorore.add("'rgb(128, 128, 128)'");
        listaColorore.add("'rgb(81,328,759)'");

        listaColorore.add("'rgb(0, 102, 51)'");
        listaColorore.add("'rgb(51, 0, 25)'");
        listaColorore.add("'rgb(153, 0, 0)'");
        listaColorore.add("'rgb(153, 76, 0)'");
        listaColorore.add("'rgb(255, 51, 153)'");
        listaColorore.add("'rgb(279,16,21)'");
        listaColorore.add("'rgb(0, 0, 0)'");
        listaColorore.add("'rgb(0, 0, 153)'");
        listaColorore.add("'rgb(255, 0, 0)'");
        listaColorore.add("'rgb(0, 0, 255)'");
        listaColorore.add("'rgb(112,120,65)'");

        return listaColorore;
    }
}
