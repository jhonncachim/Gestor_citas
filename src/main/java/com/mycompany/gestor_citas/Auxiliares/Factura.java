/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */// Factura.java (package com.mycompany.gestor_citas.Auxiliares)
package com.mycompany.gestor_citas.Auxiliares;

import com.mycompany.gestor_citas.Cliente;
import com.mycompany.gestor_citas.Profesional;
import com.mycompany.gestor_citas.Servicio;

import java.time.LocalDateTime;

/*
  DTO simple para representar una factura generada desde una cita.
  - Contiene cliente, profesional, servicio, fecha y montos.
  - Usada por VentanaCitas y GeneradorReportesPDF.
*/
public class Factura {

    private final int id;
    private final Cliente cliente;
    private final Profesional profesional;
    private final Servicio servicio;
    private final LocalDateTime fechaCita;
    private final double precio;
    private final double total;

    public Factura(int id, Cliente cliente, Profesional profesional,
                   Servicio servicio, LocalDateTime fechaCita,
                   double precio, double total) {

        this.id = id;
        this.cliente = cliente;
        this.profesional = profesional;
        this.servicio = servicio;
        this.fechaCita = fechaCita;
        this.precio = precio;
        this.total = total;
    }

    // Getters sencillos para usar en el generador de PDF
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Profesional getProfesional() { return profesional; }
    public Servicio getServicio() { return servicio; }
    public LocalDateTime getFechaCita() { return fechaCita; }
    public double getPrecio() { return precio; }
    public double getTotal() { return total; }
}