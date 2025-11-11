/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.gestor_citas;

/**
 *
 * @author ASUS VIVOBOOK
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrinci extends javax.swing.JFrame {

    private final JPanel panelMenu;
    private final JPanel panelContenido;
    private final JButton btnClientes;
    private final JButton btnProfesionales;
    private final JButton btnCitas;
    private final JButton btnReportes;
    private final JButton btnCerrarSesion;
    private JLabel lblTitulo;

    public VentanaPrinci() {
        // Configuración de la ventana principal
        setTitle("Gestor de Citas - Panel Principal");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // --- PANEL LATERAL (Menú) ---
        panelMenu = new JPanel();
        panelMenu.setBackground(new Color(46, 125, 50)); // Verde principal
        panelMenu.setLayout(new GridLayout(6, 1, 0, 10));
        panelMenu.setPreferredSize(new Dimension(220, 0));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        // --- BOTONES DEL MENÚ ---
        btnClientes = crearBotonMenu("Clientes");
        btnProfesionales = crearBotonMenu("Profesionales");
        btnCitas = crearBotonMenu("Citas");
        btnReportes = crearBotonMenu("Reportes");
        btnCerrarSesion = crearBotonMenu("Cerrar Sesión");

        // Añadir botones al menú
        panelMenu.add(btnClientes);
        panelMenu.add(btnProfesionales);
        panelMenu.add(btnCitas);
        panelMenu.add(btnReportes);
        panelMenu.add(new JLabel()); // Espacio
        panelMenu.add(btnCerrarSesion);

        add(panelMenu, BorderLayout.WEST);

        // --- PANEL DE CONTENIDO CENTRAL ---
        panelContenido = new JPanel();
        panelContenido.setBackground(new Color(245, 245, 245));
        panelContenido.setLayout(new BorderLayout());

        lblTitulo = new JLabel("Bienvenido al Gestor de Citas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(46, 125, 50));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelContenido.add(lblTitulo, BorderLayout.NORTH);

        JLabel lblMensaje = new JLabel("Selecciona una opción del menú para comenzar", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 16));
        panelContenido.add(lblMensaje, BorderLayout.CENTER);

        add(panelContenido, BorderLayout.CENTER);

        // --- EVENTOS DE LOS BOTONES ---
        btnClientes.addActionListener(e -> mostrarSeccion("Gestión de Clientes"));
        btnProfesionales.addActionListener(e -> mostrarSeccion("Gestión de Profesionales"));
        btnCitas.addActionListener(e -> mostrarSeccion("Agenda de Citas"));
        btnReportes.addActionListener(e -> mostrarSeccion("Reportes Generales"));

        btnCerrarSesion.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Deseas cerrar sesión?",
                    "Cerrar Sesión",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                dispose();
                new LoginForm().setVisible(true);
            }
        });
    }

    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 15));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(56, 142, 60));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(67, 160, 71));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(56, 142, 60));
            }
        });

        return boton;
    }

    private void mostrarSeccion(String titulo) {
        panelContenido.removeAll();

        lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(46, 125, 50));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelContenido.add(lblTitulo, BorderLayout.NORTH);

        JLabel lblMensaje = new JLabel("Aquí irá el contenido de: " + titulo, SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 16));
        panelContenido.add(lblMensaje, BorderLayout.CENTER);

        panelContenido.revalidate();
        panelContenido.repaint();
    }
}