package com.scorpio.sisconca.controlador.report;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.scorpio.sisconca.entidad.Prestamo;
import com.scorpio.sisconca.servicio.transaccion.PrestamoServicio;
import com.scorpio.sisconca.utilitario.enums.EnumEstado;
import com.scorpio.sisconca.utilitario.jsf.Faces;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * @author Alejandro Matos<amatos@gmail.com>
 */
@ManagedBean
@SessionScoped
public class LoansOverdueController
{

    private final Logger log = Logger.getLogger(LoansOverdueController.class.getName());

    private LazyDataModel<Prestamo> lazyLoans;

    @ManagedProperty("#{prestamoServicio}")
    private PrestamoServicio prestamoServicio;

    private double totalAmountLoans = 0;
    private Prestamo loanFilter;

    public void init()
    {

    }

    public void generatePDF()
    {
    }

    private void showLog(String log)
    {
        System.out.println("*************************************");
        System.out.println(log);
        System.out.println("*************************************");
    }

    public LazyDataModel<Prestamo> getLazyLoans()
    {
        return lazyLoans;
    }

    public void setLazyLoans(LazyDataModel<Prestamo> lazyLoans)
    {
        this.lazyLoans = lazyLoans;
    }

    public PrestamoServicio getPrestamoServicio()
    {
        return prestamoServicio;
    }

    public void setPrestamoServicio(PrestamoServicio prestamoServicio)
    {
        this.prestamoServicio = prestamoServicio;
    }

    public double getTotalAmountLoans()
    {
        return totalAmountLoans;
    }

    public void setTotalAmountLoans(double totalAmountLoans)
    {
        this.totalAmountLoans = totalAmountLoans;
    }

    public Prestamo getLoanFilter()
    {
        return loanFilter;
    }

    public void setLoanFilter(Prestamo loanFilter)
    {
        this.loanFilter = loanFilter;
    }

    private void getTotalAmountOfLoans()
    {
        for (Prestamo prestamo : lazyLoans)
        {
            totalAmountLoans += prestamo.getPrestamo();
        }
    }
}
