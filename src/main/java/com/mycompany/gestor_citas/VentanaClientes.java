/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.gestor_citas;
import java.awt.*;
import javax.swing.*;



import javax.swing.table.DefaultTableModel;
public class VentanaClientes extends javax.swing.JFrame {

    private final Agenda agenda;
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final JTextField txtNombre, txtTelefono, txtCorreo;
    private final JButton btnRegistrar, btnEditar, btnEliminar, btnLimpiar, btnVolver;

    public VentanaClientes(Agenda agenda) {
        this.agenda = agenda;
        setTitle("Gestión de Clientes");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // Header
        JLabel titulo = new JLabel("Clientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(12,0,12,0));
        add(titulo, BorderLayout.NORTH);

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        form.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(15);
        gbc.gridx = 1;
        form.add(txtTelefono, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Correo:"), gbc);
        txtCorreo = new JTextField(20);
        gbc.gridx = 1;
        form.add(txtCorreo, gbc);

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

        add(form, BorderLayout.WEST);

        // Tabla
        modelo = new DefaultTableModel(new Object[]{"ID","Nombre","Teléfono","Correo"},0) {
            @Override public boolean isCellEditable(int row,int col){return false;}
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Cargar datos iniciales
        refreshTabla();

        // Eventos
        btnRegistrar.addActionListener(e -> registrarCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());

        // Al seleccionar fila llenar campos
        tabla.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtNombre.setText((String) modelo.getValueAt(fila,1));
                txtTelefono.setText((String) modelo.getValueAt(fila,2));
                txtCorreo.setText((String) modelo.getValueAt(fila,3));
            }
        });

        setVisible(true);
    }

    private void refreshTabla() {
        modelo.setRowCount(0);
        for (Cliente c : agenda.getClientes()) {
            modelo.addRow(new Object[]{c.getId(), c.getNombre(), c.getTelefono(), c.getCorreo()});
        }
    }

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
        agenda.agregarCliente(c);
        refreshTabla();
        limpiarCampos();
        JOptionPane.showMessageDialog(this, "Cliente registrado.");
    }

    private void editarCliente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un cliente."); return; }
        int id = (int) modelo.getValueAt(fila,0);
        Cliente c = agenda.buscarClientePorId(id);
        if (c == null) return;
        c.setNombre(txtNombre.getText().trim());
        c.setTelefono(txtTelefono.getText().trim());
        c.setCorreo(txtCorreo.getText().trim());
        com.mycompany.gestor_citas.Auxiliares.GestorArchivos.guardarClientes(agenda.getClientes());
        refreshTabla();
        JOptionPane.showMessageDialog(this, "Cliente editado.");
    }

    private void eliminarCliente() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un cliente."); return; }
        int id = (int) modelo.getValueAt(fila,0);
        Cliente toRemove = null;
        for (Cliente c : agenda.getClientes()) if (c.getId()==id) { toRemove = c; break; }
        if (toRemove != null) {
            agenda.getClientes().remove(toRemove);
            com.mycompany.gestor_citas.Auxiliares.GestorArchivos.guardarClientes(agenda.getClientes());
            refreshTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Cliente eliminado.");
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        tabla.clearSelection();
    }
}
