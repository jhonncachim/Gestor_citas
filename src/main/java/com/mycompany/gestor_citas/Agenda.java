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
 * Clase Agenda: gestiona todas las citas, clientes, profesionales y servicios.
 * 
 * Relación principal: Agregación
 * La Agenda contiene colecciones (ArrayList) de objetos de otras clases.
 * Es decir, los objetos Cliente, Profesional, Servicio y Cita pueden existir
 * sin depender totalmente de la Agenda, pero esta los agrupa y los gestiona.
 */

public class Agenda {

    // La Agenda tiene listas de objetos, pero esos objetos pueden existir fuera de la Agenda.
    private final ArrayList<Cliente> clientes;
    private final ArrayList<Profesional> profesionales;
    private final ArrayList<Servicio> servicios;
    private final ArrayList<Cita> citas;

    // Constructor
    public Agenda() {
        // Aquí se crean las listas donde se almacenarán los objetos agregados.
        clientes = new ArrayList<>();
        profesionales = new ArrayList<>();
        servicios = new ArrayList<>();
        citas = new ArrayList<>();

        // Cargar los datos desde los archivos CSV al iniciar el sistema
        GestorArchivos.cargarTodo(this);
    }

    // 🔹 Nuevo método para cancelar una cita por ID
    public boolean cancelarCitaPorId(int id) {
        for (Cita c : citas) {
            if (c.getId() == id) {
                if (!c.isActiva()) {
                    System.out.println("⚠️ La cita ya estaba cancelada.");
                    return false;
                }
                c.cancelar();
                GestorArchivos.guardarCitas(citas);
                return true;
            }
        }
        System.out.println(" No se encontró una cita con ese ID.");
        return false;
    }

    // Método para agregar un cliente a la Agenda (sin que dependa completamente de ella)
    public void agregarCliente(Cliente c) {
        clientes.add(c);
        GestorArchivos.guardarClientes(clientes);
    }

    // Agrega un profesional existente a la lista de la Agenda.
    public void agregarProfesional(Profesional p) {
        profesionales.add(p);
        GestorArchivos.guardarProfesionales(profesionales);
    }

    // Agrega un servicio disponible a la lista de servicios.
    public void agregarServicio(Servicio s) {
        servicios.add(s);
        GestorArchivos.guardarServicios(servicios);
    }

    //  ASOCIACIÓN + AGREGACIÓN:
    // Aquí se crea una nueva cita que asocia Cliente, Profesional y Servicio.
    // Luego esa cita se agrega (agregación) a la lista de citas de la Agenda.
    public void crearCita(int id, Cliente cliente, Profesional profesional, Servicio servicio, LocalDateTime fechaHora) {
        Cita cita = new Cita(id, cliente, profesional, servicio, fechaHora);
        citas.add(cita); //  agregación (la cita se guarda en la lista de la Agenda)
        System.out.println(" Cita creada correctamente para " + cliente.getNombre());
        GeneradorPDF.generarComprobante(cita);

        // Guardar la cita en archivo
        GestorArchivos.guardarCitas(citas);
    }

    // Método para guardar todo manualmente (por ejemplo, al salir del sistema)
    public void guardarTodo() {
        GestorArchivos.guardarTodo(this);
    }

    // Métodos para buscar objetos por ID
    public Cliente buscarClientePorId(int id) {
        for (Cliente c : clientes)
            if (c.getId() == id) return c;
        return null;
    }

    public Profesional buscarProfesionalPorId(int id) {
        for (Profesional p : profesionales)
            if (p.getId() == id) return p;
        return null;
    }

    public Servicio buscarServicioPorId(int id) {
        for (Servicio s : servicios)
            if (s.getId() == id) return s;
        return null;
    }

    public Cita buscarCitaPorId(int id) {
        for (Cita c : citas)
            if (c.getId() == id) return c;
        return null;
    }

    // Muestra todas las citas de la Agenda
    public void mostrarCitas() {
        System.out.println("=== Todas las Citas ===");
        for (Cita c : citas) {
            c.mostrarCita();
        }
    }

    // Getters para acceder a las listas desde otras clases
    public ArrayList<Cita> getCitas() { return citas; }
    public ArrayList<Cliente> getClientes() { return clientes; }
    public ArrayList<Profesional> getProfesionales() { return profesionales; }
    public ArrayList<Servicio> getServicios() { return servicios; }

    public void agregarCita(Cita cita) {
        citas.add(cita);
        GestorArchivos.guardarCitas(citas);
    }

    public void listarCitas() {
        mostrarCitas();
    }
}