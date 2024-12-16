package views;

import models.Car;
import models.Rental;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Dashboard extends JFrame {
    private final ArrayList<Car> cars = new ArrayList<>();
    private final ArrayList<Rental> rentals = new ArrayList<>();


    private JComboBox<String> carDropdown;
    private JTextField totalPriceField;
    private JTable availableCarsTable;
    private DefaultTableModel availableCarsTableModel;
    private JTable rentedCarsTable;
    private DefaultTableModel rentedCarsTableModel;


    public Dashboard() {
        setTitle("Dashboard - Rental Mobil");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.add(createAddCarPanel(), "Tambah Mobil");
        contentPanel.add(createRentCarPanel(), "Penyewaan Mobil");
        contentPanel.add(createAvailableCarsPanel(), "Mobil Tersedia");
        contentPanel.add(createRentedCarsPanel(), "Mobil Disewa");

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1, 10, 10));
        menuPanel.setBackground(new Color(239, 230, 210));

        JButton addCarButton = new JButton("Tambah Mobil");
        JButton rentCarButton = new JButton("Penyewaan Mobil");
        JButton availableCarsButton = new JButton("Mobil Tersedia");
        JButton rentedCarsButton = new JButton("Mobil Disewa");

        menuPanel.add(addCarButton);
        menuPanel.add(rentCarButton);
        menuPanel.add(availableCarsButton);
        menuPanel.add(rentedCarsButton);

        addCarButton.addActionListener(e -> switchPanel(contentPanel, "Tambah Mobil"));
        rentCarButton.addActionListener(e -> switchPanel(contentPanel, "Penyewaan Mobil"));
        availableCarsButton.addActionListener(e -> {
            updateAvailableCarsTable();
            switchPanel(contentPanel, "Mobil Tersedia");
        });

        rentedCarsButton.addActionListener(e -> {
            updateRentedCarsTable();
            switchPanel(contentPanel, "Mobil Disewa");
        });

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createAddCarPanel() {

        JPanel addCarPanel = new JPanel(new GridBagLayout());
        addCarPanel.setBackground(new Color(239, 230, 210));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel plateLabel = new JLabel("Nomor Plat:");
        JTextField plateField = new JTextField(30);
        plateField.setPreferredSize(new Dimension(300, 40));

        JLabel typeLabel = new JLabel("Jenis Mobil:");
        JTextField typeField = new JTextField(30);
        typeField.setPreferredSize(new Dimension(300, 40));

        JLabel colorLabel = new JLabel("Warna:");
        JTextField colorField = new JTextField(30);
        colorField.setPreferredSize(new Dimension(300, 40));

        JLabel priceLabel = new JLabel("Harga per Hari:");
        JTextField priceField = new JTextField(30);
        priceField.setPreferredSize(new Dimension(300, 40));

        JButton saveCarButton = new JButton("Simpan");
        saveCarButton.setPreferredSize(new Dimension(300, 40));

        gbc.gridx = 0; gbc.gridy = 0; addCarPanel.add(plateLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; addCarPanel.add(plateField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; addCarPanel.add(typeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; addCarPanel.add(typeField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; addCarPanel.add(colorLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; addCarPanel.add(colorField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; addCarPanel.add(priceLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; addCarPanel.add(priceField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; addCarPanel.add(saveCarButton, gbc);

        saveCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plate = plateField.getText();
                String type = typeField.getText();
                String color = colorField.getText();
                int pricePerDay;

                try {
                    pricePerDay = Integer.parseInt(priceField.getText());
                    cars.add(new Car(plate, type, color, pricePerDay));
                    updateCarDropdown();

                    JOptionPane.showMessageDialog(Dashboard.this, "Mobil berhasil ditambahkan!");
                    plateField.setText("");
                    typeField.setText("");
                    colorField.setText("");
                    priceField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Dashboard.this, "Harga per hari harus berupa angka.");
                }
            }
        });
        return addCarPanel;
    }
    private JPanel createRentCarPanel() {

        JPanel rentCarPanel = new JPanel(new GridBagLayout());
        rentCarPanel.setBackground(new Color(239, 230, 210));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel customerLabel = new JLabel("Nama Penyewa:");
        JTextField customerField = new JTextField(20);
        customerField.setPreferredSize(new Dimension(300, 40));

        JLabel addressLabel = new JLabel("Alamat:");
        JTextField addressField = new JTextField(20);
        addressField.setPreferredSize(new Dimension(300, 40));

        JLabel phoneLabel = new JLabel("Nomor HP:");
        JTextField phoneField = new JTextField(20);
        phoneField.setPreferredSize(new Dimension(300, 40));

        JLabel daysLabel = new JLabel("Lama Hari:");
        JTextField daysField = new JTextField(20);
        daysField.setPreferredSize(new Dimension(300, 40));

        JLabel chooseCarLabel = new JLabel("Pilih Mobil:");
        chooseCarLabel.setPreferredSize(new Dimension(300, 40));

        carDropdown = new JComboBox<>();
        carDropdown.setPreferredSize(new Dimension(300, 40));

        JButton calculateButton = new JButton("Hitung");
        calculateButton.setPreferredSize(new Dimension(300, 40));

        JLabel totalPriceLabel = new JLabel("Total Harga:");
        totalPriceField = new JTextField(20);
        totalPriceField.setEditable(false);
        totalPriceField.setPreferredSize(new Dimension(300, 40));

        JButton rentButton = new JButton("Sewa");
        rentButton.setPreferredSize(new Dimension(300, 40));

        gbc.gridx = 0; gbc.gridy = 0; rentCarPanel.add(customerLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; rentCarPanel.add(customerField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; rentCarPanel.add(addressLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; rentCarPanel.add(addressField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; rentCarPanel.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; rentCarPanel.add(phoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; rentCarPanel.add(daysLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; rentCarPanel.add(daysField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; rentCarPanel.add(chooseCarLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; rentCarPanel.add(carDropdown, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; rentCarPanel.add(calculateButton, gbc);
        gbc.gridx = 0; gbc.gridy = 6; rentCarPanel.add(totalPriceLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 6; rentCarPanel.add(totalPriceField, gbc);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; rentCarPanel.add(rentButton, gbc);

        calculateButton.addActionListener(e -> {
            int selectedIndex = carDropdown.getSelectedIndex();
            if (selectedIndex >= 0) {
                try {
                    int days = Integer.parseInt(daysField.getText());
                    int pricePerDay = cars.get(selectedIndex).getHargaPerHari();
                    totalPriceField.setText(String.valueOf(days * pricePerDay));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Dashboard.this, "Lama hari harus berupa angka.");
                }
            } else {
                JOptionPane.showMessageDialog(Dashboard.this, "Pilih mobil terlebih dahulu.");
            }
        });

        rentButton.addActionListener(e -> {
            String customerName = customerField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            int rentalDays;

            try {
                rentalDays = Integer.parseInt(daysField.getText());
                int selectedIndex = carDropdown.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < cars.size()) {
                    Car car = cars.get(selectedIndex);
                    if (!car.isRented()) {
                        car.setRented(true);

                        rentals.add(new Rental(car.getPlateNumber(), car.getType(), car.getColor(), customerName,
                                car.getHargaPerHari(), address, phone, rentalDays));
                        JOptionPane.showMessageDialog(Dashboard.this, "Mobil berhasil disewa!\nTotal Harga: " + (rentalDays * car.getHargaPerHari()));

                        customerField.setText("");
                        addressField.setText("");
                        phoneField.setText("");
                        daysField.setText("");
                        totalPriceField.setText("");
                        updateCarDropdown();

                    } else {
                        JOptionPane.showMessageDialog(Dashboard.this, "Mobil ini sedang disewa.");
                    }
                } else {
                    JOptionPane.showMessageDialog(Dashboard.this, "Pilih mobil terlebih dahulu.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Dashboard.this, "Lama hari harus berupa angka.");
            }
        });
        return rentCarPanel;
    }

    private JPanel createAvailableCarsPanel() {

        JPanel availableCarsPanel = new JPanel(new BorderLayout());
        availableCarsPanel.setBackground(new Color(239, 230, 210));

        availableCarsTableModel = new DefaultTableModel(new Object[]{"      Nomor Plat", "      Jenis", "      Warna", "      Harga per Hari"}, 0);
        availableCarsTable = new JTable(availableCarsTableModel);
        availableCarsTable.setBackground(new Color(239, 230, 210));
        availableCarsTable.setForeground(Color.BLACK);
        availableCarsTable.setRowHeight(30);

        TableColumnModel columnModel = availableCarsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(availableCarsTable);
        availableCarsPanel.add(scrollPane, BorderLayout.CENTER);

        JTableHeader header = availableCarsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(217, 195, 175));
        header.setAlignmentX(SwingConstants.CENTER);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));

        availableCarsTable.setFillsViewportHeight(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Center alignment

        for (int i = 0; i < availableCarsTable.getColumnModel().getColumnCount(); i++) {
            availableCarsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");

        editButton.setPreferredSize(new Dimension(150, 40));
        deleteButton.setPreferredSize(new Dimension(150, 40));
        editButton.addActionListener(e -> {

            int selectedRow = availableCarsTable.getSelectedRow();

            if (selectedRow >= 0) {
                String plateNumber = (String) availableCarsTableModel.getValueAt(selectedRow, 0);
                Car carToEdit = findCarByPlateNumber(plateNumber);
                if (carToEdit != null) {
                    JTextField plateField = new JTextField(carToEdit.getPlateNumber());
                    JTextField typeField = new JTextField(carToEdit.getType());
                    JTextField colorField = new JTextField(carToEdit.getColor());
                    JTextField priceField = new JTextField(String.valueOf(carToEdit.getHargaPerHari()));

                    JPanel editPanel = new JPanel(new GridLayout(4, 2));

                    editPanel.add(new JLabel("Nomor Plat:"));
                    editPanel.add(plateField);
                    editPanel.add(new JLabel("Jenis Mobil:"));
                    editPanel.add(typeField);
                    editPanel.add(new JLabel("Warna:"));
                    editPanel.add(colorField);
                    editPanel.add(new JLabel("Harga per Hari:"));
                    editPanel.add(priceField);

                    int result = JOptionPane.showConfirmDialog(Dashboard.this, editPanel, "Edit Mobil", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        carToEdit.setPlateNumber(plateField.getText());
                        carToEdit.setType(typeField.getText());
                        carToEdit.setColor(colorField.getText());
                        carToEdit.setHargaPerHari(Integer.parseInt(priceField.getText()));
                        updateAvailableCarsTable();
                        updateCarDropdown();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(Dashboard.this, "Pilih mobil terlebih dahulu.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = availableCarsTable.getSelectedRow();
            if (selectedRow >= 0) {
                String plateNumber = (String) availableCarsTableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(Dashboard.this, "Apakah Anda yakin ingin menghapus mobil ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Car carToDelete = findCarByPlateNumber(plateNumber);
                    if (carToDelete != null) {
                        cars.remove(carToDelete);
                        updateAvailableCarsTable();
                        updateCarDropdown();
                        JOptionPane.showMessageDialog(Dashboard.this, "Mobil berhasil dihapus!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(Dashboard.this, "Pilih mobil terlebih dahulu.");
            }
        });
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        availableCarsPanel.add(buttonPanel, BorderLayout.SOUTH);
        return availableCarsPanel;
    }

    private JPanel createRentedCarsPanel() {
        JPanel rentedCarsPanel = new JPanel(new BorderLayout());
        rentedCarsPanel.setBackground(new Color(239, 230, 210));

        rentedCarsTableModel = new DefaultTableModel(new Object[]{"   Nomor Plat", "   Jenis", "   Penyewa", "   Lama Sewa", "   Total Harga"}, 0);
        rentedCarsTable = new JTable(rentedCarsTableModel);
        rentedCarsTable.setBackground(new Color(239, 230, 210));
        rentedCarsTable.setForeground(Color.BLACK);
        rentedCarsTable.setRowHeight(30);

        TableColumnModel columnModel = rentedCarsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(150);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < rentedCarsTable.getColumnModel().getColumnCount(); i++) {
            rentedCarsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(rentedCarsTable);
        rentedCarsPanel.add(scrollPane, BorderLayout.CENTER);

        JTableHeader header = rentedCarsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(217, 195, 175));
        header.setAlignmentX(SwingConstants.CENTER);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        rentedCarsTable.setFillsViewportHeight(true);

        JButton finishButton = new JButton("Selesai");
        finishButton.setPreferredSize(new Dimension(150, 50));

        finishButton.addActionListener(e -> {
            int selectedRow = rentedCarsTable.getSelectedRow();
            if (selectedRow >= 0) {
                String plateNumber = (String) rentedCarsTableModel.getValueAt(selectedRow, 0);

                for (Rental rental : rentals) {
                    if (rental.getPlateNumber().equals(plateNumber)) {
                        rentals.remove(rental);
                        updateAvailableCarsAfterReturn(rental);
                        updateCarDropdown();
                        JOptionPane.showMessageDialog(Dashboard.this, "Mobil berhasil dikembalikan!");
                        updateRentedCarsTable();
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(Dashboard.this, "Pilih mobil terlebih dahulu.");
            }
        });
        rentedCarsPanel.add(finishButton, BorderLayout.SOUTH);
        return rentedCarsPanel;
    }

    private void updateAvailableCarsAfterReturn(Rental rental) {
        for (Car car : cars) {
            if (car.getPlateNumber().equals(rental.getPlateNumber())) {
                car.setRented(false);
                break;
            }
        }
    }

    private void updateRentedCarsTable() {
        rentedCarsTableModel.setRowCount(0);
        for (Rental rental : rentals) {
            rentedCarsTableModel.addRow(new Object[]{
                    rental.getPlateNumber(),
                    rental.getType(),
                    rental.getCustomerName(),
                    rental.getRentalDays(),
                    rental.getRentalDays() * rental.getHargaPerHari() // Total price calculation
            });
        }
    }

    private void updateAvailableCarsTable() {
        availableCarsTableModel.setRowCount(0);
        for (Car car : cars) {
            if (!car.isRented()) {
                availableCarsTableModel.addRow(new Object[]{
                        car.getPlateNumber(),
                        car.getType(),
                        car.getColor(),
                        car.getHargaPerHari()
                });
            }
        }
    }

    private void updateCarDropdown() {
        carDropdown.removeAllItems();
        cars.forEach(car -> {
            if (!car.isRented()) {
                carDropdown.addItem(car.getPlateNumber() + " - " + car.getType());
            }
        });
        if (carDropdown.getItemCount() == 0) {
            carDropdown.addItem("Tidak ada mobil tersedia");
        }
    }

    private Car findCarByPlateNumber(String plateNumber) {
        for (Car car : cars) {
            if (car.getPlateNumber().equals(plateNumber)) {
                return car;
            }
        }
        return null;
    }

    private void switchPanel(JPanel contentPanel, String panelName) {
        CardLayout cl = (CardLayout) (contentPanel.getLayout());
        cl.show(contentPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dashboard::new);
    }
}