/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;

/**
 *
 * @author ASUS VIVOBOOK
 */
import java.io.*;
import java.util.*;

/**
 * Clase GestorArchivos: maneja el guardado y carga de citas desde archivos CSV.
 */
public class GestorArchivos {
    private static final String RUTA_CITAS = "citas.csv";

    // Guardar todas las citas en un archivo CSV
    public static void guardarCitas(ArrayList<Cita> citas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CITAS))) {
            for (Cita c : citas) {
                bw.write(c.toCSV());
                bw.newLine();
            }
            System.out.println(" Citas guardadas en archivo correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar citas: " + e.getMessage());
        }
    }

    // Cargar citas desde archivo
    public static void cargarCitas(Agenda agenda) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CITAS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Cita cita = Cita.fromCSV(linea, agenda);
                if (cita != null) agenda.getCitas().add(cita);
            }
            System.out.println(" Citas cargadas correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de citas (se creará al guardar).");
        } catch (IOException e) {
            System.out.println("Error al leer citas: " + e.getMessage());
        }
    }
}
