package view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.*;




public class MenuFrame extends JFrame implements ActionListener {
    private final JPanel mainPanel, logoPanel;
    private final JLabel  helloLabel, logoLabel;


    private final JButton newSaleButton, searchSaleButton, manageProductsButton, settingsButton, logoutButton;
    private final Dimension buttonsDimension = new Dimension(200, 25), helloDimension = new Dimension(220, 25),
            mainPanelDimension = new Dimension((int) buttonsDimension.getWidth() + 40, 0);
    private final Color mainPanelColor = Color.white, logoPanelColor = Color.cyan, buttonColor = Color.black;
    private final ImageIcon helloIcon = new ImageIcon("images\\icons\\username_icon.png"),
            logoIcon = new ImageIcon("images\\icons\\cash_register_icon.jpg"),
            newSaleIcon = new ImageIcon("images\\icons\\shopping_cart_icon.png"),
            searchSaleIcon = new ImageIcon("images\\icons\\search_icon.png"),
            manageUsersIcon = new ImageIcon("images\\icons\\user_icon.png"),
            manageProductsIcon = new ImageIcon("images\\icons\\product_icon.png"),
            settingsIcon = new ImageIcon("images\\icons\\settings_icon.png"),
            logoutIcon = new ImageIcon("images\\icons\\logout_icon.png");





    MenuFrame() throws SQLException {
        /*************************************** Setup ****************************************/



        /*************************************** Setup ****************************************/
        /*************************************** Frame ***************************************/

        this.setTitle("Menu");
        this.setSize(1000, 720);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
        mainPanel.setPreferredSize(mainPanelDimension);
        mainPanel.setBackground(mainPanelColor);
        this.add(mainPanel, BorderLayout.WEST);

        logoPanel = new JPanel(new GridBagLayout());
        logoPanel.setBackground(logoPanelColor);
        this.add(logoPanel, BorderLayout.CENTER);

        /*************************************** Frame ***************************************/
        /*************************************** Icon ****************************************/



        helloLabel = new JLabel("Xin chào, người bán" , SwingConstants.CENTER);
        helloLabel.setPreferredSize(helloDimension);
        helloLabel.setFont(new Font("Calibri", Font.BOLD, 13));
        mainPanel.add(helloLabel);

        /*************************************** Icon ****************************************/
        /*************************************** Logo ****************************************/

        logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)));
        logoPanel.add(logoLabel, new GridBagConstraints());

        /*************************************** Logo ****************************************/
        /************************************* Buttons **************************************/

        newSaleButton = new JButton("New sale");
        setLineDesign(newSaleIcon, newSaleButton);

        searchSaleButton = new JButton("Search sale");
        setLineDesign(searchSaleIcon, searchSaleButton);



        manageProductsButton = new JButton("Manage products");
        setLineDesign(manageProductsIcon, manageProductsButton);

        settingsButton = new JButton("Settings");
        setLineDesign(settingsIcon, settingsButton);

        logoutButton = new JButton("Logout");
        setLineDesign(logoutIcon, logoutButton);

        /************************************* Buttons **************************************/

        this.setVisible(true);
    }

    private void setLineDesign(ImageIcon icon, JButton button) {
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        mainPanel.add(label);

        button.setPreferredSize(buttonsDimension);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createLineBorder(buttonColor));
        button.setBackground(mainPanelColor);
        button.setForeground(buttonColor);
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
        mainPanel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            if (event.getSource().equals(newSaleButton)) {
                MainSellView frame = new MainSellView();
                frame.setVisible(true);
                this.dispose();
            } else if (event.getSource().equals(searchSaleButton)) {
//                new SearchSaleFrame();
                this.dispose();
            } else if (event.getSource().equals(manageProductsButton)) {

                    new ManageProductsView();
                    this.dispose();

            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
