/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas.Auxiliares;

import com.mycompany.gestor_citas.Agenda;
import com.mycompany.gestor_citas.Cita;
import com.mycompany.gestor_citas.Cliente;
import com.mycompany.gestor_citas.Profesional;
import com.mycompany.gestor_citas.Servicio;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * ==========================================
 *  CLASE: GestorArchivos
 * ==========================================
 *  Descripción:
 *  Esta clase se encarga de la persistencia de datos
 *  del sistema de citas (clientes, profesionales, servicios y citas)
 *  mediante archivos CSV simples.
 *  Evitar pérdida de información al cerrar el programa.
 *
 
 * @author
 *  Proyecto desarrollado por: ASUS VIVOBOOK
 */
public class GestorArchivos {

    // Rutas de los archivos
    private static final String RUTA_CLIENTES = "clientes.csv";
    private static final String RUTA_PROFESIONALES = "profesionales.csv";
    private static final String RUTA_SERVICIOS = "servicios.csv";
    private static final String RUTA_CITAS = "citas.csv";

    // Metodo para cargar todos los datos al iniciar el sistema
    public static void cargarTodo(Agenda agenda) {
        cargarClientes(agenda);
        cargarProfesionales(agenda);
        cargarServicios(agenda);
        cargarCitas(agenda);
        System.out.println(" Todos los datos fueron cargados correctamente.");
    }

    // Metodo para guardar todos los datos al cerrar el sistema
    public static void guardarTodo(Agenda agenda) {
        guardarClientes(agenda.getClientes());
        guardarProfesionales(agenda.getProfesionales());
        guardarServicios(agenda.getServicios());
        guardarCitas(agenda.getCitas());
        System.out.println(" Todos los datos fueron guardados correctamente.");
    }

    //  CLIENTES
    public static void guardarClientes(ArrayList<Cliente> clientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CLIENTES))) {
            for (Cliente c : clientes) {
                bw.write(c.getId() + ";" + c.getNombre() + ";" + c.getTelefono() + ";" + c.getCorreo());
                bw.newLine();
            }
            System.out.println(" Clientes guardados correctamente.");
        } catch (IOException e) {
            System.out.println(" Error al guardar clientes: " + e.getMessage());
        }
    }

    private static final String RUTA_FACTURAS = "facturas.csv";
public static void cargarFacturas(Agenda agenda) {
    try (BufferedReader br = new BufferedReader(new FileReader(RUTA_FACTURAS))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] p = linea.split(";");
            if (p.length == 7) {

                Cliente cli = agenda.buscarClientePorId(Integer.parseInt(p[1]));
                Profesional pro = agenda.buscarProfesionalPorId(Integer.parseInt(p[2]));
                Servicio ser = agenda.buscarServicioPorId(Integer.parseInt(p[3]));

                Factura f = new Factura(
                    Integer.parseInt(p[0]), 
                    cli, pro, ser,
                    LocalDateTime.parse(p[4]),
                    Double.parseDouble(p[5]),
                    Double.parseDouble(p[6])
                );

                agenda.agregarFactura(f);
            }
        }
    } catch (Exception e) {
        System.out.println("No hay facturas aún.");
    }
}


public static void guardarFacturas(List<Factura> facturas) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_FACTURAS))) {

        for (Factura f : facturas) {
            pw.println(
                f.getId() + ";" +
                f.getCliente().getId() + ";" +
                f.getProfesional().getId() + ";" +
                f.getServicio().getId() + ";" +
                f.getFechaCita() + ";" +
                f.getPrecio() + ";" +
                f.getTotal()
            );
        }

    } catch (Exception e) {
        System.out.println("Error al guardar facturas");
        e.printStackTrace();
    }
}
    public static void cargarClientes(Agenda agenda) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CLIENTES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    Cliente c = new Cliente(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        partes[2],
                        partes[3]
                    );
                    agenda.getClientes().add(c);
                }
            }
            System.out.println(" Clientes cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ No se encontró 'clientes.csv' (se creará al guardar).");
        } catch (IOException e) {
            System.out.println(" Error al leer clientes: " + e.getMessage());
        }
    }

    //  PROFESIONALES
    public static void guardarProfesionales(ArrayList<Profesional> profesionales) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_PROFESIONALES))) {
            for (Profesional p : profesionales) {
                bw.write(p.getId() + ";" + p.getNombre() + ";" + p.getTelefono() + ";" +
                         p.getCorreo() + ";" + p.getEspecialidad());
                bw.newLine();
            }
            System.out.println(" Profesionales guardados correctamente.");
        } catch (IOException e) {
            System.out.println(" Error al guardar profesionales: " + e.getMessage());
        }
    }

    public static void cargarProfesionales(Agenda agenda) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_PROFESIONALES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 5) {
                    Profesional p = new Profesional(
                            
                     Integer.parseInt(partes[0]),  // ID
                        partes[1],                    // Nombre
                        partes[2],                    // Especialidad
                        partes[3],                    // Teléfono
                        partes[4]                     // Correo
);
                   
                    agenda.getProfesionales().add(p);
                }
            }
            System.out.println(" Profesionales cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ No se encontró 'profesionales.csv' (se creará al guardar).");
        } catch (IOException e) {
            System.out.println(" Error al leer profesionales: " + e.getMessage());
        }
    }

    //  SERVICIOS
    public static void guardarServicios(ArrayList<Servicio> servicios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_SERVICIOS))) {
            for (Servicio s : servicios) {
                bw.write(s.getId() + ";" + s.getNombre() + ";" +
                         s.getDuracion() + ";" + s.getPrecio());
                bw.newLine();
            }
            System.out.println(" Servicios guardados correctamente.");
        } catch (IOException e) {
            System.out.println(" Error al guardar servicios: " + e.getMessage());
        }
    }

    public static void cargarServicios(Agenda agenda) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_SERVICIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    Servicio s = new Servicio(
    Integer.parseInt(partes[0]),   // id
    partes[1],                     // nombre
    Integer.parseInt(partes[2]),   // duracion
    Double.parseDouble(partes[3])  // precio
);

                    agenda.getServicios().add(s);
                }
            }
            System.out.println(" Servicios cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println(" No se encontró 'servicios.csv' (se creará al guardar).");
        } catch (IOException e) {
            System.out.println(" Error al leer servicios: " + e.getMessage());
        }
    }

    //  CITAS
    public static void guardarCitas(ArrayList<Cita> citas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CITAS))) {
            for (Cita c : citas) {
                bw.write(c.toCSV());
                bw.newLine();
            }
            System.out.println(" Citas guardadas correctamente.");
        } catch (IOException e) {
            System.out.println(" Error al guardar citas: " + e.getMessage());
        }
    }

    public static void cargarCitas(Agenda agenda) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CITAS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Cita cita = Cita.fromCSV(linea, agenda);
                if (cita != null) agenda.getCitas().add(cita);
            }
            System.out.println(" Citas cargadas correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println(" No se encontró 'citas.csv' (se creará al guardar).");
        } catch (IOException e) {
            System.out.println(" Error al leer citas: " + e.getMessage());
        }
    }

    
}