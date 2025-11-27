/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Cita.java
package com.mycompany.gestor_citas;

import com.itextpdf.text.pdf.PdfPCell;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
  Clase Cita:
  - Representa una cita entre un cliente y un profesional por un servicio.
  - Mantiene fecha/hora (LocalDateTime), estado (activa/atendida).
  - Incluye métodos para cancelar, marcar atendida, mostrar por consola y serializar a CSV.
*/
public class Cita {
    private final int id;
    private final Cliente cliente;
    private final Profesional profesional;
    private final Servicio servicio;
    private final LocalDateTime fechaHora;
    private boolean activa;
    private boolean atendida;

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Constructor principal (usa objetos completos)
    public Cita(int id, Cliente cliente, Profesional profesional, Servicio servicio, LocalDateTime fechaHora) {
        this.id = id;
        this.cliente = cliente;
        this.profesional = profesional;
        this.servicio = servicio;
        this.fechaHora = fechaHora;
        this.activa = true;
    }

    // Constructor alternativo (útil solo para pruebas o interfaces que pasen strings)
    // Crea objetos mínimos para cliente/profesional/servicio.
    public Cita(int id, String cliente, String profesional, String servicio, String fecha) {
        this.id = id;
        this.cliente = new Cliente(0, cliente, "", "");
        this.profesional = new Profesional(0, profesional, "", "", "");
        this.servicio = new Servicio(0, servicio, 0,0.0);
        this.fechaHora = LocalDateTime.now();
        this.activa = true;
        this.atendida = false;
    }

    // Marca la cita como cancelada (activa = false)
    public void cancelar() {
        this.activa = false;
        System.out.println("La cita con ID " + id + " ha sido cancelada correctamente.");
    }

    // Indica si ya fue atendida
    public boolean isAtendida() {
        return atendida;
    }

    // Marca la cita como atendida
    public void marcarAtendida() {
        this.atendida = true;
    }

    // Imprime en consola los datos principales (útil para debug)
    public void mostrarCita() {
        System.out.println("=== Detalle de la Cita ===");
        System.out.println("ID Cita: " + id);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Profesional: " + profesional.getNombre());
        System.out.println("Servicio: " + servicio.getNombre());
        System.out.println("Fecha y hora: " + fechaHora.format(FORMATO));
        System.out.println("Estado: " + (activa ? "Activa" : "Cancelada"));
        System.out.println("--------------------------");
    }

    // Getters básicos usados en paneles y reportes
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Profesional getProfesional() { return profesional; }
    public Servicio getServicio() { return servicio; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public boolean isActiva() { return activa; }

    // Cancela cita (alias)
    public void cancelarCita() { this.activa = false; }

    // Serializa la cita a una línea CSV para guardar en archivo
    public String toCSV() {
        return id + ";" + cliente.getId() + ";" + profesional.getId() + ";" + servicio.getId() + ";" +
               fechaHora.format(FORMATO) + ";" + activa;
    }

    // Crea una Cita desde una línea CSV (lee ids y busca objetos en agenda)
    public static Cita fromCSV(String linea, Agenda agenda) {
        try {
            String[] datos = linea.split(";");
            int id = Integer.parseInt(datos[0]);
            int idCliente = Integer.parseInt(datos[1]);
            int idProfesional = Integer.parseInt(datos[2]);
            int idServicio = Integer.parseInt(datos[3]);
            LocalDateTime fecha = LocalDateTime.parse(datos[4], FORMATO);
            boolean activa = Boolean.parseBoolean(datos[5]);

            Cliente c = agenda.buscarClientePorId(idCliente);
            Profesional p = agenda.buscarProfesionalPorId(idProfesional);
            Servicio s = agenda.buscarServicioPorId(idServicio);

            Cita cita = new Cita(id, c, p, s, fecha);
            if (!activa) cita.cancelarCita();
            return cita;
        } catch (Exception e) {
            System.out.println("Error al leer cita desde CSV: " + e.getMessage());
            return null;
        }
    }

    // Método placeholder (no implementado) — puede eliminarse o implementarse si se necesita en PDFs
    PdfPCell getFecha() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}