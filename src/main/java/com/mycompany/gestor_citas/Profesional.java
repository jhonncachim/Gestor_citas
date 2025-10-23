/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;

/**
 *
 * @author ASUS VIVOBOOK
 */
import java.util.ArrayList;

/**
 * Profesional extiende Persona y tiene una lista de servicios que ofrece.
 * Aquí usamos composición: el profesional "tiene" servicios.
 */
public class Profesional extends Persona { // herencia 
    private String especialidad; 
    private final ArrayList<Servicio> servicios; // Composicion

    public Profesional(int id, String nombre, String telefono, String correo, String especialidad) {
        super(id, nombre, telefono, correo);
        this.especialidad = especialidad;
        this.servicios = new ArrayList<>();// Composicion
}

    // Agregar servicio al profesional
    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
    }

    // Mostrar información completa del profesional
    public void mostrarProfesional() {
        System.out.println("=== Información del Profesional ===");
        super.mostrarInfo();
        System.out.println("Especialidad: " + especialidad);
        System.out.println("Servicios ofrecidos:");
        for (Servicio s : servicios) {
            System.out.println(" - " + s.getNombre() + " (" + s.getDuracion() + " min, $" + s.getPrecio() + ")");
        }
    }

    // Getters y Setters
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public ArrayList<Servicio> getServicios() { return servicios; }
}