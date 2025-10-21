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
        Scanner sc = new Scanner(System.in);

        // Listas que simulan una base de datos en memoria
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Profesional> profesionales = new ArrayList<>();
        Agenda agenda = new Agenda();

        int opcion;

        do {
            System.out.println("\n    GESTOR DE CITAS    ");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Registrar Profesional");
            System.out.println("3. Agregar Servicio a Profesional");
            System.out.println("4. Crear Cita");
            System.out.println("5. Listar Citas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> {
                    // Registro de cliente con id, nombre, teléfono y correo
                    System.out.print("ID cliente: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Telefono: ");
                    String tel = sc.nextLine();
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();

                    clientes.add(new Cliente(id, nombre, tel, correo));
                    System.out.println(" Cliente registrado correctamente.");
                }

                case 2 -> {
                    // Registro de profesional con especialidad
                    System.out.print("ID profesional: ");
                    int idProf = Integer.parseInt(sc.nextLine());
                    System.out.print("Nombre: ");
                    String nombreP = sc.nextLine();
                    System.out.print("Telefono: ");
                    String telP = sc.nextLine();
                    System.out.print("Correo: ");
                    String correoP = sc.nextLine();
                    System.out.print("Especialidad: ");
                    String especialidad = sc.nextLine();

                    profesionales.add(new Profesional(idProf, nombreP, telP, correoP, especialidad));
                    System.out.println(" Profesional registrado correctamente.");
                }

                case 3 -> {
                    // Agregar un servicio a un profesional
                    System.out.print("ID profesional: ");
                    int idP = Integer.parseInt(sc.nextLine());
                    Profesional p = buscarProfesional(profesionales, idP);

                    if (p != null) {
                        System.out.print("ID servicio: ");
                        int idServ = Integer.parseInt(sc.nextLine());
                        System.out.print("Nombre servicio: ");
                        String nomServ = sc.nextLine();
                        System.out.print("Precio: ");
                        double precio = Double.parseDouble(sc.nextLine());
                        System.out.print("Duración (minutos): ");
                        int duracion = Integer.parseInt(sc.nextLine());

                        p.agregarServicio(new Servicio(idServ, nomServ, (int) precio, duracion));
                        System.out.println(" Servicio agregado al profesional.");
                    } else {
                        System.out.println(" Profesional no encontrado.");
                    }
                }

                case 4 -> {
                    // Crear cita (cliente + profesional + servicio + fecha/hora)
                    try {
                        System.out.print("ID cita: ");
                        int idCita = Integer.parseInt(sc.nextLine());

                        System.out.print("ID cliente: ");
                        int idCli = Integer.parseInt(sc.nextLine());
                        Cliente c = buscarCliente(clientes, idCli);

                        System.out.print("ID profesional: ");
                        int idPr = Integer.parseInt(sc.nextLine());
                        Profesional p = buscarProfesional(profesionales, idPr);

                        if (c != null && p != null && !p.getServicios().isEmpty()) {
                            System.out.println("Servicios disponibles:");
                            for (Servicio s : p.getServicios()) System.out.println(s);

                            System.out.print("ID servicio: ");
                            int idServ = Integer.parseInt(sc.nextLine());
                            Servicio serv = buscarServicio(p, idServ);

                            if (serv != null) {
                                System.out.print("Fecha y hora (YYYY-MM-DDTHH:MM): ");
                                LocalDateTime fecha = LocalDateTime.parse(sc.nextLine());

                                Cita cita = new Cita(idCita, c, p, serv, fecha);
                                agenda.agregarCita(cita);
                                System.out.println(" Cita creada correctamente.");
                            } else {
                                System.out.println(" Servicio no encontrado.");
                            }
                        } else {
                            System.out.println(" Cliente o profesional inválido, o sin servicios disponibles.");
                        }

                    } catch (Exception e) {
                        System.out.println(" Error: " + e.getMessage());
                    }
                }

                case 5 -> {
                    // Mostrar todas las citas registradas
                    agenda.listarCitas();
                }

                case 6 -> System.out.println(" Saliendo del sistema...");

                default -> System.out.println(" Opción no válida.");
            }

        } while (opcion != 6);
    }

    // Métodos auxiliares para buscar elementos
    private static Cliente buscarCliente(ArrayList<Cliente> lista, int id) {
        for (Cliente c : lista)
            if (c.getId() == id) return c;
        return null;
    }

    private static Profesional buscarProfesional(ArrayList<Profesional> lista, int id) {
        for (Profesional p : lista)
            if (p.getId() == id) return p;
        return null;
    }

    private static Servicio buscarServicio(Profesional p, int id) {
        for (Servicio s : p.getServicios())
            if (s.getId() == id) return s;
        return null;
    }
}