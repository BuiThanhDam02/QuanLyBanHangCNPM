package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Order;
import model.OrdersDetail;
import model.dao.OrderDAO;
import model.dao.OrderDetailDAO;
import model.dao.ProductDAO;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainSellView extends JFrame implements ActionListener {

	private final Color mainColor = Color.white, inputColor = Color.black;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel table_1;

	private final JTable orderDetailDataTable;
	private ProductDAO productDAO;
	private OrderDAO orderDAO;
	private OrderDetailDAO orderDetailDAO;
	private JButton backButton, cancelButton, confirmButton, delButton;
	private List<String> list = new ArrayList<String>();
	private String[] detailOrderColumn;
	private String[][] dataDetailOrder;
	private int totalProduct;
	private float totalPrice;
	private JLabel LabelPrice, labelProduct;

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * 
	 * 
	 */

	public MainSellView() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 20, 1135, 720);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Sale");
		setContentPane(contentPane);
		contentPane.setLayout(null);

		productDAO = new ProductDAO();
		orderDAO = new OrderDAO();
		orderDetailDAO = new OrderDetailDAO();

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

		String column[] = { "Id", "Name", "Image", "Price", "Category", "Status", "Description" };
		String[][] productData = productDAO.readProductsTableData();
		table = new JTable(productData, column);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dataDetailOrder = choseProductToOrder(list);

		// chon san pham de xuat hoa don
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				String t = JOptionPane.showInputDialog(panel_1, "Product quanlity");
				if (t != null) {
					try {
						int qty = Integer.parseInt(t);
						if (qty < 0) {
							JOptionPane.showMessageDialog(panel_1, "Number of products must be greater than 0!");
						} else {
							String id = productData[table.getSelectedRow()][0];
							String name = productData[table.getSelectedRow()][1];
							String image = productData[table.getSelectedRow()][2];
							double price = Double.parseDouble(productData[table.getSelectedRow()][3]);
							String category = productData[table.getSelectedRow()][4];
							String status = productData[table.getSelectedRow()][5];
							String description = productData[table.getSelectedRow()][6];
							list.add(id + "-" + name + "-" + price + "-" + qty + "-" + price * qty);
							totalProduct += qty;
							totalPrice += price * qty;
						}
						dataDetailOrder = choseProductToOrder(list);
						updateTable();
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(panel_1, "Product quantity must be in digits!");
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

		JLabel lblNewLabel = new JLabel("Order Detail");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 10, 299, 47);
		panel_2.add(lblNewLabel);

		detailOrderColumn = new String[] { "Id", "Name", "Price", "Quanlity", "Total" };

		table_1 = new DefaultTableModel(choseProductToOrder(list), detailOrderColumn);
		orderDetailDataTable = new JTable(table_1) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		orderDetailDataTable.setFont(new Font("Tahoma", Font.PLAIN, 15));

		// table_1.addMouseListener(new MouseAdapter(){
		// @Override
		// public void mouseClicked(MouseEvent event){
		//// dataDetailOrder[table_1.getSelectedRow()][0]="";
		//// dataDetailOrder[table_1.getSelectedRow()][1]="";
		//// dataDetailOrder[table_1.getSelectedRow()][2]="";
		// }
		// });

		JScrollPane scrollPane_1 = new JScrollPane(orderDetailDataTable);
		scrollPane_1.setBounds(10, 63, 299, 314);
		panel_2.add(scrollPane_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 387, 299, 113);
		panel_2.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Total Product");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(31, 32, 100, 13);
		panel_3.add(lblNewLabel_1);

		labelProduct = new JLabel("0");
		labelProduct.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelProduct.setBounds(193, 32, 30, 13);
		panel_3.add(labelProduct);

		JLabel lblNewLabel_3 = new JLabel("Total Price");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(31, 77, 80, 13);
		panel_3.add(lblNewLabel_3);

		LabelPrice = new JLabel("0");
		LabelPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		LabelPrice.setBounds(193, 77, 80, 13);
		panel_3.add(LabelPrice);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(794, 520, 309, 163);
		contentPane.add(panel_4);
		panel_4.setLayout(null);

		confirmButton = new JButton("Create Order");
		confirmButton.setBackground(Color.WHITE);
		confirmButton.setForeground(Color.BLUE);
		confirmButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		confirmButton.setBounds(10, 26, 289, 52);

		// them su kien khi nhan nut them
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.size() > 0) {
					try {
						Random generator = new Random();
						int value = generator.nextInt((10000 - 1) + 1) + 1;
						System.out.println(value);
						long millis = System.currentTimeMillis();
						Date date = new Date(millis);
						Order order = new Order(value, date, totalProduct, totalPrice, 0, "accepted");
						addOrder(order);
						addDetaiOrder(list, value);
						clearChoseProduct();
						JOptionPane.showMessageDialog(panel_1, "Create order successfully");
					} catch (Exception e2) {
						e2.getStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(panel_1, "No products selected!");
				}
			}
		});
		panel_4.add(confirmButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.RED);
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cancelButton.setBounds(10, 88, 289, 52);

		// them su kien xu ly khi nha nut huy
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearChoseProduct();
			}
		});
		panel_4.add(cancelButton);
