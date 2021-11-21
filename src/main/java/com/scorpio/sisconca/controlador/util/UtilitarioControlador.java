package com.scorpio.sisconca.controlador.util;

import com.scorpio.sisconca.servicio.seguridad.AccionServicio;
import com.scorpio.sisconca.servicio.seguridad.CategoriaEntidadServicio;
import com.scorpio.sisconca.servicio.maestros.EmpleadoServicio;
import com.scorpio.sisconca.servicio.seguridad.EntidadServicio;
import com.scorpio.sisconca.servicio.seguridad.EstadoServicio;
import com.scorpio.sisconca.servicio.seguridad.PerfilServicio;
import com.scorpio.sisconca.servicio.seguridad.PermisoPorPerfilServicio;
import com.scorpio.sisconca.servicio.seguridad.PermisoServicio;
import com.scorpio.sisconca.servicio.seguridad.TipoDocumentoIdentidadServicio;
import com.scorpio.sisconca.servicio.seguridad.UsuarioServicio;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFColor;

@ManagedBean
@SessionScoped
public class UtilitarioControlador implements Serializable
{
    
    private static final Logger LOG = Logger.getLogger(UtilitarioControlador.class.getName());
    
    private static final long serialVersionUID = 1L;
    
    @ManagedProperty("#{accionServicio}")
    private AccionServicio accionServicio;
    
    @ManagedProperty("#{categoriaEntidadServicio}")
    private CategoriaEntidadServicio categoriaEntidadServicio;
    
    @ManagedProperty("#{empleadoServicio}")
    private EmpleadoServicio empleadoServicio;
    
    @ManagedProperty("#{entidadServicio}")
    private EntidadServicio entidadServicio;
    
    @ManagedProperty("#{estadoServicio}")
    private EstadoServicio estadoServicio;
    
    @ManagedProperty("#{perfilServicio}")
    private PerfilServicio perfilServicio;
    
    @ManagedProperty("#{permisoServicio}")
    private PermisoServicio permisoServicio;
    
    @ManagedProperty("#{permisoPorPerfilServicio}")
    private PermisoPorPerfilServicio permisoPorPerfilServicio;
    
    @ManagedProperty("#{tipoDocumentoIdentidadServicio}")
    private TipoDocumentoIdentidadServicio tipoDocumentoIdentidadServicio;
    
    @ManagedProperty("#{usuarioServicio}")
    private UsuarioServicio usuarioServicio;
    private XSSFWorkbook workBook;

    /**
     * Metodo para personalizar el excel que exporta el dataTable de primefaces.
     * <p>
     * @param document
     */
    public void personalizarExcel(Object document)
    {
        
        try
        {
            workBook = (XSSFWorkbook) document;
            XSSFSheet hoja = workBook.getSheetAt(0);
            XSSFRow blanco1 = hoja.createRow(0);
            blanco1.createCell(0).setCellValue("");
            XSSFRow imagen = hoja.getRow(0);
            XSSFRow cabecera = hoja.getRow(2);
            
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            final FileInputStream stream
                    = new FileInputStream(path + "/resources/imagenes/scorpio.png");
            final CreationHelper helper = workBook.getCreationHelper();
            final Drawing drawing = hoja.createDrawingPatriarch();
            final ClientAnchor anchor = helper.createClientAnchor();
            final int pictureIndex = workBook.addPicture(stream, Workbook.PICTURE_TYPE_JPEG);
            final Picture pict = drawing.createPicture(anchor, pictureIndex);
            pict.resize(1);
            imagen.setHeight((short) 590);
            XSSFFont headerFont = createFont(HSSFColor.WHITE.index, (short) 12, true);
            XSSFCellStyle headerStyle = createStyle(
                    headerFont,
                    HSSFCellStyle.ALIGN_CENTER,
                    new XSSFColor(new java.awt.Color(66, 139, 202)),
                    true,
                    HSSFColor.WHITE.index);
            for (int i = 0; i < cabecera.getPhysicalNumberOfCells(); i++)
            {
                XSSFCell cellHeader = cabecera.getCell(i);
                cellHeader.setCellStyle(headerStyle);
//                hoja.autoSizeColumn((short) i);
            }
            
        } catch (IOException ex)
        {
            LOG.error("error: " + ex);
        }
    }

    /**
     * Create a new font on base workbook
     *
     * @param fontColor Font color (see {@link HSSFColor})
     * @param fontHeight Font height in points
     * @param fontBold Font is boldweight (<code>true</code>) or not
     * (<code>false</code>)
     *
     * @return New cell style
     */
    private XSSFFont createFont(short fontColor, short fontHeight, boolean fontBold)
    {
        
        XSSFFont font = workBook.createFont();
        font.setBold(fontBold);
        font.setColor(fontColor);
        font.setFontName("Arial");
        font.setFontHeightInPoints(fontHeight);
        
        return font;
    }

