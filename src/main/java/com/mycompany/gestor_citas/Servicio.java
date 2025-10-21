/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;

/**
 *
 * @author ASUS VIVOBOOK
 */
/**
 * Clase Servicio: representa un servicio que puede ofrecer un profesional,
 * con id, nombre, precio y duración (en minutos).
 */
// Clase Servicio que representa lo que ofrece el profesional
public class Servicio {
    private String nombre;
    private int duracion; // en minutos
    private double precio;

    public Servicio(String nombre, int duracion, double precio) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
    }

    // Mostrar los datos del servicio
    public void mostrarServicio() {
        System.out.println("Servicio: " + nombre + " (" + duracion + " min, $" + precio + ")");
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}