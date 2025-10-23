/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.gestor_citas;

/**
 *
 * @author ASUS VIVOBOOK
**/
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Clase principal GestorCitas.
 * Contiene un menú interactivo por consola que gestiona:
 * - Clientes
 * - Profesionales
 * - Servicios
 * - Citas
 *
 * Ahora con manejo de errores, validaciones y menú mejorado.
 */
public class Gestor_citas {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Agenda agenda = new Agenda();
        Autenticacion auth = new Autenticacion();
        
        GestorArchivos.cargarCitas(agenda);
        // 🔹 Primero, el usuario debe iniciar sesión o registrarse
        boolean acceso = false;
        while (!acceso) {
            System.out.println("\n------ SISTEMA DE ACCESO ------");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrar nuevo usuario");
            System.out.print("Seleccione una opcion: ");
            String opcionLogin = sc.nextLine();

            switch (opcionLogin) {
                case "1" -> acceso = auth.iniciarSesion(sc);
                case "2" -> auth.registrarUsuario(sc);
                default -> System.out.println("️ Opcion no valida.");
            }
        }

        // 🔹 Menú principal (solo visible si el login fue exitoso)
        int opcion;

        do {
            System.out.println("\n------ ReservaPro -------");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Registrar Profesional");
            System.out.println("3. Agregar Servicio a Profesional");
            System.out.println("4. Crear Cita");
            System.out.println("5. Mostrar Citas");
            System.out.println("6. Mostrar Informacion General");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            // Manejar error si el usuario no pone un número
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("️ Opcion invalida. Intente nuevamente.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                
                
                case 1 -> { // Registrar Cliente
                    try {
                        System.out.print("ID cliente: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Telefono: ");
                        String tel = sc.nextLine();
                        System.out.print("Correo: ");
                        String correo = sc.nextLine();

                        Cliente c = new Cliente(id, nombre, tel, correo);
                        agenda.agregarCliente(c);

                        System.out.println("\nCliente registrado con exito:");
                        c.mostrarCliente();
                    } catch (NumberFormatException e) {
                        System.out.println("️ Error al registrar cliente.");
                    }
                }

                case 2 -> { // Registrar Profesional
                    try {
                        System.out.print("ID profesional: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Telefono: ");
                        String tel = sc.nextLine();
                        System.out.print("Correo: ");
                        String correo = sc.nextLine();
                        System.out.print("Especialidad (barbero, medico, etc.): ");
                        String esp = sc.nextLine();

                        Profesional p = new Profesional(id, nombre, tel, correo, esp);
                        agenda.agregarProfesional(p);

                        System.out.println("\n Profesional registrado con exito:");
                        p.mostrarProfesional();
                    } catch (NumberFormatException e) {
                        System.out.println("️ Error al registrar profesional.");
                    }
                }

                case 3 -> { // Agregar Servicio a Profesional
                    System.out.print("ID profesional: ");
                    int idProf = Integer.parseInt(sc.nextLine());
                    Profesional p = agenda.buscarProfesionalPorId(idProf);

                    if (p == null) {
                        System.out.println("️ Profesional no encontrado.");
                        break;
                    }

                    try {
                        System.out.print("ID servicio: ");
                        int idS = Integer.parseInt(sc.nextLine());
                        System.out.print("Nombre del servicio: ");
                        String nomS = sc.nextLine();
                        System.out.print("Duracion (min): ");
                        int dur = Integer.parseInt(sc.nextLine());
                        System.out.print("Precio ($): ");
                        double precio = Double.parseDouble(sc.nextLine());

                        Servicio s = new Servicio(idS, nomS, dur, precio);
                        p.agregarServicio(s);
                        agenda.agregarServicio(s);

                        System.out.println("\n Servicio agregado correctamente:");
                        s.mostrarServicio();
                    } catch (NumberFormatException e) {
                        System.out.println("️ Error al agregar servicio.");
                    }
                }

                case 4 -> { // Crear cita
                    try {
                        System.out.print("ID cita: ");
                        int idC = Integer.parseInt(sc.nextLine());
                        System.out.print("ID cliente: ");
                        int idCli = Integer.parseInt(sc.nextLine());
                        Cliente c = agenda.buscarClientePorId(idCli);

                        System.out.print("ID profesional: ");
                        int idProf = Integer.parseInt(sc.nextLine());
                        Profesional p = agenda.buscarProfesionalPorId(idProf);

                        if (c == null || p == null) {
                            System.out.println("️ Cliente o profesional no encontrados.");
                            break;
                        }

                        if (p.getServicios().isEmpty()) {
                            System.out.println("️ Este profesional no tiene servicios aún.");
                            break;
                        }

                        System.out.println("\nServicios disponibles:");
                        for (Servicio s : p.getServicios()) s.mostrarServicio();

                        System.out.print("ID servicio: ");
                        int idServ = Integer.parseInt(sc.nextLine());
                        Servicio serv = agenda.buscarServicioPorId(idServ);

                        if (serv == null) {
                            System.out.println("️ Servicio no encontrado.");
                            break;
                        }

                        System.out.println("\nIngrese fecha de la cita:");
                        System.out.print("Dia (1-31): ");
                        int dia = Integer.parseInt(sc.nextLine());
                        System.out.print("Mes (1-12): ");
                        int mes = Integer.parseInt(sc.nextLine());
                        System.out.print("Ano (ej: 2025): ");
                        int año = Integer.parseInt(sc.nextLine());
                        System.out.print("Hora (0-23): ");
                        int hora = Integer.parseInt(sc.nextLine());
                        System.out.print("Minutos (0-59): ");
                        int min = Integer.parseInt(sc.nextLine());

                        LocalDateTime fechaHora = LocalDateTime.of(año, mes, dia, hora, min);

                        agenda.crearCita(idC, c, p, serv, fechaHora);
                    } catch (NumberFormatException e) {
                        System.out.println("️ Error al crear cita. Revise los datos ingresados.");
                    }
                }

                case 5 -> agenda.mostrarCitas();

                case 6 -> { // Mostrar todo
                    System.out.println("\n--- CLIENTES REGISTRADOS ---");
                    for (Cliente c : agenda.getClientes()) c.mostrarCliente();

                    System.out.println("\n---PROFESIONALES REGISTRADOS ---");
                    for (Profesional p : agenda.getProfesionales()) p.mostrarProfesional();

                    System.out.println("\n--- SERVICIOS DISPONIBLES ---");
                    for (Servicio s : agenda.getServicios()) s.mostrarServicio();
                }

                case 7 -> {
                        System.out.println(" Guardando citas...");
                        GestorArchivos.guardarCitas(agenda.getCitas());
                        System.out.println(" Citas guardadas correctamente. Saliendo del sistema...");
                        System.out.println("Saliendo del sistema...");
                }
                default -> System.out.println(" Opción no valida.");
            }

        } while (opcion != 7);
    }
}