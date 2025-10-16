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
public class Cliente extends Persona {
    // Constructor que llama al constructor de la superclase
    public Cliente(String id, String nombre, String telefono) {
        super(id, nombre, telefono);
    }

    // toString para imprimir información legible del cliente
    @Override
    public String toString() {
        return id + " - " + nombre + " - " + telefono;
    }
}
