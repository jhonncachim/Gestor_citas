/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gestor_citas;

/**
 *
 * @author ASUS VIVOBOOK
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase principal App con menú por consola. Permite:
 * - Registrar clientes
 * - Registrar profesionales
 * - Agregar servicios a profesionales
 * - Crear citas (parseando LocalDateTime)
 * - Listar citas
 *
 * Comentarios explican cada paso para que lo leas y entiendas.
 */
public class Gestor_citas {
    public static void main(String[] args) {
        // Scanner para leer desde consola
        Scanner sc = new Scanner(System.in);

        // Repositorios en memoria
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Profesional> profesionales = new ArrayList<>();
        Agenda agenda = new Agenda();

        int opcion;

        // Bucle principal del menú
        do {
            System.out.println("\n    GESTOR DE CITAS    ");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Registrar Profesional");
            System.out.println("3. Agregar Servicio a Profesional");
            System.out.println("4. Crear Cita");
            System.out.println("5. Listar Citas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            // Leemos la opción elegida (número)
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> {
                    // Registrar Cliente: pedimos id, nombre y teléfono
                    System.out.print("ID cliente: ");
                    String id = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Telefono: ");
                    String tel = sc.nextLine();
                    clientes.add(new Cliente(id, nombre, tel));
                    System.out.println(" Cliente registrado.");
                }

                case 2 -> {
                    // Registrar Profesional: pedimos id, nombre y teléfono
                    System.out.print("ID profesional: ");
                    String idProf = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombreP = sc.nextLine();
                    System.out.print("Telefono: ");
                    String telP = sc.nextLine();
                    profesionales.add(new Profesional(idProf, nombreP, telP));
                    System.out.println(" Profesional registrado.");
                }

                case 3 -> {
                    // Agregar Servicio a un profesional existente
                    System.out.print("ID profesional: ");
                    String idP = sc.nextLine();
                    Profesional p = buscarProfesional(profesionales, idP);
                    if (p != null) {
                        System.out.print("ID servicio: ");
                        String idServ = sc.nextLine();
                        System.out.print("Nombre servicio: ");
                        String nomServ = sc.nextLine();
                        System.out.print("Precio: ");
                        double precio = Double.parseDouble(sc.nextLine());
                        System.out.print("Duración (min): ");
                        int dur = Integer.parseInt(sc.nextLine());
                        // Creamos el servicio y lo agregamos al profesional
                        p.agregarServicio(new Servicio(idServ, nomServ, precio, dur));
                        System.out.println(" Servicio agregado al profesional.");
                    } else {
                        System.out.println(" Profesional no encontrado.");
                    }
                }

                case 4 -> {
                    // Crear cita: elegimos cliente, profesional, servicio y fecha/hora
                    try {
                        System.out.print("ID cita: ");
                        String idCita = sc.nextLine();

                        System.out.print("ID cliente: ");
                        String idCli = sc.nextLine();
                        Cliente c = buscarCliente(clientes, idCli);

                        System.out.print("ID profesional: ");
                        String idPr = sc.nextLine();
                        Profesional p = buscarProfesional(profesionales, idPr);

                        // Verificamos que existan y que el profesional tenga servicios
                        if (c != null && p != null && !p.getServicios().isEmpty()) {
                            System.out.println("Servicios disponibles:");
                            for (Servicio s : p.getServicios()) System.out.println(s);
                            System.out.print("ID servicio: ");
                            String idServ = sc.nextLine();
                            Servicio serv = buscarServicio(p, idServ);

                            // Pedimos fecha y hora en formato ISO local (ej. 2025-10-13T18:30)
                            System.out.print("Fecha y hora (YYYY-MM-DDTHH:MM): ");
                            LocalDateTime fecha = LocalDateTime.parse(sc.nextLine());

                            // Creamos la cita y la agregamos a la agenda (puede lanzar excepción)
                            Cita cita = new Cita(idCita, c, p, serv, fecha);
                            agenda.agregarCita(cita);
                            System.out.println(" Cita registrada correctamente.");
                        } else {
                            System.out.println(" Datos invalidos o faltan servicios.");
                        }
                    } catch (Exception e) {
                        // Capturamos errores (parseo, validaciones...)
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 5 -> {
                    // Listar todas las citas en la agenda
                    agenda.listarCitas();
                }

                case 6 -> {
                    // Salir del programa
                    System.out.println("Saliendo..."); 
                }

                default -> System.out.println("Opcion no valida.");
            }

        } while (opcion != 6);
    }

    // Métodos helper para buscar entidades por su id en las listas
    private static Cliente buscarCliente(ArrayList<Cliente> lista, String id) {
        for (Cliente c : lista) if (c.getId().equals(id)) return c;
        return null;
    }

    private static Profesional buscarProfesional(ArrayList<Profesional> lista, String id) {
        for (Profesional p : lista) if (p.getId().equals(id)) return p;
        return null;
    }

    private static Servicio buscarServicio(Profesional p, String id) {
        for (Servicio s : p.getServicios()) if (s.getId().equals(id)) return s;
        return null;
    }
}
