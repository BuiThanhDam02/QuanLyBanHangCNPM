package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import model.dao.ProductDAO;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainSellView extends JFrame implements ActionListener {


	private final Color mainColor = Color.white, inputColor = Color.black;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel table_1;

	private final JTable orderDetailDataTable;
	private ProductDAO productDAO;
	private JButton backButton;
	private List<String> list =  new ArrayList<String>();
	private  String[] detailOrderColumn;
	private String[][] dataDetailOrder;
	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public MainSellView() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 20, 1135, 720);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Phần Mềm Hỗ Trợ Bán Hàng");
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_button = new JPanel();
		panel_button.setBackground(Color.WHITE);
		panel_button.setBounds(0, 0, 110, 720);
		contentPane.add(panel_button);
		panel_button.setLayout(null);
		backButton = new JButton("Back");
		setButtonDesign(backButton);
		panel_button.add(backButton);


		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(110, 0, 683, 683);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		productDAO = new ProductDAO();
		String column[] = { "ID", "NAME", "Image", "Price", "Category", "Status", "Description" };
		String[][] productData = productDAO.readProductsTableData();
		table = new JTable(productData, column);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));


		dataDetailOrder = choseProductToOrder(list);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				String t = JOptionPane.showInputDialog(panel_1, "Nhập số lượng sản phẩm");
				if(t!= null) {
					try {
						int qty = Integer.parseInt(t);
						if(qty<0) {
							JOptionPane.showMessageDialog(panel_1, "Số lượng sản phẩm phải lớn hơn 0!");
						}else {
							String id = productData[table.getSelectedRow()][0];
							String name =  productData[table.getSelectedRow()][1];
							String image = productData[table.getSelectedRow()][2];
							double price = Double.parseDouble( productData[table.getSelectedRow()][3]);
							String category = productData[table.getSelectedRow()][4];
							String status = productData[table.getSelectedRow()][5];
							String description = productData[table.getSelectedRow()][6];
							list.add( name +"-" + price + "-" + qty + "-" + price*qty);
							System.out.println(list);

						}
						dataDetailOrder = choseProductToOrder(list);
						updateTable();
					} catch (NumberFormatException e ) {
						JOptionPane.showMessageDialog(panel_1, "Số lượng sản phẩm phải là chữ số!");
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 683, 683);
		panel_1.add(scrollPane);





		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(794, 0, 319, 510);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("Chi Tiết Đơn Hàng");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 10, 299, 47);
		panel_2.add(lblNewLabel);

		 detailOrderColumn = new String[]{"Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Tổng"};

		table_1 = new DefaultTableModel(choseProductToOrder(list), detailOrderColumn);
		orderDetailDataTable = new JTable(table_1) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		orderDetailDataTable.setFont(new Font("Tahoma", Font.PLAIN, 15));

//		table_1.addMouseListener(new MouseAdapter(){
//      @Override
//      public void mouseClicked(MouseEvent event){
////          	dataDetailOrder[table_1.getSelectedRow()][0]="";
////          	dataDetailOrder[table_1.getSelectedRow()][1]="";
////          	dataDetailOrder[table_1.getSelectedRow()][2]="";
//      }
//  });

		JScrollPane scrollPane_1 = new JScrollPane(orderDetailDataTable);
		scrollPane_1.setBounds(10, 63, 299, 314);
		panel_2.add(scrollPane_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 387, 299, 113);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(31, 32, 45, 13);
		panel_3.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(31, 77, 45, 13);
		panel_3.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(193, 32, 45, 13);
		panel_3.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(193, 77, 45, 13);
		panel_3.add(lblNewLabel_4);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(794, 520, 309, 163);
		contentPane.add(panel_4);
		panel_4.setLayout(null);

		JButton btnNewButton_2 = new JButton("Xác Nhận Thanh Toán");
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.setForeground(Color.BLUE);
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_2.setBounds(10, 26, 289, 52);
		panel_4.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Hủy");
		btnNewButton_3.setForeground(Color.RED);
		btnNewButton_3.setBackground(Color.WHITE);
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_3.setBounds(10, 88, 289, 52);
		panel_4.add(btnNewButton_3);


		
	}
	private void updateTable() {
		int currentRowCount = table_1.getRowCount();
		table_1.setRowCount(0);
		table_1.setRowCount(currentRowCount);
		table_1.setDataVector(dataDetailOrder, detailOrderColumn);
	}
	private void setButtonDesign(JButton button){
		button.setBounds(0,0,110,25);
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
	public void addDetaiOrder() {
	}
	
	public void addOrder() {
		
	}
	
	
	// update product in Order
	public String[][] choseProductToOrder(List<String> li){
		String[][] arr = new String[li.size()][4];
		if(li.size() == 0) {
			return new String[][]{ { "", "", "", "" } };
		}else {
			for(int i = 0;i<li.size(); i++) {
				String[] tk  = li.get(i).split("-");
				String name = tk[0];
				String price = tk[1];
				String qty = tk[2];
				String total = tk[3];
				arr[i][0] = name;
				arr[i][1] = price;
				arr[i][2] = qty;
				arr[i][3] = total;
			}
		}
		return arr;
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(backButton)) {
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
