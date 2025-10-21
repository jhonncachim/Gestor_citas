/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;
import java.util.regex.Pattern;

/**
 * Clase abstracta Persona: superclase para Cliente y Profesional.
 * Se declara abstracta porque no queremos instanciar personas genéricas,
 * solo subtipos concretos (Cliente, Profesional).
 */
public abstract class Persona {
    protected int id; // ID único de la persona
    protected String nombre;// Nombre de la persona
    protected String telefono;// Telefono de la persona 
    protected String correo;// Correo de la persona 

    // Constructor
    public Persona(int id, String nombre, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        // Validamos el correo con expresión regular
        if (validarCorreo(correo)) {
            this.correo = correo;
        } else {
            this.correo = "correo_invalido@desconocido.com";
        }
    }

    // Mostrar información de la persona
    public void mostrarInfo() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Correo: " + correo);
    }

    // Validación de correo
    private boolean validarCorreo(String correo) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, correo);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { 
        if (validarCorreo(correo)) this.correo = correo;
    }
}
