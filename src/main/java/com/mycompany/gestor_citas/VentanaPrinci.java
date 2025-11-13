package com.mycompany.gestor_citas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrinci extends JFrame {
    private final JPanel panelMenu;
    private final JPanel panelContenido;
    private final JLabel lblTitulo;
    private final Agenda agenda; // ahora se asigna correctamente desde el constructor

    public VentanaPrinci(Agenda agenda) {
        this.agenda = agenda; // ✅ CORRECCIÓN: se usa la instancia recibida
        setTitle("Gestor de Citas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setUndecorated(false);

        // ===== PANEL SUPERIOR =====
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setPreferredSize(new Dimension(1200, 70));

        lblTitulo = new JLabel("GESTOR DE CITAS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(30, 30, 60));
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        // ===== PANEL LATERAL =====
        panelMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(25, 40, 80),
                        0, getHeight(), new Color(60, 140, 255)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelMenu.setLayout(new GridLayout(7, 1, 0, 15));
        panelMenu.setPreferredSize(new Dimension(250, 0));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        // ===== BOTONES =====
        JButton btnInicio = crearBoton("  Inicio");
        JButton btnClientes = crearBoton("  Clientes");
        JButton btnProfesionales = crearBoton(" Profesionales");
        JButton btnServicios = crearBoton("  Servicios");
        JButton btnCitas = crearBoton("  Citas");
        JButton btnReportes = crearBoton(" Reportes");
        JButton btnSalir = crearBoton(" Cerrar Sesión");

        panelMenu.add(btnInicio);
        panelMenu.add(btnClientes);
        panelMenu.add(btnProfesionales);
        panelMenu.add(btnServicios);
        panelMenu.add(btnCitas);
        panelMenu.add(btnReportes);
        panelMenu.add(btnSalir);
        add(panelMenu, BorderLayout.WEST);

        // ===== PANEL CENTRAL =====
        panelContenido = new JPanel();
        panelContenido.setBackground(new Color(240, 242, 245));
        panelContenido.setLayout(new GridBagLayout());
        mostrarInicio();
        add(panelContenido, BorderLayout.CENTER);

        // ===== EVENTOS =====
        btnInicio.addActionListener(e -> mostrarInicio());
        btnClientes.addActionListener(e -> new VentanaClientes(agenda).setVisible(true));
        btnProfesionales.addActionListener(e -> new VentanaProfesionales(agenda).setVisible(true));
        btnServicios.addActionListener(e -> new VentanaServicios(agenda).setVisible(true));
        btnCitas.addActionListener(e -> new VentanaCitas(agenda).setVisible(true));
        btnReportes.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Generando reportes PDF...\n(Revisar carpeta del proyecto)",
                "Reportes", JOptionPane.INFORMATION_MESSAGE));
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new com.mycompany.gestor_citas.LoginForm().setVisible(true);
            }
        });

        setVisible(true);
    }

    // ===== BOTONES CON EFECTO HOVER ELEGANTE =====
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(255, 255, 255, 40));
                boton.setOpaque(true);
                boton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setOpaque(false);
                boton.setForeground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boton.setBackground(new Color(200, 220, 255, 100));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                boton.setBackground(new Color(255, 255, 255, 60));
            }
        });

        return boton;
    }

    // ===== CONTENIDO DE INICIO =====
    private void mostrarInicio() {
        panelContenido.removeAll();

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(700, 400));
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        JLabel titulo = new JLabel(" Bienvenido al Gestor de Citas ", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(30, 30, 90));

        JLabel subtitulo = new JLabel("<html><div style='text-align:center;'>Administra clientes, profesionales, servicios y citas.<br>Selecciona una opción en el menú lateral para comenzar.</div></html>", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        subtitulo.setForeground(new Color(90, 90, 90));

        card.add(titulo, BorderLayout.NORTH);
        card.add(subtitulo, BorderLayout.CENTER);

        panelContenido.add(card, new GridBagConstraints());
        panelContenido.revalidate();
        panelContenido.repaint();
    }
}