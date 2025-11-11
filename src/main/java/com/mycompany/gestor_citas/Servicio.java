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
public class Servicio {
    private int id; // ID único del servicio
    private String nombre;
    private int duracion; // en minutos
    private double precio;

    public Servicio(int id, String nombre, int duracion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
    }

    // Mostrar los datos del servicio
    public void mostrarServicio() {
        System.out.println("ID: " + id + " | Servicio: " + nombre + " (" + duracion + " min, $" + precio + ")");
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}
