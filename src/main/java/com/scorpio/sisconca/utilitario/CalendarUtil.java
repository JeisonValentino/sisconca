/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scorpio.sisconca.utilitario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 *
 * @author smateo
 */
public class CalendarUtil
{

    public static Date obtenerPrimerDiaDelMes(String fecha)
    {
        StringTokenizer stringTokenizer = new StringTokenizer(fecha, "/");
        String mes = stringTokenizer.nextToken();
        String año = stringTokenizer.nextToken();
        Calendar calFechaInicio = Calendar.getInstance();
        calFechaInicio.clear();
        calFechaInicio.set(Calendar.YEAR, Integer.parseInt(año));
        calFechaInicio.set(Calendar.MONTH, Integer.parseInt(mes) - 1);
        calFechaInicio.set(Calendar.DATE, calFechaInicio.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calFechaInicio.getTime();
    }

    /**
     * Convierte una cadena a fecha. Ejem: December 2016 a 12/2016
     *
     * @return
     */
    public static String convertirFechaCadenaAFormatoNumerico(String fechaCadena)
    {
        String[] fechaArray = fechaCadena.split(" ");
        String mesCadena = fechaArray[0];
        StringBuilder fecha = null;
        if (fechaArray.length == 2)
        {
            fecha = new StringBuilder();
            switch (mesCadena.toLowerCase())
            {
                case ("october"):
                    fecha.append("10/");
                    break;
                case ("november"):
                    fecha.append("11/");
                    break;
                case ("december"):
                    fecha.append("12/");
                    break;
                case ("january"):
                    fecha.append("01/");
                    break;
                case ("february"):
                    fecha.append("02/");
                    break;
                case ("march"):
                    fecha.append("03/");
                    break;
                case ("april"):
                    fecha.append("04/");
                    break;
                case ("may"):
                    fecha.append("05/");
                    break;
                case ("june"):
                    fecha.append("06/");
                    break;
                case ("july"):
                    fecha.append("07/");
                    break;
                case ("august"):
                    fecha.append("08/");
                    break;
                case ("september"):
                    fecha.append("09/");
                    break;
            }
            fecha.append(fechaArray[1]);
        }
        return fecha.toString();
    }

    public static Date obtenerUltimoDiaDelMes(String fecha)
    {
        StringTokenizer stringTokenizer = new StringTokenizer(fecha, "/");
        String mes = stringTokenizer.nextToken();
        String año = stringTokenizer.nextToken();
        Calendar calFechaFin = Calendar.getInstance();
        calFechaFin.clear();
        calFechaFin.set(Calendar.YEAR, Integer.parseInt(año));
        calFechaFin.set(Calendar.MONTH, Integer.parseInt(mes) - 1);
        calFechaFin.set(Calendar.DATE, calFechaFin.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calFechaFin.getTime();
    }

    /**
     * Se pasa un Date como parámetro y se obtiene un String con el siguiente
     * formato YYYY-mm-dd
     *
     * @param fecha
     * @return
     */
    public static String formatearDateBd(Date fecha)
    {
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        return formateador.format(fecha);
    }

    public static String formatearAFechaFinBD(Date fecha)
    {
        StringBuilder fechaCadena = new StringBuilder();
        Calendar fechaCalendar = Calendar.getInstance();
        fechaCalendar.clear();
        fechaCalendar.set(Calendar.YEAR, fecha.getYear());
        fechaCalendar.set(Calendar.MONTH, fecha.getMonth());
        fechaCalendar.set(Calendar.DATE, fecha.getDate());

        fechaCadena.append("01/").append(fechaCalendar.get(Calendar.MONTH)).append("/").append(fechaCalendar.get(Calendar.YEAR));
        return fechaCadena.toString();
    }

    /**
     * Se le pasa como parametro la cadena que devuelve el datepickear y se
     * convierte a un Date
     *
     * @param fecha
     * @return
     */
    public static Date convertirDatepickerADate(String fecha)
    {
        Calendar fechaCalendar = Calendar.getInstance();
        fechaCalendar.clear();
        if (!("").equals(fecha))
        {
            StringTokenizer stringTokenizer = new StringTokenizer(fecha, "/");
            int mes = Integer.parseInt(stringTokenizer.nextToken());
            int anio = Integer.parseInt(stringTokenizer.nextToken());
            fechaCalendar.set(Calendar.YEAR, anio);
            fechaCalendar.set(Calendar.MONTH, mes - 1);
        }
        return fechaCalendar.getTime();
    }

    public static String obtenerMesDeFecha(Date date)
    {
        SimpleDateFormat simpleDateFormatMes = new SimpleDateFormat("MM");
        return simpleDateFormatMes.format(date);
    }

    public static String obtenerAnioDeFecha(Date date)
    {
        SimpleDateFormat simpleDateFormatAno = new SimpleDateFormat("yyyy");
        return simpleDateFormatAno.format(date);
    }

    public static Date obtenerUnMesAntes(Date date)
    {
        Calendar calendarActual = Calendar.getInstance();
        calendarActual.clear();
        calendarActual.setTime(date);
        Calendar calendarAnterior = Calendar.getInstance();
        calendarAnterior.set(Calendar.MONTH, calendarActual.get(Calendar.MONTH) - 1);
        calendarAnterior.set(Calendar.YEAR, calendarActual.get(Calendar.YEAR));
        return calendarAnterior.getTime();
    }

    public static String convertirMesTextANumerico(String mesTexto)
    {
        String mesNumerico = "";
        if (null != mesTexto || !("").equalsIgnoreCase(mesTexto))
        {
            switch (mesTexto)
            {
                case ("Enero"):
                    mesNumerico = "01";
                    break;
                case ("Febrero"):
                    mesNumerico = "02";
                    break;
                case ("Marzo"):
                    mesNumerico = "03";
                    break;
                case ("Abril"):
                    mesNumerico = "04";
                    break;
                case ("Mayo"):
                    mesNumerico = "05";
                    break;
                case ("Junio"):
                    mesNumerico = "06";
                    break;
                case ("Julio"):
                    mesNumerico = "07";
                    break;
                case ("Agosto"):
                    mesNumerico = "08";
                    break;
                case ("Septiembre"):
                    mesNumerico = "09";
                    break;
                case ("Octubre"):
                    mesNumerico = "10";
                    break;
                case ("Noviembre"):
                    mesNumerico = "11";
                    break;
                case ("Diciembre"):
                    mesNumerico = "12";
                    break;
            }
        }
        return mesNumerico;
    }

    public static String convertirNumeroTextAMes(String mesNumeroTexto)
    {
        String mesTexto = "";
        Integer mesNumero = Integer.parseInt(mesNumeroTexto);
        if (null != mesNumero)
        {
            switch (mesNumero)
            {
                case (1):
                    mesTexto = "Enero";
                    break;
                case (2):
                    mesTexto = "Febrero";
                    break;
                case (3):
                    mesTexto = "Marzo";
                    break;
                case (4):
                    mesTexto = "Abril";
                    break;
                case (5):
                    mesTexto = "Mayo";
                    break;
                case (6):
                    mesTexto = "Junio";
                    break;
                case (7):
                    mesTexto = "Julio";
                    break;
                case (8):
                    mesTexto = "Agosto";
                    break;
                case (9):
                    mesTexto = "Septiembre";
                    break;
                case (10):
                    mesTexto = "Octubre";
                    break;
                case (11):
                    mesTexto = "Noviembre";
                    break;
                case (12):
                    mesTexto = "Diciembre";
                    break;
            }
        }
        return mesTexto;
    }

    /**
     *
     */
    public static String convertirDateAStringBD(Date fecha)
    {
        SimpleDateFormat simpleDateFormatMes = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormatMes.format(fecha);
    }
}
