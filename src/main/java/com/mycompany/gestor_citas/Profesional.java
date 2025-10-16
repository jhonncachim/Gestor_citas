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
public class Profesional extends Persona {
    // Lista de servicios que ofrece el profesional
    private ArrayList<Servicio> servicios;

    // Constructor inicializa la lista de servicios
    public Profesional(String id, String nombre, String telefono) {
        super(id, nombre, telefono);
        this.servicios = new ArrayList<>(); // composición
    }

    // Método para agregar un servicio (encapsula la lista interna)
    public void agregarServicio(Servicio s) {
        servicios.add(s);
    }

    // Getter para acceder a los servicios del profesional
    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    // toString para mostrar info legible del profesional
    @Override
    public String toString() {
        return id + " - " + nombre + " - " + telefono;
    }
}