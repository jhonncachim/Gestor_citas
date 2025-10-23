/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;

import java.io.*;
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

    //  CLIENTES

    /**
     * Guarda todos los clientes registrados en el archivo clientes.csv
     * @param clientes
     */
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

    /**
     * Carga los clientes desde clientes.csv a la agenda
     * @param agenda
     */
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
            System.out.println("ℹ️ No se encontró 'clientes.csv' (se creará al guardar).");
        } catch (IOException e) {
            System.out.println(" Error al leer clientes: " + e.getMessage());
        }
    }

    //  PROFESIONALES

    /**
     * Guarda todos los profesionales registrados en el archivo profesionales.csv
     * @param profesionales
     */
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

    /**
     * Carga los profesionales desde profesionales.csv a la agenda
     * @param agenda
     */
    public static void cargarProfesionales(Agenda agenda) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_PROFESIONALES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 5) {
                    Profesional p = new Profesional(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        partes[2],
                        partes[3],
                        partes[4]
                    );
                    agenda.getProfesionales().add(p);
                }
            }
            System.out.println(" Profesionales cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No se encontró 'profesionales.csv' (se creará al guardar).");
        } catch (IOException e) {
            System.out.println(" Error al leer profesionales: " + e.getMessage());
        }
    }

    //  SERVICIOS

    /**
     * Guarda todos los servicios registrados en el archivo servicios.csv
     * @param servicios
     */
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

    /**
     * Carga los servicios desde servicios.csv a la agenda
     * @param agenda
     */
    public static void cargarServicios(Agenda agenda) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_SERVICIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    Servicio s = new Servicio(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        Integer.parseInt(partes[2]),
                        Double.parseDouble(partes[3])
                    );
                    agenda.getServicios().add(s);
                }
            }
            System.out.println(" Servicios cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No se encontró 'servicios.csv' (se creará al guardar).");
        } catch (IOException e) {
            System.out.println(" Error al leer servicios: " + e.getMessage());
        }
    }

    //  CITAS

    /**
     * Guarda todas las citas registradas en el archivo citas.csv
     * @param citas
     */
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

    /**
     * Carga las citas desde citas.csv a la agenda
     * @param agenda
     */
    public static void cargarCitas(Agenda agenda) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CITAS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Cita cita = Cita.fromCSV(linea, agenda);
                if (cita != null) agenda.getCitas().add(cita);
            }
            System.out.println(" Citas cargadas correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No se encontró 'citas.csv' (se creará al guardar).");
        } catch (IOException e) {
            System.out.println(" Error al leer citas: " + e.getMessage());
        }
    }
}
