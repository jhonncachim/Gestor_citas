/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;

/**
 *
 * @author ASUS VIVOBOOK
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class GeneradorReportesPDF {

    public static void generarConsolidadoClientes(List<Cita> citas) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("consolidado_clientes.pdf"));
            doc.open();
            doc.add(new Paragraph("CONSOLIDADO DE CLIENTES CON SUS SERVICIOS"));
            doc.add(new Paragraph(" "));

            for (Cita c : citas) {
                doc.add(new Paragraph("Cliente: " + c.getCliente().getNombre()));
                doc.add(new Paragraph("Servicio: " + c.getServicio().getNombre()));
                doc.add(new Paragraph("Profesional: " + c.getProfesional().getNombre()));
                doc.add(new Paragraph("Fecha: " + c.getFecha()));
                doc.add(new Paragraph("Costo: " + c.getServicio().getPrecio()));
                doc.add(new Paragraph(" "));
            }

            doc.close();
            System.out.println("Reporte consolidado de clientes generado.");
        } catch (DocumentException | FileNotFoundException e) {
        }
    }

    public static void generarConsolidadoProfesionales(List<Cita> citas) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("consolidado_profesionales.pdf"));
            doc.open();
            doc.add(new Paragraph("CONSOLIDADO DE PROFESIONALES CON SUS SERVICIOS"));
            doc.add(new Paragraph(" "));

            for (Cita c : citas) {
                doc.add(new Paragraph("Profesional: " + c.getProfesional().getNombre()));
                doc.add(new Paragraph("Cliente: " + c.getCliente().getNombre()));
                doc.add(new Paragraph("Servicio: " + c.getServicio().getNombre()));
                doc.add(new Paragraph("Fecha: " + c.getFecha()));
                doc.add(new Paragraph("Valor: " + c.getServicio().getPrecio()));
                doc.add(new Paragraph(" "));
            }

            doc.close();
            System.out.println("Reporte consolidado de profesionales generado.");
        } catch (DocumentException | FileNotFoundException e) {
        }
    }

    public static void generarReporteGeneralServicios(List<Cita> citas) {
        try {
            Document doc = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(doc, new FileOutputStream("reporte_servicios_general.pdf"));
            doc.open();
            doc.add(new Paragraph("REPORTE GENERAL DE SERVICIOS"));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Fecha");
            tabla.addCell("Cliente");
            tabla.addCell("Profesional");
            tabla.addCell("Servicio");
            tabla.addCell("Precio");

            double totalGeneral = 0;

            for (Cita c : citas) {
                tabla.addCell(c.getFecha());
                tabla.addCell(c.getCliente().getNombre());
                tabla.addCell(c.getProfesional().getNombre());
                tabla.addCell(c.getServicio().getNombre());
                tabla.addCell(String.valueOf(c.getServicio().getPrecio()));
                totalGeneral += c.getServicio().getPrecio();
            }

            doc.add(tabla);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("TOTAL GENERAL: $" + totalGeneral));

            doc.close();
            System.out.println("Reporte general de servicios generado.");
        } catch (DocumentException | FileNotFoundException e) {
        }
    }
}