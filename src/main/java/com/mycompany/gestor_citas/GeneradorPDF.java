/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Clase GeneradorPDF
 * ------------------
 * Genera un archivo PDF con la información de cada cita registrada.
 * 
 * Cada vez que se crea una cita en la agenda, se puede invocar este generador
 * para crear un comprobante (recibo o constancia) con los detalles.
 */
public class GeneradorPDF {

    /**
     * Genera un archivo PDF con la información de una cita específica.
     *
     * @param cita  Objeto de tipo Cita con todos los datos de la reserva.
     */
    public static void generarComprobante(Cita cita) {
        try {
            // 📁 Nombre del archivo con ID de la cita
            String nombreArchivo = "Comprobante_Cita_" + cita.getId() + ".pdf";

            // Crear documento PDF
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            // 🔹 Encabezado
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("Comprobante de Cita - ReservaPro\n\n", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            // 🔹 Datos de la cita
            Font texto = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            documento.add(new Paragraph("ID de la cita: " + cita.getId(), texto));
            documento.add(new Paragraph("Cliente: " + cita.getCliente().getNombre(), texto));
            documento.add(new Paragraph("Profesional: " + cita.getProfesional().getNombre(), texto));
            documento.add(new Paragraph("Especialidad: " + cita.getProfesional().getEspecialidad(), texto));
            documento.add(new Paragraph("Servicio: " + cita.getServicio().getNombre(), texto));
            documento.add(new Paragraph("Duración: " + cita.getServicio().getDuracion() + " minutos", texto));
            documento.add(new Paragraph("Precio: $" + cita.getServicio().getPrecio(), texto));
            documento.add(new Paragraph("Fecha y hora: " + cita.getFechaHora().toString(), texto));

            documento.add(new Paragraph("\nEstado: ACTIVA", texto));

            // 🔹 Línea final decorativa
            LineSeparator separator = new LineSeparator();
            separator.setLineColor(BaseColor.LIGHT_GRAY);
            documento.add(new Chunk(separator));
            documento.add(new Paragraph("\nGracias por confiar en ReservaPro", texto));

            documento.close();
            System.out.println(" PDF generado correctamente: " + nombreArchivo);
            Desktop.getDesktop().open(new File(nombreArchivo));

        } catch (Exception e) {
            System.out.println(" Error al generar el PDF: " + e.getMessage());
        }
    }
}