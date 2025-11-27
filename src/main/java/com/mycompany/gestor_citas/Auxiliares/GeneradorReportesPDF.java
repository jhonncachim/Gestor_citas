/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas.Auxiliares;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.mycompany.gestor_citas.Cita;
import com.mycompany.gestor_citas.Cliente;
import com.mycompany.gestor_citas.Profesional;
import com.mycompany.gestor_citas.Servicio;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class GeneradorReportesPDF {

    // ===================== LOGO ======================
    private static final String RUTA_LOGO = "C:\\Users\\ASUS VIVOBOOK\\Documents\\reser.png";

    private static Image cargarLogo() {
        try {
            Image img = Image.getInstance(RUTA_LOGO);
            img.scaleToFit(100, 100);
            img.setAlignment(Element.ALIGN_LEFT);
            return img;
        } catch (Exception e) {
            System.out.println("⚠ No se pudo cargar el logo.");
            return null;
        }
    }

    // ============================================================
    // ===================== 1. FACTURA MODERNA ====================
    // ============================================================

    public static void generarFacturaPDF(Factura factura) {

        try {
            File carpeta = new File("Facturas");
            if (!carpeta.exists()) carpeta.mkdir();

            String ruta = "Facturas/Factura_" + factura.getId() + ".pdf";

            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            // ========== ENCABEZADO ==========
            PdfPTable header = new PdfPTable(2);
            header.setWidthPercentage(100);
            header.setWidths(new float[]{1.2f, 2f});

            // Logo
            Image logo = cargarLogo();
            if (logo != null) {
                PdfPCell logoCell = new PdfPCell(logo);
                logoCell.setBorder(Rectangle.NO_BORDER);
                logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                header.addCell(logoCell);
            } else {
                header.addCell("");
            }

            // Información del negocio
            PdfPCell infoNegocio = new PdfPCell();
            infoNegocio.setBorder(Rectangle.NO_BORDER);
            infoNegocio.addElement(new Paragraph("GESTOR DE CITAS",
                    new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));
            infoNegocio.addElement(new Paragraph("Dirección: --"));
            infoNegocio.addElement(new Paragraph("Teléfono: --"));
            infoNegocio.addElement(new Paragraph("Correo: --"));
            header.addCell(infoNegocio);

            doc.add(header);
            doc.add(new Paragraph("\n"));

            // ========== TÍTULO ==========
            Paragraph titulo = new Paragraph("FACTURA",
                    new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD));
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(10);
            doc.add(titulo);

            doc.add(new LineSeparator());
            doc.add(new Paragraph("\n"));

            // ========== INFORMACIÓN DEL CLIENTE / FACTURA ==========
            PdfPTable info = new PdfPTable(2);
            info.setWidthPercentage(100);
            info.setWidths(new float[]{1f, 1f});

            PdfPCell cliente = new PdfPCell();
            cliente.setBorder(Rectangle.NO_BORDER);
            cliente.addElement(new Paragraph("FACTURA ENTREGADA A:",
                    new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            cliente.addElement(new Paragraph(factura.getCliente().getNombre()));
            cliente.addElement(new Paragraph("Tel: " + factura.getCliente().getTelefono()));

            PdfPCell datosFactura = new PdfPCell();
            datosFactura.setBorder(Rectangle.NO_BORDER);
            datosFactura.addElement(new Paragraph("N° Factura: " + factura.getId()));
            datosFactura.addElement(new Paragraph("Fecha emisión: " + java.time.LocalDate.now()));
            datosFactura.addElement(new Paragraph("Profesional: " + factura.getProfesional().getNombre()));

            info.addCell(cliente);
            info.addCell(datosFactura);

            doc.add(info);
            doc.add(new Paragraph("\n"));

            // ========== TABLA PRINCIPAL ==========
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{2.5f, 1f, 1f, 1f});

            tabla.addCell(encabezado("Descripción"));
            tabla.addCell(encabezado("Cantidad"));
            tabla.addCell(encabezado("Precio"));
            tabla.addCell(encabezado("Importe"));

            tabla.addCell(factura.getServicio().getNombre());
            tabla.addCell("1");
            tabla.addCell("$ " + factura.getPrecio());
            tabla.addCell("$ " + factura.getTotal());

            doc.add(tabla);
            doc.add(new Paragraph("\n"));

            // ========== TOTALES ==========
            PdfPTable totales = new PdfPTable(2);
            totales.setWidthPercentage(40);
            totales.setHorizontalAlignment(Element.ALIGN_RIGHT);

            totales.addCell(noBorde("Subtotal:"));
            totales.addCell(noBorde("$ " + factura.getPrecio()));

            double iva = factura.getTotal() * 0.19;

            totales.addCell(noBorde("IVA (19%):"));
            totales.addCell(noBorde("$ " + iva));

            totales.addCell(noBorde("TOTAL A PAGAR:", true));
            totales.addCell(noBorde("$ " + factura.getTotal(), true));

            doc.add(totales);

            doc.add(new Paragraph("\n\n"));

            // ========== FIRMA ==========
            Paragraph firma = new Paragraph(
                    "Firma del profesional:\n\n______________________________",
                    new Font(Font.FontFamily.HELVETICA, 11)
            );
            firma.setAlignment(Element.ALIGN_LEFT);
            doc.add(firma);

            doc.close();
            System.out.println("Factura creada → " + ruta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= UTILIDADES PARA TABLA DE FACTURA ==================

    private static PdfPCell encabezado(String text) {
        PdfPCell c = new PdfPCell(new Phrase(text,
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        c.setBackgroundColor(BaseColor.LIGHT_GRAY);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setPadding(5);
        return c;
    }

    private static PdfPCell noBorde(String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt));
        c.setBorder(Rectangle.NO_BORDER);
        c.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return c;
    }

    private static PdfPCell noBorde(String txt, boolean bold) {
        Font f = bold ?
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD) :
                new Font(Font.FontFamily.HELVETICA, 12);

        PdfPCell c = new PdfPCell(new Phrase(txt, f));
        c.setBorder(Rectangle.NO_BORDER);
        c.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return c;
    }

    // =====================================================================
    // ===================== 2. CONSOLIDADO CLIENTES ========================
    // =====================================================================

    public static void generarConsolidadoClientes(List<Cliente> clientes) {
        try {

            File carpeta = new File("Reportes");
            if (!carpeta.exists()) carpeta.mkdir();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Reportes/Consolidado_Clientes.pdf"));
            doc.open();

            Image logo = cargarLogo();
            if (logo != null) doc.add(logo);

            Paragraph titulo = new Paragraph("CONSOLIDADO DE CLIENTES",
                    new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            doc.add(titulo);

            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);

            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Teléfono");

            for (Cliente x : clientes) {
                tabla.addCell(String.valueOf(x.getId()));
                tabla.addCell(x.getNombre());
                tabla.addCell(x.getTelefono());
            }

            doc.add(tabla);
            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================================================
    // ================== 3. CONSOLIDADO PROFESIONALES =====================
    // =====================================================================

    public static void generarConsolidadoProfesionales(List<Profesional> profesionales) {
        try {

            File carpeta = new File("Reportes");
            if (!carpeta.exists()) carpeta.mkdir();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Reportes/Consolidado_Profesionales.pdf"));
            doc.open();

            Image logo = cargarLogo();
            if (logo != null) doc.add(logo);

            Paragraph titulo = new Paragraph("CONSOLIDADO DE PROFESIONALES",
                    new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            doc.add(titulo);

            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);

            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Especialidad");

            for (Profesional p : profesionales) {
                tabla.addCell(String.valueOf(p.getId()));
                tabla.addCell(p.getNombre());
                tabla.addCell(p.getEspecialidad());
            }

            doc.add(tabla);
            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================================================
    // ===================== 4. REPORTE DE SERVICIOS ========================
    // =====================================================================

    public static void generarReporteGeneralServicios(List<Servicio> servicios) {
        try {

            File carpeta = new File("Reportes");
            if (!carpeta.exists()) carpeta.mkdir();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Reportes/Reporte_Servicios.pdf"));
            doc.open();

            Image logo = cargarLogo();
            if (logo != null) doc.add(logo);

            Paragraph titulo = new Paragraph("REPORTE GENERAL DE SERVICIOS",
                    new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            doc.add(titulo);

            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);

            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Precio");

            for (Servicio s : servicios) {
                tabla.addCell(String.valueOf(s.getId()));
                tabla.addCell(s.getNombre());
                tabla.addCell(String.valueOf(s.getPrecio()));
            }

            doc.add(tabla);
            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================================================
    // ========================== 5. REPORTE CITAS ==========================
    // =====================================================================

    public static void generarReporteGeneralCitas(List<Cita> citas) {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) carpeta.mkdir();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Reportes/Reporte_Citas.pdf"));
            doc.open();

            Image logo = cargarLogo();
            if (logo != null) doc.add(logo);

            Paragraph titulo = new Paragraph("REPORTE GENERAL DE CITAS",
                    new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            doc.add(titulo);

            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);

            tabla.addCell("ID Cita");
            tabla.addCell("Cliente");
            tabla.addCell("Profesional");
            tabla.addCell("Servicio");
            tabla.addCell("Fecha/Hora");

            for (Cita c : citas) {
                tabla.addCell(String.valueOf(c.getId()));
                tabla.addCell(c.getCliente().getNombre());
                tabla.addCell(c.getProfesional().getNombre());
                tabla.addCell(c.getServicio().getNombre());
                tabla.addCell(c.getFechaHora().toString());
            }

            doc.add(tabla);
            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
