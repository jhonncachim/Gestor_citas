/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas.Auxiliares;

import java.io.FileOutputStream;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mycompany.gestor_citas.Cita;
import com.mycompany.gestor_citas.Cliente;
import com.mycompany.gestor_citas.Profesional;
import java.io.FileNotFoundException;

/**
 * Clase que genera los reportes PDF del sistema
 */
public class GeneradorReportesPDF {

    // Consolidado de clientes
    public static void generarConsolidadoClientes(List<Cliente> clientes) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Consolidado_Clientes.pdf"));
            doc.open();

            doc.add(new Paragraph("Consolidado de Clientes"));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(4);
            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Telefono");
            tabla.addCell("Correo");

            for (Cliente c : clientes) {
                tabla.addCell(String.valueOf(c.getId()));
                tabla.addCell(c.getNombre());
                tabla.addCell(c.getTelefono());
                tabla.addCell(c.getCorreo());
            }

            doc.add(tabla);
            doc.close();
            System.out.println("PDF Consolidado_Clientes generado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al generar Consolidado_Clientes: " + e.getMessage());
        }
    }

    // Consolidado de profesionales
    public static void generarConsolidadoProfesionales(List<Profesional> profesionales) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Consolidado_Profesionales.pdf"));
            doc.open();

            doc.add(new Paragraph("Consolidado de Profesionales"));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Telefono");
            tabla.addCell("Correo");
            tabla.addCell("Especialidad");

            for (Profesional p : profesionales) {
                tabla.addCell(String.valueOf(p.getId()));
                tabla.addCell(p.getNombre());
                tabla.addCell(p.getTelefono());
                tabla.addCell(p.getCorreo());
                tabla.addCell(p.getEspecialidad());
            }

            doc.add(tabla);
            doc.close();
            System.out.println("PDF Consolidado_Profesionales generado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al generar Consolidado_Profesionales: " + e.getMessage());
        }
    }

    // Reporte general de servicios (requiere citas)
    public static void generarReporteGeneralServicios(List<Cita> citas) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Reporte_Servicios.pdf"));
            doc.open();

            doc.add(new Paragraph("Reporte General de Servicios"));
            doc.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Fecha");
            tabla.addCell("Cliente");
            tabla.addCell("Profesional");
            tabla.addCell("Servicio");
            tabla.addCell("Precio");

            for (Cita cita : citas) {
                tabla.addCell(String.valueOf(cita.getFechaHora()));
                tabla.addCell(cita.getCliente().getNombre());
                tabla.addCell(cita.getProfesional().getNombre());
                tabla.addCell(cita.getServicio().getNombre());
                tabla.addCell(String.valueOf(cita.getServicio().getPrecio()));
            }

            doc.add(tabla);
            doc.close();
            System.out.println("PDF Reporte_Servicios generado correctamente.");

        } catch (DocumentException | FileNotFoundException e) {
            System.out.println("Error al generar Reporte_Servicios: " + e.getMessage());
        }
    }

    
}
