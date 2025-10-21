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
 * Cliente extiende Persona. Representa a la persona que solicita el servicio.
 */
// Clase Cliente que hereda de Persona
public class Cliente extends Persona {

    // Constructor que llama al constructor de la superclase Persona
    public Cliente(String nombre, String telefono, String correo) {
        super(nombre, telefono, correo);
    }

    // Método adicional si quieres agregar comportamiento especial
    public void mostrarCliente() {
        System.out.println("=== Información del Cliente ===");
        super.mostrarInfo();
    }
}