    /**
     * Create a style on base workbook
     *
     * @param font Font used by the style
     * @param cellAlign Cell alignment for contained text (see
     * {@link HSSFCellStyle})
     * @param cellColor Cell background color (see {@link HSSFColor})
     * @param cellBorder Cell has border (<code>true</code>) or not
     * (<code>false</code>)
     * @param cellBorderColor Cell border color (see {@link HSSFColor})
     *
     * @return New cell style
     */
    private XSSFCellStyle createStyle(XSSFFont font, short cellAlign, XSSFColor cellColor, boolean cellBorder, short cellBorderColor)
    {
        
        XSSFCellStyle style = workBook.createCellStyle();
        style.setFont(font);
        style.setAlignment(cellAlign);
        style.setFillForegroundColor(cellColor);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        if (cellBorder)
        {
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            
            style.setTopBorderColor(cellBorderColor);
            style.setLeftBorderColor(cellBorderColor);
            style.setRightBorderColor(cellBorderColor);
            style.setBottomBorderColor(cellBorderColor);
        }
        
        return style;
    }
    
    public void personalizarPdf(Object document) throws IOException, BadElementException, DocumentException
    {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        Image logo = Image.getInstance(path + "/resources/imagenes/scorpio.png");
        logo.setAlignment(Image.MIDDLE);
        logo.scaleAbsoluteHeight(100);
        logo.scaleAbsoluteWidth(100);
        logo.scalePercent(100);
        Chunk chunk = new Chunk(logo, 0, -45);
        HeaderFooter header = new HeaderFooter(new Phrase(chunk), false);
//        pdf.setHeader(header);
        pdf.add(Image.getInstance(logo));
        pdf.add(new Paragraph(" "));
    }
    
    public void personalizarPdfLandscape(Object document) throws IOException, BadElementException, DocumentException
    {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setMargins(-70, -70, 30, 30);
//        pdf.setMarginMirroringTopBottom(false);

        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        Image logo = Image.getInstance(path + "/resources/imagenes/logoExportacion.png");
        logo.setAlignment(Image.MIDDLE);
        logo.scaleAbsoluteHeight(100);
        logo.scaleAbsoluteWidth(100);
        logo.scalePercent(100);
        Chunk chunk = new Chunk(logo, 0, -45);
        HeaderFooter header = new HeaderFooter(new Phrase(chunk), false);
//        pdf.setHeader(header);
        pdf.add(Image.getInstance(logo));
        pdf.add(new Paragraph(" "));
    }

    // <editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public UsuarioServicio getUsuarioServicio()
    {
        return usuarioServicio;
    }
    
    public void setUsuarioServicio(UsuarioServicio usuarioServicio)
    {
        this.usuarioServicio = usuarioServicio;
    }
    
    public EmpleadoServicio getEmpleadoServicio()
    {
        return empleadoServicio;
    }
    
    public void setEmpleadoServicio(EmpleadoServicio empleadoServicio)
    {
        this.empleadoServicio = empleadoServicio;
    }
    
    public TipoDocumentoIdentidadServicio getTipoDocumentoIdentidadServicio()
    {
        return tipoDocumentoIdentidadServicio;
    }
    
    public void setTipoDocumentoIdentidadServicio(TipoDocumentoIdentidadServicio tipoDocumentoIdentidadServicio)
    {
        this.tipoDocumentoIdentidadServicio = tipoDocumentoIdentidadServicio;
    }
    
    public AccionServicio getAccionServicio()
    {
        return accionServicio;
    }
    
    public void setAccionServicio(AccionServicio accionServicio)
    {
        this.accionServicio = accionServicio;
    }
    
    public CategoriaEntidadServicio getCategoriaEntidadServicio()
    {
        return categoriaEntidadServicio;
    }
    
    public void setCategoriaEntidadServicio(CategoriaEntidadServicio categoriaEntidadServicio)
    {
        this.categoriaEntidadServicio = categoriaEntidadServicio;
    }
    
    public EntidadServicio getEntidadServicio()
    {
        return entidadServicio;
    }
    
    public void setEntidadServicio(EntidadServicio entidadServicio)
    {
        this.entidadServicio = entidadServicio;
    }
    
    public EstadoServicio getEstadoServicio()
    {
        return estadoServicio;
    }
    
    public void setEstadoServicio(EstadoServicio estadoServicio)
    {
        this.estadoServicio = estadoServicio;
    }
    
    public PerfilServicio getPerfilServicio()
    {
        return perfilServicio;
    }
    
    public void setPerfilServicio(PerfilServicio perfilServicio)
    {
        this.perfilServicio = perfilServicio;
    }
    
    public PermisoServicio getPermisoServicio()
    {
        return permisoServicio;
    }
    
    public void setPermisoServicio(PermisoServicio permisoServicio)
    {
        this.permisoServicio = permisoServicio;
    }
    
    public PermisoPorPerfilServicio getPermisoPorPerfilServicio()
    {
        return permisoPorPerfilServicio;
    }
    
    public void setPermisoPorPerfilServicio(PermisoPorPerfilServicio permisoPorPerfilServicio)
    {
        this.permisoPorPerfilServicio = permisoPorPerfilServicio;
    }
    
    public XSSFWorkbook getWorkBook()
    {
        return workBook;
    }
    
    public void setWorkBook(XSSFWorkbook workBook)
    {
        this.workBook = workBook;
    }
}
    // </editor-fold>
