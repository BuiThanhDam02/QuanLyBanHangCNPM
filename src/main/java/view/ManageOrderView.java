package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import model.OrdersDetail;
import model.dao.OrderDAO;
import model.dao.OrderDetailDAO;

public class ManageOrderView extends JFrame implements ActionListener {
    private final JPanel bottomPanel, topPanel, tablePanel;
    private final JLabel startDateLabel;
    private final JTextField startDateTextField;
    private final JButton backButton, searchButton;
    private final Dimension inputBoxDimension = new Dimension(100, 20),
            tableDimension = new Dimension(800, 500), buttonsDimension = new Dimension(100, 25);
    private final Color mainColor = Color.white, inputColor = Color.black;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JScrollPane scrollPane;
    private Object[][] tableData;
    private final String[] tableColumns = {"ID","Date","Num of Product" ,"Total cost", "Status", "Description"};
    private final OrderDAO orderDAO;

    ManageOrderView() throws SQLException {
        orderDAO = new OrderDAO();

        /****************************** Frame ******************************/

        this.setTitle("Manage Order");
        this.setSize(1000, 720);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(mainColor);
        this.add(tablePanel, BorderLayout.CENTER);

        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setPreferredSize(new Dimension(0, 50));
        topPanel.setBackground(mainColor);
        this.add(topPanel, BorderLayout.NORTH);

        /****************************** Frame ******************************/
        /****************************** Input ******************************/

        startDateLabel = new JLabel("Id Order");
        startDateLabel.setPreferredSize(new Dimension(60, 20));
        startDateLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        topPanel.add(startDateLabel);

        startDateTextField = new JTextField();
        startDateTextField.setPreferredSize(inputBoxDimension);
        startDateTextField.setForeground(inputColor);
        startDateTextField.setBorder(BorderFactory.createLineBorder(inputColor));
        topPanel.add(startDateTextField);


        /****************************** Input ******************************/
        /***************************** Buttons *****************************/

        searchButton = new JButton("Delete order");
        setButtonDesign(searchButton);
        topPanel.add(searchButton);



        /***************************** Buttons *****************************/
        /**************************** Sale Table ****************************/

        tableModel = new DefaultTableModel(null, tableColumns);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(tableDimension);
        tablePanel.add(scrollPane, new GridBagConstraints());

        /**************************** Sale Table ****************************/
        /****************************** Frame ******************************/
        searchTableData();
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.setBackground(mainColor);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        setButtonDesign(backButton);
        bottomPanel.add(backButton);

        this.setVisible(true);

        /****************************** Frame ******************************/
    }

    private void setButtonDesign(JButton button) {
        button.setPreferredSize(buttonsDimension);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createLineBorder(inputColor));
        button.setBackground(mainColor);
        button.setForeground(inputColor);
        button.addActionListener(this);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent event) {
                button.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    private void updateTable() {
        int currentRowCount = tableModel.getRowCount();
        tableModel.setRowCount(0);
        tableModel.setRowCount(currentRowCount);
        tableModel.setDataVector(tableData, tableColumns);
    }

    private void searchTableData() {


        try {
            tableData = orderDAO.readOrdersTableData();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        updateTable();
    }


    public void delOrder(int orderId) throws SQLException {
        orderDAO.deleteOrder(orderId);
        OrderDetailDAO d = new OrderDetailDAO();
        d.deleteOrderDetail(orderId);

    }
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource().equals(searchButton)) {
          String id =   startDateTextField.getText();
            int idOrdrer = Integer.parseInt(id);
            try {
                delOrder(idOrdrer);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            searchTableData();
        } else if(event.getSource().equals(backButton)) {
            try {
                new MenuFrame();
            } catch ( SQLException exception) {
                exception.printStackTrace();
            }
            this.dispose();
        }
    }
}
