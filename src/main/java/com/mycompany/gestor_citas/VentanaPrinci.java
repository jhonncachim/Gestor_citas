// VentanaPrinci.java
package com.mycompany.gestor_citas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
  Ventana principal (Dashboard).
  - Crea la topbar con logo y título.
  - Crea sidebar con botones estilo "pill" (redondeados).
  - Muestra un tarjeta de bienvenida en el centro.
  - Cada botón abre la ventana correspondiente pasando la misma Agenda.
*/
public class VentanaPrinci extends JFrame {
    private final JPanel panelMenu;
    private final JPanel panelContenido;
    private final Agenda agenda;

    public VentanaPrinci(Agenda agenda) {
        this.agenda = agenda;

        setTitle("Gestor de Citas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setUndecorated(false);

        // Icono de la aplicación (barra título)
        try {
            ImageIcon icon = new ImageIcon("C:\\Users\\ASUS VIVOBOOK\\Documents\\reser.png");
            this.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo: " + e.getMessage());
        }

        // TOPBAR (barra superior)
        // Color oscuro y logo a la izquierda, título centrado.
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.decode("#0F172A"));
        topBar.setPreferredSize(new Dimension(0, 80));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        // Carga y agrega el logo (si existe)
        try {
            ImageIcon ico = new ImageIcon("C:\\Users\\ASUS VIVOBOOK\\Documents\\reser.png");
            Image img = ico.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH);
            JLabel l = new JLabel(new ImageIcon(img));
            topBar.add(l, BorderLayout.WEST);
        } catch (Exception ex) { /* si falla, seguimos sin logo */ }

        // Título principal
        JLabel lblTitle = new JLabel("Panel Principal", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI Semibold", Font.BOLD, 24));
        topBar.add(lblTitle, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

        // SIDEBAR con gradiente
        // - contiene botones pill (redondeados).
        // - layout vertical con espacio.
        panelMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.decode("#1E293B"), 0, getHeight(), Color.decode("#0EA5E9"));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelMenu.setLayout(new GridLayout(10, 1, 0, 12));
        panelMenu.setPreferredSize(new Dimension(260, 0));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(32, 20, 32, 20));

        // BOTONES del sidebar
        JButton btnInicio = crearBotonPill("Inicio");
        JButton btnClientes = crearBotonPill("Clientes");
        JButton btnProfesionales = crearBotonPill("Profesionales");
        JButton btnServicios = crearBotonPill("Servicios");
        JButton btnCitas = crearBotonPill("Citas");
        JButton btnReportes = crearBotonPill("Reportes");
        JButton btnSalir = crearBotonPill("Cerrar sesión");

        panelMenu.add(btnInicio);
        panelMenu.add(btnClientes);
        panelMenu.add(btnProfesionales);
        panelMenu.add(btnServicios);
        panelMenu.add(btnCitas);
        panelMenu.add(btnReportes);
        panelMenu.add(Box.createVerticalGlue()); // empuja el botón de salir al final
        panelMenu.add(btnSalir);

        add(panelMenu, BorderLayout.WEST);

        // PANEL CONTENIDO CENTRAL
        // - fondo oscuro para contraste con tarjeta azul.
        panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setBackground(Color.decode("#0F172A"));
        mostrarInicio(); // carga la tarjeta de bienvenida
        add(panelContenido, BorderLayout.CENTER);

        // EVENTOS de los botones
        // - cada uno abre su ventana y pasa la instancia de agenda.

        btnInicio.addActionListener(e -> mostrarInicio());
        btnClientes.addActionListener(e -> new VentanaClientes(agenda).setVisible(true));
        btnProfesionales.addActionListener(e -> new VentanaProfesionales(agenda).setVisible(true));
        btnServicios.addActionListener(e -> new VentanaServicios(agenda).setVisible(true));
        btnCitas.addActionListener(e -> new VentanaCitas(agenda).setVisible(true));
        btnReportes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Generando reportes PDF...", "Reportes", JOptionPane.INFORMATION_MESSAGE));
        btnSalir.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                dispose();
                new LoginForm().setVisible(true);
            }
        });

        setVisible(true);
    }

    // Crea un botón "pill" redondeado con hover sencillo.
    private JButton crearBotonPill(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setForeground(Color.WHITE);
        b.setBackground(Color.decode("#0EA5E9"));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.setBorder(new RoundedBorder(30, Color.decode("#0EA5E9")));
        b.setPreferredSize(new Dimension(200, 44));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(Color.decode("#38BDF8")); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(Color.decode("#0EA5E9")); }
        });
        return b;
    }

    // Muestra la tarjeta grande de bienvenida en el centro (gradiente azul)
    private void mostrarInicio() {
        panelContenido.removeAll();

        JPanel tarjeta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0,0, Color.decode("#0EA5E9"), getWidth(), getHeight(), Color.decode("#006C9A"));
                g2.setPaint(gp);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),24,24);
            }
        };
        tarjeta.setPreferredSize(new Dimension(820,420));
        tarjeta.setOpaque(false);
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBorder(BorderFactory.createEmptyBorder(28,28,28,28));

        // Encabezado y subtítulo dentro de la tarjeta
        JLabel h = new JLabel("Bienvenido a ReservaPro", SwingConstants.CENTER);
        h.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
        h.setForeground(Color.white);

        JLabel subt = new JLabel("<html><div style='text-align:center; color:white; font-size:16px;'>"
                        + "Gestiona clientes, profesionales, servicios y agendamiento dentro de un entorno moderno, "
                        + "ágil y visualmente profesional.<br><br>"
                        + "Utiliza el menú lateral para acceder a cada módulo del sistema."
                        + "</div></html>", SwingConstants.CENTER);
        subt.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        tarjeta.add(h, BorderLayout.NORTH);
        tarjeta.add(subt, BorderLayout.CENTER);

        panelContenido.add(tarjeta, new GridBagConstraints());
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    // Clase interna para dibujar bordes redondeados en los botones
    private static class RoundedBorder implements javax.swing.border.Border {
        private final int radius;
        private final Color color;
        public RoundedBorder(int r, Color c){ radius=r; color=c; }
        public Insets getBorderInsets(Component c){ return new Insets(radius,radius,radius,radius); }
        public boolean isBorderOpaque(){ return true; }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(x+1,y+1,width-2,height-2,radius,radius);
        }
    }
}