/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.gestor_citas;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaClientes extends javax.swing.JFrame {

    // === Atributos principales de la ventana ===
    private final Agenda agenda;                 // Contiene la lista de clientes
    private final DefaultTableModel modelo;      // Modelo de la tabla
    private final JTable tabla;                  // Tabla para visualizar clientes
    private final JTextField txtNombre, txtTelefono, txtCorreo;  // Campos de entrada
    private final JButton btnRegistrar, btnEditar, btnEliminar, btnLimpiar, btnVolver; // Botones

    public VentanaClientes(Agenda agenda) {

        // Guardamos agenda para trabajar con los clientes
        this.agenda = agenda;

        // Configuración básica de la ventana
        setTitle("Gestión de Clientes");
        setSize(900, 600);
        setLocationRelativeTo(null);              // Centra la ventana
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245)); // Fondo gris claro moderno

        //  ----------------- ICONO DEL PROGRAMA -----------------
        try {
            ImageIcon icon = new ImageIcon("C:\\Users\\ASUS VIVOBOOK\\Documents\\reser.png");
            this.setIconImage(icon.getImage());  // Establece el ícono en la barra de título
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo: " + e.getMessage());
        }

        // ------------- PANEL SUPERIOR CON LOGO ----------------
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setPreferredSize(new Dimension(900, 100));

        try {
            ImageIcon icon = new ImageIcon("C:\\Users\\ASUS VIVOBOOK\\Documents\\reser.png");
            Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(img)); // Logo redimensionado
            lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            panelSuperior.add(lblLogo, BorderLayout.WEST);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo: " + e.getMessage());
        }

        // ---------------------- TÍTULO -------------------------
        JLabel titulo = new JLabel("Clientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        add(titulo, BorderLayout.NORTH);

        // ------------------ FORMULARIO IZQUIERDO ---------------
        JPanel form = new JPanel(new GridBagLayout());  // Layout flexible
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);           // Espaciado entre elementos
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Campo Nombre ---
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Nombre:"), gbc);

        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        form.add(txtNombre, gbc);

        // --- Campo Teléfono ---
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Teléfono:"), gbc);

        txtTelefono = new JTextField(15);
        gbc.gridx = 1;
        form.add(txtTelefono, gbc);

        // --- Campo Correo ---
        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Correo:"), gbc);

        txtCorreo = new JTextField(20);
        gbc.gridx = 1;
        form.add(txtCorreo, gbc);

        // ----------------------- BOTONES -----------------------
        btnRegistrar = new JButton("Registrar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnVolver = new JButton("Volver");

        JPanel botones = new JPanel();
        botones.setBackground(Color.WHITE);
        botones.add(btnRegistrar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        botones.add(btnVolver);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        form.add(botones, gbc);

        add(form, BorderLayout.WEST);  // Formulario a la izquierda

        // ------------------------ TABLA ------------------------
        modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Teléfono", "Correo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;  // Evita edición directa en la tabla
            }
        };

        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Selección de una fila

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER); // Tabla al centro

        // Cargar datos de clientes
        refreshTabla();

        // -------------------- EVENTOS BOTONES ------------------
        btnRegistrar.addActionListener(e -> registrarCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose()); // Cierra ventana

        // Evento: cuando selecciono una fila, llenar campos
        tabla.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtNombre.setText((String) modelo.getValueAt(fila, 1));
                txtTelefono.setText((String) modelo.getValueAt(fila, 2));
                txtCorreo.setText((String) modelo.getValueAt(fila, 3));
            }
        });

        setVisible(true); // Mostrar ventana
    }

    // ----------- MÉTODO PARA ACTUALIZAR TABLA ----------------
    private void refreshTabla() {
        modelo.setRowCount(0); // Limpia tabla

        for (Cliente c : agenda.getClientes()) {
            modelo.addRow(new Object[]{
                c.getId(), c.getNombre(), c.getTelefono(), c.getCorreo()
            });
        }
    }

    // ----------- REGISTRAR NUEVO CLIENTE ----------------------
    private void registrarCliente() {

        String nombre = txtNombre.getText().trim();
        String tel = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || tel.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        int id = agenda.obtenerProximoIdCliente();
        Cliente c = new Cliente(id, nombre, tel, correo);

        agenda.agregarCliente(c);  // Agrega cliente a la agenda
        refreshTabla();            // Actualiza tabla
        limpiarCampos();

        JOptionPane.showMessageDialog(this, "Cliente registrado.");
    }

    // -------------------- EDITAR CLIENTE ----------------------
    private void editarCliente() {

        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        Cliente c = agenda.buscarClientePorId(id);
        if (c == null) return;

        // Actualizar datos del cliente seleccionado
        c.setNombre(txtNombre.getText().trim());
        c.setTelefono(txtTelefono.getText().trim());
        c.setCorreo(txtCorreo.getText().trim());

        com.mycompany.gestor_citas.Auxiliares.GestorArchivos
            .guardarClientes(agenda.getClientes()); // Guarda en CSV

        refreshTabla();
        JOptionPane.showMessageDialog(this, "Cliente editado.");
    }

    // -------------------- ELIMINAR CLIENTE --------------------
    private void eliminarCliente() {

        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        Cliente toRemove = null;

        // Buscar cliente por ID
        for (Cliente c : agenda.getClientes())
            if (c.getId() == id) { toRemove = c; break; }

        // Si lo encontró, lo elimina
        if (toRemove != null) {

            agenda.getClientes().remove(toRemove);

            com.mycompany.gestor_citas.Auxiliares.GestorArchivos
                .guardarClientes(agenda.getClientes()); // Guardar cambios

            refreshTabla();
            limpiarCampos();

            JOptionPane.showMessageDialog(this, "Cliente eliminado.");
        }
    }

    // --------------------- LIMPIAR CAMPOS ---------------------
    private void limpiarCampos() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        tabla.clearSelection();  // Quita selección de la tabla
    }
}