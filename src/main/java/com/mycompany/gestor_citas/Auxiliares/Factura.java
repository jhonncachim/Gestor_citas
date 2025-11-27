/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas.Auxiliares;

import com.mycompany.gestor_citas.Cliente;
import com.mycompany.gestor_citas.Profesional;
import com.mycompany.gestor_citas.Servicio;

import java.time.LocalDateTime;

public class Factura {

    private int id;
    private Cliente cliente;
    private Profesional profesional;
    private Servicio servicio;
    private LocalDateTime fechaCita;
    private double precio;
    private double total;

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

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Profesional getProfesional() { return profesional; }
    public Servicio getServicio() { return servicio; }
    public LocalDateTime getFechaCita() { return fechaCita; }
    public double getPrecio() { return precio; }
    public double getTotal() { return total; }
}