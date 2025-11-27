/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.gestor_citas;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author ASUS VIVOBOOK
 */



import javax.swing.table.DefaultTableModel;
public class VentanaProfesionales extends javax.swing.JFrame {

    
    private final Agenda agenda;
    private final JTextField txtNombre, txtTelefono, txtCorreo, txtEspecialidad;
    private final JTable tablaProfesionales;
    private final DefaultTableModel modeloTabla;

    public VentanaProfesionales(Agenda agenda) {
        this.agenda = agenda;

        setTitle("Gestor de Citas - Profesionales");
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
panelSuperior.setPreferredSize(new Dimension(900, 100));

try {
    ImageIcon icon = new ImageIcon("C:\\Users\\ASUS VIVOBOOK\\Documents\\reser.png");
    Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
    JLabel lblLogo = new JLabel(new ImageIcon(img));
    lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    panelSuperior.add(lblLogo, BorderLayout.WEST);
} catch (Exception e) {
    System.out.println("No se pudo cargar el logo: " + e.getMessage());
}

        // Panel superior
        JLabel lblTitulo = new JLabel("Registro de Profesionales", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(40, 40, 90));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel central con formulario y tabla
        JPanel panelCentro = new JPanel(new BorderLayout(15, 15));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelCentro.setBackground(new Color(240, 242, 245));

        // Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo);

        panelFormulario.add(new JLabel("Especialidad:"));
        txtEspecialidad = new JTextField();
        panelFormulario.add(txtEspecialidad);

        panelCentro.add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono", "Correo", "Especialidad"}, 0);
        tablaProfesionales = new JTable(modeloTabla);
        tablaProfesionales.setRowHeight(25);
        JScrollPane scrollTabla = new JScrollPane(tablaProfesionales);
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // Botones inferiores
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

        // Eventos
        btnRegistrar.addActionListener(e -> registrarProfesional());
        btnEliminar.addActionListener(e -> eliminarProfesional());
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

    private void registrarProfesional() {
        try {
            String nombre = txtNombre.getText();
            String tel = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String esp = txtEspecialidad.getText();

            if (nombre.isEmpty() || tel.isEmpty() || correo.isEmpty() || esp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.");
                return;
            }

            int id = agenda.obtenerProximoIdProfesional();
            Profesional p = new Profesional(id, nombre, tel, correo, esp);
            agenda.agregarProfesional(p);
            refrescarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Profesional registrado con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar profesional.");
        }
    }

    private void eliminarProfesional() {
        int fila = tablaProfesionales.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            agenda.getProfesionales().removeIf(p -> p.getId() == id);
            com.mycompany.gestor_citas.Auxiliares.GestorArchivos.guardarProfesionales(agenda.getProfesionales());
            refrescarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un profesional para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtEspecialidad.setText("");
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (Profesional p : agenda.getProfesionales()) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getTelefono(), p.getCorreo(), p.getEspecialidad()});
        }
    }
}