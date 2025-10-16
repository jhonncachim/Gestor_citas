/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;

/**
 *
 * @author ASUS VIVOBOOK
 */
import java.time.LocalDateTime;

/**
 * Clase Cita: asocia Cliente, Profesional, Servicio y fecha/hora.
 * Usa LocalDateTime para manejar fecha y hora de forma correcta.
 */
public class Cita {
    private final String id;
    private final Cliente cliente;
    private final Profesional profesional;
    private final Servicio servicio;
    private final LocalDateTime fechaHora;
    private String estado; // ej. "Programada", "Cancelada"

    // Constructor que inicializa la cita y su estado por defecto
    public Cita(String id, Cliente cliente, Profesional profesional, Servicio servicio, LocalDateTime fechaHora) {
        this.id = id;
        this.cliente = cliente;
        this.profesional = profesional;
        this.servicio = servicio;
        this.fechaHora = fechaHora;
        this.estado = "Programada";
    }

    // Getters
    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Profesional getProfesional() { return profesional; }
    public Servicio getServicio() { return servicio; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getEstado() { return estado; }

    // Método para cancelar la cita (cambia el estado)
    public void cancelar() { this.estado = "Cancelada"; }

    // toString para mostrar la cita en formato legible
    @Override
    public String toString() {
        return "Cita #" + id + " | Cliente: " + cliente.getNombre() +
               " | Profesional: " + profesional.getNombre() +
               " | Servicio: " + servicio.getNombre() +
               " | Fecha: " + fechaHora + " | Estado: " + estado;
    }
}