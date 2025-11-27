/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.gestor_citas;

import com.mycompany.gestor_citas.Auxiliares.Factura;
import com.mycompany.gestor_citas.Auxiliares.GestorArchivos;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class VentanaCitas extends javax.swing.JFrame {
    private final Agenda agenda;
    private final DefaultTableModel modelo;
    private final JComboBox<String> cbCliente, cbProfesional, cbServicio;
    private final JTextField txtDia, txtMes, txtAnio, txtHora, txtMinuto;

    public VentanaCitas(Agenda agenda) {
        this.agenda = agenda;
        setTitle("ReservaPro");
        setSize(920, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // PANEL SUPERIOR: LOGO + TÍTULO
        JLabel titulo = new JLabel("ReservaPro", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        JLabel logo = new JLabel();
        try {
            ImageIcon img = new ImageIcon("C:\\Users\\ASUS VIVOBOOK\\Documents\\reser.png");
            Image esc = img.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(esc));
        } catch (Exception ex) {
            System.out.println("No se pudo cargar el logo.");
        }

        JPanel header = new JPanel(new BorderLayout());
        header.add(logo, BorderLayout.WEST);
        header.add(titulo, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        // FORMULARIO IZQUIERDO
        JPanel form = new JPanel(new GridLayout(8, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cbCliente = new JComboBox<>();
        cbProfesional = new JComboBox<>();
        cbServicio = new JComboBox<>();

        txtDia = new JTextField();
        txtMes = new JTextField();
        txtAnio = new JTextField();
        txtHora = new JTextField();
        txtMinuto = new JTextField();

        form.add(new JLabel("Cliente (id - nombre):")); form.add(cbCliente);
        form.add(new JLabel("Profesional (id - nombre):")); form.add(cbProfesional);
        form.add(new JLabel("Servicio (id - nombre):")); form.add(cbServicio);
        form.add(new JLabel("Día (1-31):")); form.add(txtDia);
        form.add(new JLabel("Mes (1-12):")); form.add(txtMes);
        form.add(new JLabel("Año (ej: 2025):")); form.add(txtAnio);
        form.add(new JLabel("Hora (0-23):")); form.add(txtHora);
        form.add(new JLabel("Minuto (0-59):")); form.add(txtMinuto);

        add(form, BorderLayout.WEST);

        // BOTONES
        JButton btnRegistrar = new JButton("Registrar Cita");
        JButton btnEliminar = new JButton("Eliminar Cita");
        JButton btnAtender = new JButton("Marcar como atendida");
        JButton btnFactura = new JButton("Generar Factura");

        JPanel botones = new JPanel();
        botones.add(btnRegistrar);
        botones.add(btnEliminar);
        botones.add(btnAtender);
        botones.add(btnFactura);

        add(botones, BorderLayout.SOUTH);

        // TABLA
        modelo = new DefaultTableModel(new Object[]{"ID","Cliente","Profesional","Servicio","Fecha y hora","Estado"},0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(26);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // EVENTOS
        refreshCombos();
        refreshTabla();

        btnRegistrar.addActionListener(e -> registrarCita());
        btnEliminar.addActionListener(e -> eliminarCita(tabla));
        btnFactura.addActionListener(e -> generarFactura(tabla));

        btnAtender.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una cita para marcar como atendida.");
                return;
            }

            int id = (int) modelo.getValueAt(fila, 0);

            for (Cita c : agenda.getCitas()) {
                if (c.getId() == id) {
                    if (!c.isActiva()) {
                        JOptionPane.showMessageDialog(this, "No puedes marcar una cita cancelada.");
                        return;
                    }
                    if (c.isAtendida()) {
                        JOptionPane.showMessageDialog(this, "Esta cita ya está marcada como atendida.");
                        return;
                    }

                    c.marcarAtendida();
                    GestorArchivos.guardarCitas(agenda.getCitas());
                    refreshTabla();
                    JOptionPane.showMessageDialog(this, "Cita marcada como atendida.");
                    return;
                }
            }
        });

        // ACTUALIZACIÓN AUTOMÁTICA
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() { SwingUtilities.invokeLater(() -> refreshTabla()); }
        }, 0, 60_000);

        setVisible(true);
    }

    // MÉTODOS
    private void refreshCombos() {
        cbCliente.removeAllItems();
        for (Cliente c : agenda.getClientes())
            cbCliente.addItem(c.getId() + " - " + c.getNombre());

        cbProfesional.removeAllItems();
        for (Profesional p : agenda.getProfesionales())
            cbProfesional.addItem(p.getId() + " - " + p.getNombre());

        cbServicio.removeAllItems();
        for (Servicio s : agenda.getServicios())
            cbServicio.addItem(s.getId() + " - " + s.getNombre());
    }

    private final java.time.format.DateTimeFormatter FORMATO =
        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private void refreshTabla() {
        modelo.setRowCount(0);
        LocalDateTime ahora = LocalDateTime.now();

        for (Cita c : agenda.getCitas()) {
            String estado;

            if (!c.isActiva()) estado = "Cancelada";
            else if (c.isAtendida()) estado = "Atendida";
            else if (c.getFechaHora().isBefore(ahora)) estado = "Caducada";
            else estado = "Activa";

            modelo.addRow(new Object[]{
                c.getId(),
                c.getCliente().getNombre(),
                c.getProfesional().getNombre(),
                c.getServicio().getNombre(),
                c.getFechaHora().format(FORMATO),
                estado
            });
        }
    }

    private void registrarCita() {
        try {
            if (cbCliente.getItemCount()==0 || cbProfesional.getItemCount()==0 || cbServicio.getItemCount()==0) {
                JOptionPane.showMessageDialog(this,"Asegúrate de tener clientes, profesionales y servicios registrados.");
                return;
            }

            String selCli = (String) cbCliente.getSelectedItem();
            String selProf = (String) cbProfesional.getSelectedItem();
            String selServ = (String) cbServicio.getSelectedItem();

            int idCli = Integer.parseInt(selCli.split(" - ")[0].trim());
            int idProf = Integer.parseInt(selProf.split(" - ")[0].trim());
            int idServ = Integer.parseInt(selServ.split(" - ")[0].trim());

            Cliente cliente = agenda.buscarClientePorId(idCli);
            Profesional profesional = agenda.buscarProfesionalPorId(idProf);
            Servicio servicio = agenda.buscarServicioPorId(idServ);

            int dia = Integer.parseInt(txtDia.getText());
            int mes = Integer.parseInt(txtMes.getText());
            int anio = Integer.parseInt(txtAnio.getText());
            int hora = Integer.parseInt(txtHora.getText());
            int minuto = Integer.parseInt(txtMinuto.getText());

            LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, minuto);

            int id = agenda.obtenerProximoIdCita();
            Cita cita = new Cita(id, cliente, profesional, servicio, fechaHora);
            agenda.agregarCita(cita);
            GestorArchivos.guardarCitas(agenda.getCitas());

            refreshTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this,"Cita registrada correctamente.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,"Error al registrar cita. Verifique los datos ingresados.");
            ex.printStackTrace();
        }
    }

    private void eliminarCita(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita para eliminar.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        Cita rem = null;

        for (Cita c : agenda.getCitas())
            if (c.getId() == id) { rem = c; break; }

        if (rem != null) {
            agenda.getCitas().remove(rem);
            GestorArchivos.guardarCitas(agenda.getCitas());
            refreshTabla();
            JOptionPane.showMessageDialog(this, "Cita eliminada correctamente.");
        }
    }

    private void generarFactura(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita.");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        for (Cita c : agenda.getCitas()) {
            if (c.getId() == id) {

                Factura factura = new Factura(
                    agenda.obtenerProximoIdFactura(),
                    c.getCliente(),
                    c.getProfesional(),
                    c.getServicio(),
                    c.getFechaHora(),
                    c.getServicio().getPrecio(),
                    c.getServicio().getPrecio()
                );

                agenda.agregarFactura(factura);
                GestorArchivos.guardarFacturas(agenda.getFacturas());

                com.mycompany.gestor_citas.Auxiliares.GeneradorReportesPDF
                    .generarFacturaPDF(factura);

                JOptionPane.showMessageDialog(this,
                    "Factura generada y guardada como PDF.\nID: " + factura.getId());

                return;
            }
        }
    }

    private void limpiarCampos() {
        txtDia.setText("");
        txtMes.setText("");
        txtAnio.setText("");
        txtHora.setText("");
        txtMinuto.setText("");
    }
}