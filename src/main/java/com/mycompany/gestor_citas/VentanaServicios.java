/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.gestor_citas;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class VentanaServicios extends javax.swing.JFrame {


    private final Agenda agenda;
    private final JTextField txtNombre, txtDuracion, txtPrecio;
    private final JTable tablaServicios;
    private final DefaultTableModel modeloTabla;

    public VentanaServicios(Agenda agenda) {
        this.agenda = agenda;

        setTitle("Gestor de Citas - Servicios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));
        // === Logo en la barra de título ===
        try {
            ImageIcon icon = new ImageIcon("C:\\Users\\ASUS VIVOBOOK\\Documents\\reser.png");
            this.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo: " + e.getMessage());
        }
        JPanel panelSuperior = new JPanel(new BorderLayout());
panelSuperior.setBackground(Color.WHITE);
panelSuperior.setPreferredSize(new Dimension(800, 100));

try {
    ImageIcon icon = new ImageIcon("C:\\Users\\ASUS VIVOBOOK\\Documents\\reser.png");
    Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
    JLabel lblLogo = new JLabel(new ImageIcon(img));
    lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    panelSuperior.add(lblLogo, BorderLayout.WEST);
} catch (Exception e) {
    System.out.println("No se pudo cargar el logo: " + e.getMessage());
}


        JLabel lblTitulo = new JLabel("Gestión de Servicios", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(40, 40, 90));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(15, 15));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelCentro.setBackground(new Color(240, 242, 245));

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelFormulario.add(new JLabel("Nombre del Servicio:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Duración (min):"));
        txtDuracion = new JTextField();
        panelFormulario.add(txtDuracion);

        panelFormulario.add(new JLabel("Precio ($):"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelCentro.add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Duración", "Precio"}, 0);
        tablaServicios = new JTable(modeloTabla);
        tablaServicios.setRowHeight(25);
        JScrollPane scrollTabla = new JScrollPane(tablaServicios);
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);

        JButton btnRegistrar = crearBoton("Registrar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnLimpiar = crearBoton("Limpiar");
        JButton btnVolver = crearBoton("Volver");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrarServicio());
        btnEliminar.addActionListener(e -> eliminarServicio());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());

        refrescarTabla();
        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(new Color(70, 130, 180));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { b.setBackground(new Color(90, 150, 200)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { b.setBackground(new Color(70, 130, 180)); }
        });
        return b;
    }

    private void registrarServicio() {
        try {
            String nombre = txtNombre.getText();
            int duracion = Integer.parseInt(txtDuracion.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.");
                return;
            }

            int id = agenda.obtenerProximoIdServicio();
            Servicio s = new Servicio(id, nombre, (int) precio,duracion);
            agenda.agregarServicio(s);
            refrescarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Servicio registrado con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar servicio. Verifica los datos.");
        }
    }

    private void eliminarServicio() {
        int fila = tablaServicios.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            agenda.getServicios().removeIf(s -> s.getId() == id);
            com.mycompany.gestor_citas.Auxiliares.GestorArchivos.guardarServicios(agenda.getServicios());
            refrescarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un servicio para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtDuracion.setText("");
        txtPrecio.setText("");
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (Servicio s : agenda.getServicios()) {
            modeloTabla.addRow(new Object[]{s.getId(), s.getNombre(), s.getDuracion(), s.getPrecio()});
        }
    }
}