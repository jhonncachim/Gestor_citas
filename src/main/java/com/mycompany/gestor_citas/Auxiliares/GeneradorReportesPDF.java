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
    private static final String RUTA_LOGO =
            "C:\\Users\\ASUS VIVOBOOK\\Documents\\reser.png";

    private static Image cargarLogo() {
        try {
            Image img = Image.getInstance(RUTA_LOGO);
            img.scaleToFit(90, 90);
            img.setAlignment(Element.ALIGN_LEFT);
            return img;
        } catch (Exception e) {
            System.out.println("⚠ No se pudo cargar el logo.");
            return null;
        }
    }

    private static Paragraph tituloCentrado(String texto) {
        Paragraph p = new Paragraph(texto,
                new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(15);
        return p;
    }

    private static PdfPCell celdaHeader(String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt,
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        c.setBackgroundColor(new BaseColor(40, 80, 150));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setPadding(6);
        return c;
    }

    private static PdfPCell celdaNormal(String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        return c;
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

            // ENCABEZADO
            PdfPTable header = new PdfPTable(2);
            header.setWidthPercentage(100);
            header.setWidths(new float[]{1.2f, 2f});

            Image logo = cargarLogo();
            if (logo != null) {
                PdfPCell logoCelda = new PdfPCell(logo);
                logoCelda.setBorder(Rectangle.NO_BORDER);
                header.addCell(logoCelda);
            } else header.addCell("");

            PdfPCell infoNegocio = new PdfPCell();
            infoNegocio.setBorder(Rectangle.NO_BORDER);
            infoNegocio.addElement(new Paragraph("ReservaPro",
                    new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));
            infoNegocio.addElement(new Paragraph("Dirección: --"));
            infoNegocio.addElement(new Paragraph("Teléfono: --"));
            infoNegocio.addElement(new Paragraph("Correo: --"));
            header.addCell(infoNegocio);

            doc.add(header);
            doc.add(new Paragraph("\n"));

            // TITULO
            doc.add(tituloCentrado("FACTURA"));
            doc.add(new LineSeparator());
            doc.add(new Paragraph("\n"));

            // INFO CLIENTE
            PdfPTable info = new PdfPTable(2);
            info.setWidthPercentage(100);

            PdfPCell cliente = new PdfPCell();
            cliente.setBorder(Rectangle.NO_BORDER);
            cliente.addElement(new Paragraph("Cliente:",
                    new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            cliente.addElement(new Paragraph(factura.getCliente().getNombre()));
            cliente.addElement(new Paragraph("Tel: " + factura.getCliente().getTelefono()));

            PdfPCell datosFactura = new PdfPCell();
            datosFactura.setBorder(Rectangle.NO_BORDER);
            datosFactura.addElement(new Paragraph("N° Factura: " + factura.getId()));
            datosFactura.addElement(new Paragraph("Fecha: " + java.time.LocalDate.now()));
            datosFactura.addElement(new Paragraph("Profesional: " + factura.getProfesional().getNombre()));

            info.addCell(cliente);
            info.addCell(datosFactura);
            doc.add(info);
            doc.add(new Paragraph("\n"));

            // TABLA PRINCIPAL
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{2.5f, 1f, 1f, 1f});

            tabla.addCell(celdaHeader("Descripción"));
            tabla.addCell(celdaHeader("Cantidad"));
            tabla.addCell(celdaHeader("Precio"));
            tabla.addCell(celdaHeader("Importe"));

            tabla.addCell(celdaNormal(factura.getServicio().getNombre()));
            tabla.addCell(celdaNormal("1"));
            tabla.addCell(celdaNormal("$ " + factura.getPrecio()));
            tabla.addCell(celdaNormal("$ " + factura.getTotal()));

            doc.add(tabla);
            doc.add(new Paragraph("\n"));

            // TOTALES
            PdfPTable totales = new PdfPTable(2);
            totales.setWidthPercentage(40);
            totales.setHorizontalAlignment(Element.ALIGN_RIGHT);

            double iva = factura.getTotal() * 0.19;

            totales.addCell(noBorde("Subtotal:"));
            totales.addCell(noBorde("$ " + factura.getPrecio()));
            totales.addCell(noBorde("IVA (19%):"));
            totales.addCell(noBorde("$ " + iva));
            totales.addCell(noBorde("TOTAL:", true));
            totales.addCell(noBorde("$ " + factura.getTotal(), true));

            doc.add(totales);

            doc.add(new Paragraph("\n\n"));
            doc.add(new Paragraph("Firma del profesional:\n\n________________________"));

            doc.close();
            System.out.println("Factura creada → " + ruta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PdfPCell noBorde(String txt) {
        PdfPCell c = new PdfPCell(new Phrase(txt));
        c.setBorder(Rectangle.NO_BORDER);
        return c;
    }

    private static PdfPCell noBorde(String txt, boolean bold) {
        Font f = bold ?
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD) :
                new Font(Font.FontFamily.HELVETICA, 12);

        PdfPCell c = new PdfPCell(new Phrase(txt, f));
        c.setBorder(Rectangle.NO_BORDER);
        return c;
    }

    // ================== 2. CONSOLIDADO CLIENTES ====================

    public static void generarConsolidadoClientes(List<Cliente> clientes) {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) carpeta.mkdir();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Reportes/Consolidado_Clientes.pdf"));
            doc.open();

            Image logo = cargarLogo();
            if (logo != null) doc.add(logo);

            doc.add(tituloCentrado("CONSOLIDADO DE CLIENTES"));

            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);

            tabla.addCell(celdaHeader("ID"));
            tabla.addCell(celdaHeader("Nombre"));
            tabla.addCell(celdaHeader("Teléfono"));

            for (Cliente c : clientes) {
                tabla.addCell(celdaNormal(String.valueOf(c.getId())));
                tabla.addCell(celdaNormal(c.getNombre()));
                tabla.addCell(celdaNormal(c.getTelefono()));
            }

            doc.add(tabla);
            doc.close();

        } catch (Exception e) { e.printStackTrace(); }
    }

    // ================= CONSOLIDADO PROFESIONALES ===================

    public static void generarConsolidadoProfesionales(List<Profesional> profesionales) {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) carpeta.mkdir();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Reportes/Consolidado_Profesionales.pdf"));
            doc.open();

            Image logo = cargarLogo();
            if (logo != null) doc.add(logo);

            doc.add(tituloCentrado("CONSOLIDADO DE PROFESIONALES"));

            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);

            tabla.addCell(celdaHeader("ID"));
            tabla.addCell(celdaHeader("Nombre"));
            tabla.addCell(celdaHeader("Especialidad"));

            for (Profesional p : profesionales) {
                tabla.addCell(celdaNormal(String.valueOf(p.getId())));
                tabla.addCell(celdaNormal(p.getNombre()));
                tabla.addCell(celdaNormal(p.getEspecialidad()));
            }

            doc.add(tabla);
            doc.close();

        } catch (Exception e) { e.printStackTrace(); }
    }

    // =================== REPORTE GENERAL SERVICIOS =================

    public static void generarReporteGeneralServicios(List<Servicio> servicios) {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) carpeta.mkdir();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Reportes/Reporte_Servicios.pdf"));
            doc.open();

            Image logo = cargarLogo();
            if (logo != null) doc.add(logo);

            doc.add(tituloCentrado("REPORTE DE SERVICIOS"));

            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);

            tabla.addCell(celdaHeader("ID"));
            tabla.addCell(celdaHeader("Servicio"));
            tabla.addCell(celdaHeader("Precio"));

            for (Servicio s : servicios) {
                tabla.addCell(celdaNormal(String.valueOf(s.getId())));
                tabla.addCell(celdaNormal(s.getNombre()));
                tabla.addCell(celdaNormal("$ " + s.getPrecio()));
            }

            doc.add(tabla);
            doc.close();

        } catch (Exception e) { e.printStackTrace(); }
    }

    // ======================= REPORTE CITAS ========================

    public static void generarReporteGeneralCitas(List<Cita> citas) {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) carpeta.mkdir();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Reportes/Reporte_Citas.pdf"));
            doc.open();

            Image logo = cargarLogo();
            if (logo != null) doc.add(logo);

            doc.add(tituloCentrado("REPORTE DE CITAS"));

            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);

            tabla.addCell(celdaHeader("ID"));
            tabla.addCell(celdaHeader("Cliente"));
            tabla.addCell(celdaHeader("Profesional"));
            tabla.addCell(celdaHeader("Servicio"));
            tabla.addCell(celdaHeader("Fecha/Hora"));

            for (Cita cita : citas) {
                tabla.addCell(celdaNormal(String.valueOf(cita.getId())));
                tabla.addCell(celdaNormal(cita.getCliente().getNombre()));
                tabla.addCell(celdaNormal(cita.getProfesional().getNombre()));
                tabla.addCell(celdaNormal(cita.getServicio().getNombre()));
                tabla.addCell(celdaNormal(cita.getFechaHora().toString()));
            }

            doc.add(tabla);
            doc.close();

        } catch (Exception e) { e.printStackTrace(); }
    }

}