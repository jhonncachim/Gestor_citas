/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ASUS VIVOBOOK
 */
/**
 * Clase Cita: representa una cita entre un Cliente y un Profesional.
 * Usa LocalDateTime para manejar fecha y hora.
 */
public class Cita {
    private int id; // ID único de la cita
    private Cliente cliente;
    private Profesional profesional;
    private Servicio servicio;
    private LocalDateTime fechaHora;
    private boolean activa;

    // Formato de fecha
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Cita(int id, Cliente cliente, Profesional profesional, Servicio servicio, LocalDateTime fechaHora) {
        this.id = id;
        this.cliente = cliente;
        this.profesional = profesional;
        this.servicio = servicio;
        this.fechaHora = fechaHora;
        this.activa = true;
    }

    // Mostrar detalles de la cita
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

    // Getters y Setters
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Profesional getProfesional() { return profesional; }
    public Servicio getServicio() { return servicio; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public boolean isActiva() { return activa; }

    public void cancelarCita() { this.activa = false; }

    // Convertir a texto CSV
    public String toCSV() {
        return id + ";" + cliente.getId() + ";" + profesional.getId() + ";" + servicio.getId() + ";" +
               fechaHora.format(FORMATO) + ";" + activa;
    }

    // Leer desde CSV
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
}