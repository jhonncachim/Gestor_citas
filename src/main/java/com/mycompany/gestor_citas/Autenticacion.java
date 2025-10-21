/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_citas;

/**
 *
 * @author ASUS VIVOBOOK
 */
import java.util.HashMap;
import java.util.Scanner;

/**
 * Clase Autenticacion:
 * Maneja el inicio de sesión y registro de nuevos usuarios en el sistema.
 * Usa un HashMap para almacenar pares (usuario, contraseña).
 */
public class Autenticacion {
    private HashMap<String, String> usuarios; // Estructura clave-valor para guardar usuarios

    // Constructor: inicializa con un usuario administrador por defecto
    public Autenticacion() {
        usuarios = new HashMap<>();
        usuarios.put("admin", "1234"); // Usuario principal del sistema
    }

    // Método para iniciar sesión
    public boolean iniciarSesion(Scanner sc) {
        System.out.println("\n    INICIO DE SESIÓN    ");
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine();

        // Verifica si el usuario existe y la contraseña es correcta
        if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(contrasena)) {
            System.out.println("Inicio de sesión exitoso. ¡Bienvenido, " + usuario + "!");
            return true;
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
            return false;
        }
    }

    // Método para registrar un nuevo usuario
    public void registrarUsuario(Scanner sc) {
        System.out.println("\n    REGISTRAR NUEVO USUARIO  ");
        System.out.print("Nuevo nombre de usuario: ");
        String usuario = sc.nextLine();

        // Validamos que no exista
        if (usuarios.containsKey(usuario)) {
            System.out.println(" El usuario ya existe. Intente con otro nombre.");
            return;
        }

        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine();

        // Guardamos el nuevo usuario
        usuarios.put(usuario, contrasena);
        System.out.println(" Usuario registrado con éxito.");
    }
}
