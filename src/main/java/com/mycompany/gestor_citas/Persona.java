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
 * Clase abstracta Persona: superclase para Cliente y Profesional.
 * Se declara abstracta porque no queremos instanciar personas genéricas,
 * solo subtipos concretos (Cliente, Profesional).
 */
public abstract class Persona {
    // Atributos protegidos para visibilidad en subclases
    protected String id;
    protected String nombre;
    protected String telefono;

    // Constructor que inicializa los atributos básicos
    public Persona(String id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Getters (encapsulamiento)
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }

    // Setters (si en algún caso necesitamos modificar los datos)
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}