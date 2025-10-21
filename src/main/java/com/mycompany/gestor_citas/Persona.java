/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;
import java.util.regex.Pattern;
/**
 *
 * @author ASUS VIVOBOOK
 */
/**
 * Clase abstracta Persona: superclase para Cliente y Profesional.
 * Se declara abstracta porque no queremos instanciar personas genéricas,
 * solo subtipos concretos (Cliente, Profesional).
 */
// Clase abstracta base de Cliente y Profesional
public abstract class Persona {
    protected String nombre;
    protected String telefono;
    protected String correo;

    // Constructor
    public Persona(String nombre, String telefono, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        // Validamos el correo con expresión regular
        if (validarCorreo(correo)) {
            this.correo = correo;
        } else {
            this.correo = "correo_invalido@desconocido.com";
        }
    }

    // Método para mostrar información de la persona
    public void mostrarInfo() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Correo: " + correo);
    }

    // Validación simple de correo
    private boolean validarCorreo(String correo) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, correo);
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { 
        if (validarCorreo(correo)) this.correo = correo;
    }
}
