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
    private final String id;
    private final String nombre;
    private final double precio;
    private final int duracionMin; // duración en minutos

    // Constructor
    public Servicio(String id, String nombre, double precio, int duracionMin) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.duracionMin = duracionMin;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getDuracionMin() { return duracionMin; }

    // toString para mostrar servicio con precio y duración
    @Override
    public String toString() {
        return id + " - " + nombre + " ($" + precio + ", " + duracionMin + " min)";
    }
}
