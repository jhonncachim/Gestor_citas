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
public class Cliente extends Persona {// herencia 

    // Constructor que llama al constructor de la superclase Persona
    public Cliente(int id, String nombre, String telefono, String correo) {
        super(id, nombre, telefono, correo);
 }

    // Mostrar información del cliente
    public void mostrarCliente() {
        System.out.println("=== Información del Cliente ===");
        super.mostrarInfo();
    }
}