//
//		delButton = new JButton("Xoá");
//		delButton.setForeground(Color.RED);
//		delButton.setBackground(Color.WHITE);
//		delButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
//		delButton.setBounds(10, 88, 289, 52);
//
//		delButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String idOrdrerText = JOptionPane.showInputDialog(null, "Nhập id của đơn hàng cần xoá: ");
//				int idOrdrer = Integer.parseInt(idOrdrerText);
//				try {
//					delOrder(idOrdrer);
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				;
//			}
//		});
//		panel_4.add(delButton);

	}

	// Cap nhat table san pham da chon
	private void updateTable() {
		int currentRowCount = table_1.getRowCount();
		table_1.setRowCount(0);
		table_1.setRowCount(currentRowCount);
		table_1.setDataVector(dataDetailOrder, detailOrderColumn);
		labelProduct.setText(Integer.toString(totalProduct));
		LabelPrice.setText(Float.toString(totalPrice));
	}

	// Xoa san pham da chon de thanh toan
	private void clearChoseProduct() {
		totalPrice = 0;
		totalProduct = 0;
		list.clear();
		dataDetailOrder = choseProductToOrder(list);
		updateTable();
	}

	private void setButtonDesign(JButton button) {
		button.setBounds(0, 0, 110, 25);
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

	// add detail order
	public void addDetaiOrder(List<String> li, int orderID) throws SQLException {
		for (String t : li) {
			String[] tk = t.split("-");
			String productId = tk[0];
			Float totalPrice = Float.parseFloat(tk[4]);
			int qty = Integer.parseInt(tk[3]);
			OrdersDetail detail = new OrdersDetail(0, Integer.parseInt(productId), orderID, qty, totalPrice, 0);
			orderDetailDAO.createOrderDetail(detail);
		}

	}

	// add order
	public void addOrder(Order order) throws SQLException {
		orderDAO.createOrder(order);
	}

	// del order
	public void delOrder(int orderId) throws SQLException {
		orderDAO.deleteOrder(orderId);
	}

	// update product in Order
	public String[][] choseProductToOrder(List<String> li) {
		String[][] arr = new String[li.size()][5];
		if (li.size() == 0) {
			return new String[][] { { "", "", "", "", "" } };
		} else {
			for (int i = 0; i < li.size(); i++) {
				String[] tk = li.get(i).split("-");
				String id = tk[0];
				String name = tk[1];
				String price = tk[2];
				String qty = tk[3];
				String total = tk[4];
				arr[i][0] = id;
				arr[i][1] = name;
				arr[i][2] = price;
				arr[i][3] = qty;
				arr[i][4] = total;
			}
		}
		return arr;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(backButton)) {
			try {
				productDAO.close();
				orderDAO.close();
				orderDetailDAO.close();
				new MenuFrame();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			this.dispose();
		}
	}

}
