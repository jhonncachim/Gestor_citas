/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;

import java.time.LocalDateTime;

import java.util.ArrayList;

/**
 *
 * @author ASUS VIVOBOOK
 */  


/**
 * Clase Agenda: mantiene una lista de citas y proporciona
 * operaciones para agregar y listar citas.
 */
public class Agenda {
    // Colección que almacena las citas (ArrayList)
    private final ArrayList<Cita> citas;

    public Agenda() {
        citas = new ArrayList<>();
    }

    /**
     * Agrega una cita validando que no haya otra cita del mismo profesional
     * exactamente en la misma fecha y hora. Lanza Exception si hay conflicto.
     * @param c
     * @throws java.lang.Exception
     */
    public void agregarCita(Cita c) throws Exception {
        for (Cita x : citas) {
            // Comprobamos si el profesional es el mismo y la fechaHora coincide
            if (x.getProfesional().getId().equals(c.getProfesional().getId()) &&
                x.getFechaHora().equals(c.getFechaHora())) {
                throw new Exception("⚠ El profesional ya tiene una cita en ese horario.");
            }
        }
        // Si no hay conflicto, la añadimos
        citas.add(c);
    }

    // Muestra por consola todas las citas registradas
    public void listarCitas() {
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
        } else {
            for (Cita c : citas) {
                System.out.println(c);
            }
        }
    }
}