package view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.SQLException;
import java.util.Random;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import model.Product;
import model.dao.ProductDAO;

public class ManageProductsView extends JFrame implements ActionListener {
    private final JPanel bottomPanel, inputsPanel, tablePanel;
    private final JLabel idLabel, nameLabel,  priceLabel, categoryLabel,instructionLabel;
    private final JLabel imageLabel,statusLabel,descriptionLabel;
    private final JTextField idTextField, nameTextField, priceTextField,imageTextField,statusTextField,descriptionTextField;
    private final JComboBox<String> categoryComboBox;
    private final JButton backButton, createProductButton,deleteProductButton;
    private final Dimension labelDimension = new Dimension(85, 20), inputBoxDimension = new Dimension(180, 20),
            inputPanelDimension = new Dimension((int)(labelDimension.getWidth() + inputBoxDimension.getWidth()) + 20, 0),
            tableDimension = new Dimension(690, 600), buttonsDimension = new Dimension(105, 25);
    private final Color mainColor = Color.white, inputColor = Color.black;
    private final DefaultTableModel tableModel;
    private final JTable productDataTable;
    private final JScrollPane scrollPane;
    private Object[][] productData;
    private final String[] tableColumns, categoriesArray;
    private final ProductDAO productDAO;

    ManageProductsView() throws SQLException{
        productDAO = new ProductDAO();

        /****************************** Frame ******************************/

        this.setTitle("Manage products");
        this.setSize(1000, 720);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        inputsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        inputsPanel.setPreferredSize(inputPanelDimension);
        inputsPanel.setBackground(mainColor);
        this.add(inputsPanel, BorderLayout.WEST);

        tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(mainColor);
        this.add(tablePanel, BorderLayout.CENTER);

        /****************************** Frame ******************************/
        /****************************** Input ******************************/

        idLabel = new JLabel("Product ID");
        idTextField = new JTextField();
        setTextFieldDesign(idLabel, idTextField);

        nameLabel = new JLabel("Name");
        nameTextField = new JTextField();
        setTextFieldDesign(nameLabel, nameTextField);

        imageLabel= new JLabel("Image Url");
        imageTextField = new JTextField();
        setTextFieldDesign(imageLabel, imageTextField);

        priceLabel = new JLabel("Price (VND)");
        priceTextField = new JTextField();
        setTextFieldDesign(priceLabel, priceTextField);

        categoryLabel = new JLabel("Category");
        categoryLabel.setPreferredSize(labelDimension);
        categoryLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        inputsPanel.add(categoryLabel);

        categoriesArray = new String[]{"Food", "Drinks"};

        categoryComboBox = new JComboBox<>(categoriesArray);
        categoryComboBox.setPreferredSize(inputBoxDimension);
        categoryComboBox.setFocusable(false);
        inputsPanel.add(categoryComboBox);

        statusLabel= new JLabel("Status");
        statusTextField = new JTextField();
        setTextFieldDesign(statusLabel, statusTextField);

        descriptionLabel= new JLabel("Description");
        descriptionTextField = new JTextField();
        setTextFieldDesign(descriptionLabel, descriptionTextField);
        /****************************** Input ******************************/
        /***************************** Buttons *****************************/

        createProductButton = new JButton("Create product");
        setButtonDesign(createProductButton);
        inputsPanel.add(createProductButton);

//        updateProductButton = new JButton("Update Product");
//        setButtonDesign(updateProductButton);
//        inputsPanel.add(updateProductButton);

        instructionLabel = new JLabel("Select from table to delete");
        instructionLabel.setFont(new Font("Calibri", Font.BOLD, 10));
        inputsPanel.add(instructionLabel);

        deleteProductButton = new JButton("Delete Product");
        setButtonDesign(deleteProductButton);
        inputsPanel.add(deleteProductButton);

        /***************************** Buttons *****************************/
        /****************************** Table ******************************/

        productData = productDAO.readProductsTableData();
        tableColumns = new String[]{"ID", "Name","Image", "Price","Category","Status","Description"};
        tableModel = new DefaultTableModel(productData, tableColumns);

        productDataTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        scrollPane = new JScrollPane(productDataTable);
        scrollPane.setPreferredSize(tableDimension);
        tablePanel.add(scrollPane, new GridBagConstraints());

        /****************************** Table ******************************/
        /****************************** Frame ******************************/

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 5));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.setBackground(mainColor);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        setButtonDesign(backButton);
        bottomPanel.add(backButton);

        this.setVisible(true);

        /****************************** Frame ******************************/
    }

    private void setTextFieldDesign(JLabel label, JTextField textField){
        label.setPreferredSize(labelDimension);
        label.setFont(new Font("Calibri", Font.BOLD, 14));
        inputsPanel.add(label);

        textField.setPreferredSize(inputBoxDimension);
        textField.setForeground(inputColor);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, inputColor));
        inputsPanel.add(textField);
    }

    private void setButtonDesign(JButton button){
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


    private boolean isBoxesEmpty() {
        return (idTextField.getText().isBlank() || nameTextField.getText().isBlank()
                || priceTextField.getText().isBlank() || categoryComboBox.getSelectedItem() == null);
    }

    private void emptyBoxes() {
        idTextField.setText(null);
        idTextField.requestFocus();
        nameTextField.setText(null);
        priceTextField.setText(null);
        imageTextField.setText(null);
        statusTextField.setText(null);
        descriptionTextField.setText(null);
        categoryComboBox.setSelectedIndex(0);
    }

    private void updateTable() {
        int currentRowCount = tableModel.getRowCount();
        tableModel.setRowCount(0);
        tableModel.setRowCount(currentRowCount);
        tableModel.setDataVector(productData, tableColumns);
    }
    private void dbCreateProduct(Product product) {
        if (isBoxesEmpty()) {
            JOptionPane.showMessageDialog(null, "You must fill all text fields!",
                    "Input error", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                // Use Case ThĂªm mĂ³n Äƒn (20130217-BĂ¹i Thanh Ä�áº£m)
                // BÆ°á»›c 1.4 : Há»‡ thĂ´ng ghi nháº­n mĂ³n Äƒn vĂ o ProductDAO
                if (productDAO.createProduct(product)){
                    productData = productDAO.readProductsTableData();
                    // Use Case ThĂªm mĂ³n Äƒn (20130217-BĂ¹i Thanh Ä�áº£m)
                    // BÆ°á»›c 1.6 : Há»‡ thĂ´ng pháº£n há»“i thĂªm mĂ³n Äƒn thĂ nh cĂ´ng
                    JOptionPane.showMessageDialog(null, "This product has been created successfully!",
                            "Product created", JOptionPane.INFORMATION_MESSAGE);
                    emptyBoxes();
                    updateTable();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Someting went wrong!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void dbDeleteProduct() {

        if (productDataTable.getSelectionModel().isSelectionEmpty()) {
            JOptionPane.showMessageDialog(null, "You must pick a line from the table!",
                    "Delete error", JOptionPane.WARNING_MESSAGE);
        } else {
            // Use Case XĂ³a mĂ³n Äƒn (20130217-BĂ¹i Thanh Ä�áº£m)
            // BÆ°á»›c 2.5 : Há»‡ thá»‘ng hiá»ƒn thá»‹ há»™p thoáº¡i xĂ¡c nháº­n
            String[] options = {"Yes", "No"};
            int option = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this product?", "Delete product",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            // Use Case XĂ³a mĂ³n Äƒn (20130217-BĂ¹i Thanh Ä�áº£m)
            // BÆ°á»›c 2.6 : NhĂ¢n viĂªn chá»�n vĂ o nĂºt cĂ³ Ä‘á»ƒ xĂ³a
            if (option == 0) {
                try {
                    // Use Case XĂ³a mĂ³n Äƒn (20130217-BĂ¹i Thanh Ä�áº£m)
                    // BÆ°á»›c 2.7 : Há»‡ thá»‘ng xĂ¡c thá»±c thĂ´ng tin mĂ³n Äƒn bá»‹ xĂ³a

                    int selectedProduct = Integer.parseInt((String) productData[productDataTable.getSelectedRow()][0]);
                    if (productDAO.deleteProduct(selectedProduct)) {
                        productData = productDAO.readProductsTableData();
                        // Use Case XĂ³a mĂ³n Äƒn (20130217-BĂ¹i Thanh Ä�áº£m)
                        // BÆ°á»›c 2.9 : Há»‡ thá»‘ng pháº£n há»“i thĂ´ng tin xĂ³a thĂ nh cĂ´ng
                        JOptionPane.showMessageDialog(null, "This product has been deleted successfully!",
                                "Product deleted", JOptionPane.INFORMATION_MESSAGE);
                        emptyBoxes();
                        updateTable();
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Someting went wrong!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        // Use Case ThĂªm mĂ³n Äƒn (20130217-BĂ¹i Thanh Ä�áº£m)
        // BÆ°á»›c 1.3 : Khi nhĂ¢n viĂªn nháº¥n vĂ o nĂºt ThĂªm mĂ³n Äƒn thĂ´ng tin trong cĂ¡c input sáº½ Ä‘Æ°á»£c táº¡o
        if (event.getSource().equals(createProductButton)) {
            Product product = new Product(
                    idTextField.getText().isBlank() ? new Random().nextInt(15,30) : Integer.parseInt(idTextField.getText()),
                    nameTextField.getText(),
                    imageTextField.getText(),
                    Float.parseFloat(priceTextField.getText()),
                    (String) categoryComboBox.getSelectedItem(),
                    Integer.parseInt(statusTextField.getText()),
                    descriptionTextField.getText());
            dbCreateProduct(product);
        }
        // Use Case XĂ³a mĂ³n Äƒn (20130217-BĂ¹i Thanh Ä�áº£m)
        // BÆ°á»›c 2.3 : NhĂ¢n viĂªn chá»�n vĂ o mĂ³n Äƒn cáº§n chá»�n

        else if (event.getSource().equals(deleteProductButton)) {
            // Use Case XĂ³a mĂ³n Äƒn (20130217-BĂ¹i Thanh Ä�áº£m)
            // BÆ°á»›c 2.4 : NhĂ¢n viĂªn chá»�n vĂ o nĂºt xĂ³a mĂ³n Äƒn
            dbDeleteProduct();
        }
        else if (event.getSource().equals(backButton)) {
            try {
                productDAO.close();
                new MenuFrame();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            this.dispose();
        }
    }
}
